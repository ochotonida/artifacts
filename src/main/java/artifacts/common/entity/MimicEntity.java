package artifacts.common.entity;

import artifacts.common.init.ModLootTables;
import artifacts.common.init.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class MimicEntity extends MobEntity implements IMob {

    public int ticksInAir;
    public int attackCooldown;
    public boolean isDormant;

    public MimicEntity(EntityType<? extends MimicEntity> type, World world) {
        super(type, world);
        moveControl = new MimicMovementController(this);
        xpReward = 10;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        if (getMoveControl() instanceof MimicMovementController) {
            ((MimicMovementController) moveControl).setDirection(random.nextInt(4) * 90, false);
        }
        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
    }

    public SoundCategory getSoundSource() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.MOVEMENT_SPEED, 0.8)
                .add(Attributes.ATTACK_DAMAGE, 5);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new AttackGoal(this));
        goalSelector.addGoal(3, new FaceRandomGoal(this));
        goalSelector.addGoal(5, new HopGoal(this));
        // noinspection ConstantConditions
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 1, true, false, (entity) -> !isDormant || distanceTo(entity) < getAttribute(Attributes.FOLLOW_RANGE).getValue() / 2.5));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("ticksInAir", ticksInAir);
        compound.putBoolean("isDormant", isDormant);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        ticksInAir = compound.getInt("ticksInAir");
        isDormant = compound.getBoolean("isDormant");
    }

    @Override
    public void tick() {
        super.tick();

        if (isInWater()) {
            ticksInAir = 0;
            if (isDormant) {
                isDormant = false;
            }
        } else if (!onGround) {
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
    public void playerTouch(PlayerEntity player) {
        super.playerTouch(player);
        // noinspection ConstantConditions
        if (attackCooldown <= 0 && player.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL && canSee(player) && distanceToSqr(player.getBoundingBox().getCenter().subtract(0, getBoundingBox().getYsize() / 2, 0)) < 1 && player.hurt(DamageSource.mobAttack(this), (float) getAttribute(Attributes.ATTACK_DAMAGE).getValue())) {
            attackCooldown = 20;
            doEnchantDamageEffects(this, player);
        }
    }

    @Override
    public void setTarget(LivingEntity entity) {
        isDormant = false;
        super.setTarget(entity);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof PlayerEntity) {
            setTarget((LivingEntity) source.getEntity());
        }
        if (ticksInAir <= 0 && source.isProjectile() && !source.isBypassArmor()) {
            playSound(ModSoundEvents.MIMIC_HURT.get(), getSoundVolume(), getVoicePitch());
            return false;
        }

        if (isOnGround() && getRandom().nextBoolean() && getMoveControl() instanceof MimicMovementController) {
            ((MimicMovementController) getMoveControl()).setDirection(getRandom().nextInt(4) * 90, true);
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
        return ModLootTables.MIMIC;
    }

    public void setDormant() {
        isDormant = true;
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

            return target instanceof PlayerEntity
                    && target.isAlive()
                    && target.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL
                    && !((PlayerEntity) target).abilities.invulnerable;
        }

        @Override
        public void start() {
            timeRemaining = 300;
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = mimic.getTarget();

            return target instanceof PlayerEntity
                    && target.isAlive()
                    && target.getCommandSenderWorld().getDifficulty() != Difficulty.PEACEFUL
                    && !((PlayerEntity) target).abilities.invulnerable
                    && --timeRemaining > 0;
        }

        @Override
        public void tick() {
            super.tick();
            if (mimic.getTarget() != null && mimic.getMoveControl() instanceof MimicMovementController) {
                mimic.lookAt(mimic.getTarget(), 10, 10);
                ((MimicMovementController) mimic.getMoveControl()).setDirection(mimic.yRot, true);
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
            return mimic.getTarget() == null && (mimic.onGround || mimic.isInWater() || mimic.isInLava() || mimic.hasEffect(Effects.LEVITATION));
        }

        @Override
        public void tick() {
            if (--nextRandomizeTime <= 0) {
                nextRandomizeTime = 480 + mimic.getRandom().nextInt(320);
                if (mimic.isDormant) {
                    chosenDegrees = Math.round(mimic.yRot / 90) * 90;
                } else {
                    chosenDegrees = mimic.getRandom().nextInt(4) * 90;
                }
            }
            if (mimic.getMoveControl() instanceof MimicMovementController) {
                ((MimicMovementController) mimic.getMoveControl()).setDirection(chosenDegrees, false);
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
            if (mimic.getMoveControl() instanceof MimicMovementController) {
                ((MimicMovementController) mimic.getMoveControl()).setSpeed(1.2);
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
            if (mimic.getMoveControl() instanceof MimicMovementController) {
                ((MimicMovementController) mimic.getMoveControl()).setSpeed(1);
            }
        }
    }

    protected static class MimicMovementController extends MovementController {

        private final MimicEntity mimic;
        private float rotationDegrees;
        private int jumpDelay;

        public MimicMovementController(MimicEntity mimic) {
            super(mimic);
            this.mimic = mimic;
            rotationDegrees = 180 * mimic.yRot / (float) Math.PI;
            jumpDelay = mimic.random.nextInt(320) + 640;
        }

        public void setDirection(float rotation, boolean shouldJump) {
            this.rotationDegrees = rotation;
            if (shouldJump && jumpDelay > 10) {
                jumpDelay = 10;
            }
        }

        public void setSpeed(double speed) {
            this.speedModifier = speed;
            operation = Action.MOVE_TO;
        }

        @Override
        public void tick() {
            mimic.yHeadRot = mimic.yBodyRot = mimic.yRot = rotlerp(mimic.yRot, rotationDegrees, 90);

            if (operation != Action.MOVE_TO) {
                mimic.setZza(0);
            } else {
                operation = Action.WAIT;
                if (mimic.onGround) {
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
