package artifacts.entity;

import artifacts.registry.ModSoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Objects;

// TODO: rename to Mimic
public class MimicEntity extends Mob implements Enemy {

    private static final int DORMANT_TICK_RATE = 20;

    public int ticksInAir;
    public int attackCooldown;
    public boolean isDormant;
    public Direction facing;
    private int tickRate = 1;

    public MimicEntity(EntityType<? extends MimicEntity> type, Level world) {
        super(type, world);
        moveControl = new MimicMovementController(this);
        xpReward = 10;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.MOVEMENT_SPEED, 0.8)
                .add(Attributes.ATTACK_DAMAGE, 5);
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
        if (facing != null && getMoveControl() instanceof MimicMovementController controller) {
            controller.setDirection(facing.toYRot(), false);
        }
    }

    public void setDormant(boolean isDormant) {
        this.isDormant = isDormant;
        if (!isDormant) {
            facing = null;
        }
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new AttackGoal(this));
        goalSelector.addGoal(3, new PanicGoal());
        goalSelector.addGoal(4, new FaceRandomGoal(this));
        goalSelector.addGoal(5, new HopGoal(this));
        targetSelector.addGoal(1, new NearestPlayerTargetGoal());
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("ticksInAir", ticksInAir);
        output.putBoolean("isDormant", isDormant);
        if (facing != null) {
            output.putString("facing", facing.name());
        }
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        ticksInAir = input.getIntOr("ticksInAir", 0);
        setFacing(Direction.byName(input.getStringOr("facing", "").toLowerCase(Locale.ENGLISH)));
        setDormant(input.getBooleanOr("isDormant", false));
    }

    @Override
    public void tick() {
        if (tickCount % 20 == 0) {
            if (!isDormant || level().isClientSide()
                    || !onGround() || isDeadOrDying()
                    || isPassenger() || !getPassengers().isEmpty()
                    || level().getNearestPlayer(this, 32) != null) {
                tickRate = 1;
            } else {
                tickRate = DORMANT_TICK_RATE;
            }
        }

        if (tickCount % tickRate != 0) {
            return;
        }
        super.tick();

        if (isInWater()) {
            ticksInAir = 0;
        } else if (!onGround()) {
            ticksInAir++;
        } else {
            if (ticksInAir > 0) {
                playSound(getLandingSound(), getSoundVolume(), getVoicePitch());
                ticksInAir = 0;
            }
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (attackCooldown <= 0
                && player.level().getDifficulty() != Difficulty.PEACEFUL
                && isAlive()
                && isWithinMeleeAttackRange(player)
                && hasLineOfSight(player)
        ) {
            attackCooldown = 20;
            DamageSource damageSource = this.damageSources().mobAttack(this);
            float amount = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
            if (player.hurtOrSimulate(damageSource, amount) && level() instanceof ServerLevel level) {
                EnchantmentHelper.doPostAttackEffects(level, player, damageSource);
            }
        }
    }

    @Override
    public void setTarget(LivingEntity entity) {
        setDormant(false);
        super.setTarget(entity);
    }

    @Override
    public boolean isPushedByFluid() {
        return !isDormant;
    }

    @Override
    protected int decreaseAirSupply(int i) {
        return isDormant ? i : super.decreaseAirSupply(i);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (source.getEntity() instanceof Player player) {
            setTarget(player);
        }

        if (ticksInAir <= 0 && source.is(DamageTypeTags.IS_PROJECTILE) && !source.is(DamageTypeTags.BYPASSES_ARMOR)) {
            playSound(ModSoundEvents.MIMIC_HURT.value(), getSoundVolume(), getVoicePitch());
            return false;
        }

        if (onGround() && getRandom().nextBoolean() && getMoveControl() instanceof MimicMovementController controller) {
            controller.setDirection(getRandom().nextInt(4) * 90, true);
        }

        return super.hurtServer(level, source, amount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.MIMIC_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.MIMIC_DEATH.value();
    }

    protected SoundEvent getJumpingSound() {
        return ModSoundEvents.MIMIC_OPEN.value();
    }

    protected SoundEvent getLandingSound() {
        return ModSoundEvents.MIMIC_CLOSE.value();
    }

    protected static class AttackGoal extends Goal {

        private final MimicEntity mimic;
        private int timeRemaining;

        public AttackGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = mimic.getTarget();

            return target instanceof Player player
                    && player.isAlive()
                    && player.level().getDifficulty() != Difficulty.PEACEFUL
                    && !player.getAbilities().invulnerable;
        }

        @Override
        public void start() {
            timeRemaining = 300;
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = mimic.getTarget();

            return target instanceof Player player
                    && player.isAlive()
                    && player.level().getDifficulty() != Difficulty.PEACEFUL
                    && !player.getAbilities().invulnerable
                    && --timeRemaining > 0;
        }

        @Override
        public void tick() {
            super.tick();

            if (mimic.isDormant) {
                return;
            }

            if (mimic.getTarget() != null && mimic.getMoveControl() instanceof MimicMovementController controller) {
                mimic.lookAt(mimic.getTarget(), 10, 10);
                controller.setDirection(mimic.getYRot(), true);
            }
        }
    }

    protected class PanicGoal extends Goal {

        private int timeRemaining;

        public PanicGoal() {
            setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = getTarget();

            return level().getDifficulty() == Difficulty.PEACEFUL
                    && target instanceof Player player
                    && player.isAlive()
                    && !player.getAbilities().invulnerable;
        }

        @Override
        public void start() {
            timeRemaining = 300;
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = getTarget();

            return target instanceof Player player
                    && player.isAlive()
                    && player.level().getDifficulty() == Difficulty.PEACEFUL
                    && !player.getAbilities().invulnerable
                    && --timeRemaining > 0;
        }

        @Override
        public void tick() {
            super.tick();

            if (isDormant) {
                return;
            }

            if (onGround() && getMoveControl() instanceof MimicMovementController controller && controller.jumpDelay >= 10) {
                controller.setDirection(controller.rotationDegrees + random.nextFloat() * 180 - 90, true);
            }
        }
    }

    protected static class FaceRandomGoal extends Goal {

        private final MimicEntity mimic;
        private int chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return mimic.getTarget() == null && (mimic.onGround() || mimic.isInWater() || mimic.isInLava() || mimic.hasEffect(MobEffects.LEVITATION));
        }

        @Override
        public void tick() {
            if (mimic.isDormant) {
                return;
            }

            if (--nextRandomizeTime <= 0) {
                nextRandomizeTime = 480 + mimic.getRandom().nextInt(320);
                chosenDegrees = mimic.getRandom().nextInt(4) * 90;
            }

            if (mimic.getMoveControl() instanceof MimicMovementController controller) {
                controller.setDirection(chosenDegrees, false);
            }
        }
    }

    protected static class FloatGoal extends Goal {

        private final MimicEntity mimic;

        public FloatGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            mimic.getNavigation().setCanFloat(true);
        }

        @Override
        public boolean canUse() {
            return mimic.isInWater() || mimic.isInLava();
        }

        @Override
        public void tick() {
            if (mimic.getRandom().nextFloat() < 0.8F) {
                mimic.jumpControl.jump();
            }
            if (mimic.getMoveControl() instanceof MimicMovementController controller) {
                controller.setSpeed(1.2);
            }
        }
    }

    protected static class HopGoal extends Goal {

        private final MimicEntity mimic;

        public HopGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !mimic.isDormant && !mimic.isPassenger();
        }

        @Override
        public void tick() {
            if (mimic.getMoveControl() instanceof MimicMovementController controller) {
                controller.setSpeed(1);
            }
        }
    }

    private class NearestPlayerTargetGoal extends NearestAttackableTargetGoal<Player> {

        public NearestPlayerTargetGoal() {
            super(MimicEntity.this, Player.class, 1, true, false,
                    (entity, _) -> entity.canBeSeenAsEnemy() && (!isDormant || distanceTo(entity) < Objects.requireNonNull(getAttribute(Attributes.FOLLOW_RANGE)).getValue() / 2.5)
            );
        }
    }

    protected static class MimicMovementController extends MoveControl<MimicEntity> {

        private final MimicEntity mimic;
        private float rotationDegrees;
        private int jumpDelay;

        public MimicMovementController(MimicEntity mimic) {
            super(mimic);
            this.mimic = mimic;
            rotationDegrees = 180 * mimic.getYRot() / (float) Math.PI;
            jumpDelay = mimic.random.nextInt(320) + 640;
        }

        public void setDirection(float rotation, boolean shouldJump) {
            rotationDegrees = rotation;
            if (shouldJump && jumpDelay > 10) {
                jumpDelay = 10;
            }
        }

        public void setSpeed(double speed) {
            this.speedModifier = speed;
            operation = Operation.MOVE_TO;
        }

        @Override
        public void tick() {
            mimic.yHeadRot = mimic.yBodyRot = rotlerp(mimic.getYRot(), rotationDegrees, 90);
            mimic.setYRot(mimic.yHeadRot);
            if (operation != Operation.MOVE_TO) {
                mimic.setZza(0);
            } else {
                operation = Operation.WAIT;
                if (mimic.onGround()) {
                    // noinspection ConstantConditions
                    mimic.setSpeed((float) (speedModifier * mimic.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
                    if (jumpDelay-- > 0) {
                        mimic.xxa = mimic.zza = 0;
                        mimic.setSpeed(0);
                    } else {
                        jumpDelay = mimic.random.nextInt(320) + 640;

                        mimic.jumpControl.jump();
                        mimic.playSound(mimic.getJumpingSound(), mimic.getSoundVolume(), mimic.getVoicePitch());
                    }
                } else {
                    // noinspection ConstantConditions
                    mimic.setSpeed((float) (speedModifier * mimic.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
                }
            }
        }
    }
}
