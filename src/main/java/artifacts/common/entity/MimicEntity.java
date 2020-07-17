package artifacts.common.entity;

import artifacts.common.init.LootTables;
import artifacts.common.init.SoundEvents;
import net.minecraft.entity.*;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.EnumSet;

public class MimicEntity extends MobEntity implements IMob {

    public int ticksInAir;
    public boolean isDormant;

    public MimicEntity(EntityType<? extends MimicEntity> type, World world) {
        super(type, world);
        moveController = new MimicMovementController(this);
        experienceValue = 10;
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        if (getMoveHelper() instanceof MimicMovementController) {
            ((MimicMovementController) moveController).setDirection(rand.nextInt(4) * 90, false);
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public boolean canDespawn(double distance) {
        return false;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(65);
        getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24);
        getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new AttackGoal(this));
        goalSelector.addGoal(3, new FaceRandomGoal(this));
        goalSelector.addGoal(5, new HopGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (entity) -> Math.abs(entity.getPosY() - getPosY()) <= 4));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("ticksInAir", ticksInAir);
        compound.putBoolean("isDormant", isDormant);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
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
                playSound(getLandingSound(), getSoundVolume(), getSoundPitch());
                ticksInAir = 0;
            }
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity player) {
        super.onCollideWithPlayer(player);
        if (player.getEntityWorld().getDifficulty() != Difficulty.PEACEFUL && canEntityBeSeen(player) && getDistanceSq(player) < 1 && player.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue())) {
            applyEnchantments(this, player);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getTrueSource() instanceof PlayerEntity) {
            setAttackTarget((LivingEntity) source.getTrueSource());
            isDormant = false;
        }
        if (ticksInAir <= 0 && !source.isCreativePlayer() && source.getTrueSource() != null) {
            playSound(SoundEvents.MIMIC_HURT, getSoundVolume(), getSoundPitch());
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.MIMIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.MIMIC_DEATH;
    }

    protected SoundEvent getJumpingSound() {
        return SoundEvents.MIMIC_OPEN;
    }

    protected SoundEvent getLandingSound() {
        return SoundEvents.MIMIC_CLOSE;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LootTables.MIMIC;
    }

    public void setDormant() {
        isDormant = true;
    }

    protected static class AttackGoal extends Goal {

        private final MimicEntity mimic;
        private int timeRemaining;

        public AttackGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingEntity = mimic.getAttackTarget();

            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else {
                return !(livingEntity instanceof PlayerEntity) || !((PlayerEntity) livingEntity).abilities.disableDamage;
            }
        }

        @Override
        public void startExecuting() {
            timeRemaining = 300;
            super.startExecuting();
        }

        @Override
        public boolean shouldContinueExecuting() {
            LivingEntity livingEntity = mimic.getAttackTarget();

            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.disableDamage) {
                return false;
            } else {
                return --timeRemaining > 0;
            }
        }

        @Override
        public void tick() {
            super.tick();
            if (mimic.getAttackTarget() != null && mimic.getMoveHelper() instanceof MimicMovementController) {
                mimic.faceEntity(mimic.getAttackTarget(), 10, 10);
                ((MimicMovementController) mimic.getMoveHelper()).setDirection(mimic.rotationYaw, true);
            }
        }
    }

    protected static class FaceRandomGoal extends Goal {

        private final MimicEntity mimic;
        private int chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            return mimic.getAttackTarget() == null && (mimic.onGround || mimic.isInWater() || mimic.isInLava() || mimic.isPotionActive(Effects.LEVITATION));
        }

        @Override
        public void tick() {
            if (--nextRandomizeTime <= 0) {
                nextRandomizeTime = 480 + mimic.getRNG().nextInt(320);
                if (mimic.isDormant) {
                    chosenDegrees = Math.round(mimic.rotationYaw / 90) * 90;
                } else {
                    chosenDegrees = mimic.getRNG().nextInt(4) * 90;
                }
            }
            if (mimic.getMoveHelper() instanceof MimicMovementController) {
                ((MimicMovementController) mimic.getMoveHelper()).setDirection(chosenDegrees, false);
            }
        }
    }

    protected static class FloatGoal extends Goal {

        private final MimicEntity mimic;

        public FloatGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            mimic.getNavigator().setCanSwim(true);
        }

        @Override
        public boolean shouldExecute() {
            return mimic.isInWater() || mimic.isInLava();
        }

        @Override
        public void tick() {
            if (mimic.getRNG().nextFloat() < 0.8F) {
                mimic.jumpController.setJumping();
            }
            if (mimic.getMoveHelper() instanceof MimicMovementController) {
                ((MimicMovementController) mimic.getMoveHelper()).setSpeed(1.2);
            }
        }
    }

    protected static class HopGoal extends Goal {

        private final MimicEntity mimic;

        public HopGoal(MimicEntity mimic) {
            this.mimic = mimic;
            setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return !mimic.isDormant && !mimic.isPassenger();
        }

        @Override
        public void tick() {
            if (mimic.getMoveHelper() instanceof MimicMovementController) {
                ((MimicMovementController) mimic.getMoveHelper()).setSpeed(1.5);
            }
        }
    }

    protected static class MimicMovementController extends MovementController {

        private final MimicEntity mimic;
        private float rotationDegrees;
        private int jumpDelay;
        private boolean isAggressive;

        public MimicMovementController(MimicEntity mimic) {
            super(mimic);
            this.mimic = mimic;
            rotationDegrees = 180 * mimic.rotationYaw / (float) Math.PI;
            jumpDelay = mimic.rand.nextInt(320) + 640;
        }

        public void setDirection(float rotation, boolean isAggressive) {
            this.rotationDegrees = rotation;
            this.isAggressive = isAggressive;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
            action = Action.MOVE_TO;
        }

        @Override
        public void tick() {
            mimic.rotationYaw = limitAngle(mimic.rotationYaw, rotationDegrees, 90);
            mimic.rotationYawHead = mimic.rotationYaw;
            mimic.renderYawOffset = mimic.rotationYaw;

            if (action != Action.MOVE_TO) {
                mimic.setMoveForward(0);
            } else {
                action = Action.WAIT;
                if (mimic.onGround) {
                    mimic.setAIMoveSpeed((float) (speed * mimic.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
                    if (jumpDelay-- > 0) {
                        mimic.moveStrafing = 0;
                        mimic.moveForward = 0;
                        mimic.setAIMoveSpeed(0);

                        if (isAggressive && jumpDelay > 20) {
                            jumpDelay = mimic.rand.nextInt(10) + 10;
                        }
                    } else {
                        jumpDelay = mimic.rand.nextInt(320) + 640;

                        mimic.jumpController.setJumping();
                        mimic.playSound(mimic.getJumpingSound(), mimic.getSoundVolume(), mimic.getSoundPitch());
                    }
                } else {
                    mimic.setAIMoveSpeed((float) (speed * mimic.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
                }
            }
        }
    }
}
