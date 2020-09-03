package artifacts.common.entity;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EntityHallowStar extends Entity {

    public @Nullable
    EntityLivingBase shootingEntity;

    public int ticksAlive;

    public EntityHallowStar(World world) {
        super(world);
        setSize(0.5F, 0.5F);
    }

    public EntityHallowStar(World world, EntityLivingBase shooter) {
        this(world);
        shootingEntity = shooter;
        double offSetX = 8 * (2 * rand.nextDouble() - 1);
        double offSetZ = 8 * (2 * rand.nextDouble() - 1);
        double offSetY = 25 + rand.nextDouble() * 15;
        setPosition(shooter.posX + offSetX - 1 + rand.nextFloat() * 2, shooter.posY + offSetY, shooter.posZ + offSetZ - 1 + rand.nextFloat() * 2);
        double velocity = Math.sqrt(offSetX * offSetX + offSetY * offSetY + offSetZ * offSetZ);
        motionX = -offSetX * 1.5 / velocity;
        motionY = -offSetY * 1.5 / velocity;
        motionZ = -offSetZ * 1.5 / velocity;
    }

    @Override
    protected void entityInit() {
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d = getEntityBoundingBox().getAverageEdgeLength() * 6;
        if (Double.isNaN(d)) {
            d = 4;
        }
        d = d * 64;
        return distance < d * d;
    }

    public void onUpdate() {
        if (world.isRemote || (shootingEntity == null || !shootingEntity.isDead) && world.isBlockLoaded(new BlockPos(this)) && ticksAlive < 200) {
            super.onUpdate();
            ticksAlive++;

            // noinspection ConstantConditions
            RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, true, shootingEntity);
            // noinspection ConstantConditions
            if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                onImpact(raytraceresult);
            }

            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);

            if (isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * 0.25D, posY - motionY * 0.25D, posZ - motionZ * 0.25D, motionX, motionY, motionZ);
                }
            }

            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
            setPosition(posX, posY, posZ);
        } else {
            setDead();
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        ticksAlive = compound.getInteger("tickAlive");

        if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3) {
            NBTTagList nbtTagList = compound.getTagList("direction", 6);
            motionX = nbtTagList.getDoubleAt(0);
            motionY = nbtTagList.getDoubleAt(1);
            motionZ = nbtTagList.getDoubleAt(2);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setTag("direction", newDoubleNBTList(motionX, motionY, motionZ));
        compound.setInteger("ticksAlive", ticksAlive);
    }

    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote && result.entityHit != shootingEntity && !(result.entityHit instanceof EntityHallowStar)) {
            if (result.entityHit != null) {
                if (!result.entityHit.isImmuneToFire()) {
                    result.entityHit.attackEntityFrom(new EntityDamageSourceIndirect(Artifacts.MODID + ".hallow_star", this, shootingEntity).setProjectile(), ModConfig.general.starCloakDamage);
                }
            }
            world.setEntityState(this, (byte) 3);
        }
        playSound(SoundEvents.BLOCK_WOOD_PLACE, 1, 1);
        setDead();
    }

    public float getBrightness() {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int i = 0; i < 16; ++i) {
                world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX + rand.nextGaussian(), posY - 0.5 * rand.nextDouble(), posZ + rand.nextGaussian(), 0, 0, 0);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }
}
