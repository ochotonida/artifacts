package artifacts.common.entity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public class EntityMimic extends EntityLiving implements IMob {

    public int ticksInAir;

    public EntityMimic(World world) {
        super(world);
        moveHelper = new EntityMimic.MimicMoveHelper(this);
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityMimic.AIMimicFloat(this));
        this.tasks.addTask(2, new EntityMimic.AIMimicAttack(this));
        this.tasks.addTask(3, new EntityMimic.AIMimicFaceRandom(this));
        this.tasks.addTask(5, new EntityMimic.AIMimicHop(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("ticksInAir", ticksInAir);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        ticksInAir = compound.getInteger("ticksInAir");
    }

    @Override
    public void onUpdate() {
        if (!world.isRemote && world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            isDead = true;
        }

        super.onUpdate();

        if (isInWater()) {
            ticksInAir = 0;
        } else if (!onGround) {
            ticksInAir++;
        } else {
            if (ticksInAir > 0) {
                playSound(getLandingSound(), this.getSoundVolume(), this.getSoundPitch());
                ticksInAir = 0;
            }
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        super.onCollideWithPlayer(player);
        if (canEntityBeSeen(player) && getDistanceSq(player) < 1 && player.attackEntityFrom(DamageSource.causeMobDamage(this), 6F)) {
            applyEnchantments(this, player);
        }
    }

    @Override
    public float getEyeHeight() {
        return 0.8F;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    protected SoundEvent getJumpingSound() {
        return SoundEvents.BLOCK_CHEST_OPEN;
    }

    protected SoundEvent getLandingSound() {
        return SoundEvents.BLOCK_CHEST_CLOSE;
    }

    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }

    @Override
    protected void jump() {
        this.motionY = 0.5;
        this.isAirBorne = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
    }

    static class AIMimicAttack extends EntityAIBase {

        private final EntityMimic mimic;
        private int executeTimeRemaining;

        public AIMimicAttack(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(2);
        }

        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = mimic.getAttackTarget();

            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else {
                return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage;
            }
        }

        public void startExecuting() {
            executeTimeRemaining = 300;
            super.startExecuting();
        }

        public boolean shouldContinueExecuting() {
            EntityLivingBase entitylivingbase = mimic.getAttackTarget();

            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
                return false;
            } else {
                return --this.executeTimeRemaining > 0;
            }
        }

        public void updateTask() {
            // noinspection ConstantConditions
            mimic.faceEntity(mimic.getAttackTarget(), 10, 10);
            ((EntityMimic.MimicMoveHelper) mimic.getMoveHelper()).setDirection(mimic.rotationYaw, true);
        }
    }

    static class AIMimicFaceRandom extends EntityAIBase {

        private final EntityMimic mimic;
        private int chosenDegrees;
        private int timeUntilNextFaceRandom;

        public AIMimicFaceRandom(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(2);
        }

        public boolean shouldExecute() {
            return mimic.getAttackTarget() == null && (mimic.onGround || mimic.isInWater() || mimic.isInLava() || mimic.isPotionActive(MobEffects.LEVITATION));
        }

        public void updateTask() {
            if (--timeUntilNextFaceRandom <= 0) {
                timeUntilNextFaceRandom = 40 + mimic.getRNG().nextInt(60);
                chosenDegrees = mimic.getRNG().nextInt(360);
            }
            ((EntityMimic.MimicMoveHelper) mimic.getMoveHelper()).setDirection(chosenDegrees, false);
        }
    }

    static class AIMimicFloat extends EntityAIBase {

        private final EntityMimic mimic;

        public AIMimicFloat(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(5);
            ((PathNavigateGround) mimic.getNavigator()).setCanSwim(true);
        }

        public boolean shouldExecute() {
            return mimic.isInWater() || mimic.isInLava();
        }

        public void updateTask() {
            if (mimic.getRNG().nextFloat() < 0.8F) {
                mimic.getJumpHelper().setJumping();
            }

            ((EntityMimic.MimicMoveHelper) mimic.getMoveHelper()).setSpeed(1.2);
        }
    }

    static class AIMimicHop extends EntityAIBase {

        private final EntityMimic mimic;

        public AIMimicHop(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(5);
        }

        public boolean shouldExecute() {
            return true;
        }

        public void updateTask() {
            ((EntityMimic.MimicMoveHelper) mimic.getMoveHelper()).setSpeed(1);
        }
    }

    static class MimicMoveHelper extends EntityMoveHelper {

        private float rotationDegrees;
        private int jumpDelay;
        private final EntityMimic mimic;
        private boolean isAggressive;

        public MimicMoveHelper(EntityMimic mimic) {
            super(mimic);
            this.mimic = mimic;
            rotationDegrees = 180 * mimic.rotationYaw / (float) Math.PI;
        }

        public void setDirection(float rotation, boolean isAggressive) {
            this.rotationDegrees = rotation;
            this.isAggressive = isAggressive;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
            action = EntityMoveHelper.Action.MOVE_TO;
        }

        public void onUpdateMoveHelper() {
            entity.rotationYaw = limitAngle(entity.rotationYaw, rotationDegrees, 90);
            entity.rotationYawHead = entity.rotationYaw;
            entity.renderYawOffset = entity.rotationYaw;

            if (action != EntityMoveHelper.Action.MOVE_TO) {
                entity.setMoveForward(0.0F);
            } else {
                action = EntityMoveHelper.Action.WAIT;

                if (entity.onGround) {
                    entity.setAIMoveSpeed((float) (speed * entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                    if (jumpDelay-- <= 0) {
                        jumpDelay = mimic.rand.nextInt(80) + 40;

                        if (isAggressive) {
                            jumpDelay /= 3;
                        }

                        mimic.getJumpHelper().setJumping();
                        mimic.playSound(mimic.getJumpingSound(), mimic.getSoundVolume(), mimic.getSoundPitch());

                    } else {
                        mimic.moveStrafing = 0;
                        mimic.moveForward = 0;
                        entity.setAIMoveSpeed(0);
                    }
                } else {
                    entity.setAIMoveSpeed((float) (speed * entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                }
            }
        }
    }
}
