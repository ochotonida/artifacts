package artifacts.mixin.aquadashers;

import artifacts.common.capability.swimhandler.ISwimHandler;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.item.CurioItem;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public World level;

    @Shadow public float fallDistance;

    @Shadow @Final protected Random random;

    @Shadow private EntitySize dimensions;

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract boolean isSprinting();

    @Shadow public abstract Vector3d getDeltaMovement();

    @Inject(method = "playStepSound", at = @At("HEAD"))
    private void playWaterStepSound(BlockPos pos, BlockState blockState, CallbackInfo callbackInfo) {
        if (blockState.getMaterial().isLiquid() && isRunningWithAquaDashers()) {
            ((LivingEntity) (Object) this).playSound(ModSoundEvents.WATER_STEP.get(), 0.15F, 1);
        }
    }

    @Inject(method = "spawnSprintParticle", at = @At("HEAD"))
    private void spawnWaterSprintParticle(CallbackInfo callbackInfo) {
        if (isRunningWithAquaDashers()) {
            BlockPos pos = new BlockPos(MathHelper.floor(getX()), MathHelper.floor(getY() - 0.2), MathHelper.floor(getZ()));
            BlockState blockstate = level.getBlockState(pos);
            if (blockstate.getRenderShape() == BlockRenderType.INVISIBLE) {
                IParticleData particle;
                Vector3d motion = getDeltaMovement().multiply(-4, 0, -4);
                if (blockstate.getFluidState().is(FluidTags.LAVA)) {
                    motion = motion.add(0, 1, 0);
                    if (random.nextInt(3) == 0) {
                        particle = ParticleTypes.LAVA;
                    } else {
                        particle = ParticleTypes.FLAME;
                        motion = motion.multiply(0.2, 0.03, 0.2);
                    }
                } else if (!blockstate.getFluidState().isEmpty()) {
                    if (random.nextInt(3) == 0) {
                        particle = ParticleTypes.BUBBLE;
                    } else {
                        particle = ParticleTypes.SPLASH;
                        motion = motion.add(0, 1.5, 0);
                    }
                } else {
                    return;
                }
                level.addParticle(particle, getX() + (random.nextDouble() - 0.5) * dimensions.width, getY(), getZ() + (random.nextDouble() - 0.5) * dimensions.width, motion.x, motion.y, motion.z);
            }
        }
    }

    private boolean isRunningWithAquaDashers() {
        // noinspection ConstantConditions
        return (Object) this instanceof LivingEntity
                && ((CurioItem) ModItems.AQUA_DASHERS.get()).isEquippedBy((LivingEntity) (Object) this)
                && isSprinting()
                && fallDistance < 4
                && !((LivingEntity) (Object) this).getCapability(SwimHandlerCapability.INSTANCE).map(ISwimHandler::isWet).orElse(true);
    }
}
