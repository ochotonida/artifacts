package artifacts.mixin.aquadashers;

import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.item.CurioItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract boolean isSprinting();

    @Shadow
    public float fallDistance;

    @Inject(method = "playStepSound", at = @At("HEAD"))
    private void playStepSound(BlockPos pos, BlockState blockState, CallbackInfo callbackInfo) {
        // noinspection ConstantConditions
        if ((Object) this instanceof LivingEntity
                && blockState.getMaterial().isLiquid()
                && ((CurioItem) ModItems.AQUA_DASHERS.get()).isEquippedBy((LivingEntity) (Object) this)
                && isSprinting()
                && fallDistance < 4) {
            ((LivingEntity) (Object) this).getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        if (!handler.isWet()) {
                            ((LivingEntity) (Object) this).playSound(ModSoundEvents.WATER_STEP.get(), 0.15F, 1);
                        }
                    }
            );
        }
    }

    @Inject(method = "canSpawnSprintParticle", at = @At("HEAD"))
    private void canSpawnSprintParticle(CallbackInfoReturnable<Boolean> cir) {

    }
}
