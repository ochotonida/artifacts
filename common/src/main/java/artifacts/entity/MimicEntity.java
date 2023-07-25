package artifacts.entity;

import artifacts.Artifacts;
import artifacts.registry.ModSoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class MimicEntity extends Mob implements Enemy {

    public static final ResourceLocation LOOT_TABLE = Artifacts.id("entities/mimic");

    public int ticksInAir;
    public int attackCooldown;
    public boolean isDormant;
    public Direction facing;

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
        goalSelector.addGoal(3, new FaceRandomGoal(this));
        goalSelector.addGoal(5, new HopGoal(this));
        // noinspection ConstantConditions
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 1, true, false, (entity) -> !isDormant || distanceTo(entity) < getAttribute(Attributes.FOLLOW_RANGE).getValue() / 2.5));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("ticksInAir", ticksInAir);
        compound.putBoolean("isDormant", isDormant);
        if (facing != null) {
            compound.putString("facing", facing.name());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        ticksInAir = compound.getInt("ticksInAir");
        if (compound.contains("facing")) {
            setFacing(Direction.byName(compound.getString("facing").toLowerCase()));
        } else {
            setFacing(null);
        }
        setDormant(compound.getBoolean("isDormant"));
    }

    @Override
    public void tick() {
        super.tick();

        if (isInWater()) {
            ticksInAir = 0;
            if (isDormant) {
                setDormant(false);
            }
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
        super.playerTouch(player);
        // noinspection ConstantConditions
        if (
                attackCooldown <= 0
                && player.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL
                && distanceToSqr(player.getBoundingBox().getCenter().subtract(0, getBoundingBox().getYsize() / 2, 0)) < 1
                && player.hurt(damageSources().mobAttack(this), (float) getAttribute(Attributes.ATTACK_DAMAGE).getValue())
        ) {
            attackCooldown = 20;
            doEnchantDamageEffects(this, player);
        }
    }

    @Override
    public void setTarget(LivingEntity entity) {
        setDormant(false);
        super.setTarget(entity);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Player player) {
            setTarget(player);
        }

        if (ticksInAir <= 0 && source.is(DamageTypeTags.IS_PROJECTILE) && !source.is(DamageTypeTags.BYPASSES_ARMOR)) {
            playSound(ModSoundEvents.MIMIC_HURT.get(), getSoundVolume(), getVoicePitch());
            return false;
        }

        if (onGround() && getRandom().nextBoolean() && getMoveControl() instanceof MimicMovementController controller) {
            controller.setDirection(getRandom().nextInt(4) * 90, true);
        }

        return super.hurt(source, amount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.MIMIC_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.MIMIC_DEATH.get();
    }

    protected SoundEvent getJumpingSound() {
        return ModSoundEvents.MIMIC_OPEN.get();
    }

    protected SoundEvent getLandingSound() {
        return ModSoundEvents.MIMIC_CLOSE.get();
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return LOOT_TABLE;
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
                    && player.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL
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
                    && player.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL
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

    protected static class MimicMovementController extends MoveControl {

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
