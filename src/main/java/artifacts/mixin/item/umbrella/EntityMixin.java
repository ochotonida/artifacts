package artifacts.mixin.item.umbrella;

import artifacts.common.item.UmbrellaItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isInRain", at = @At("RETURN"), cancellable = true)
    private void blockRain(CallbackInfoReturnable<Boolean> info) {
        Entity self = (Entity) (Object) this;

        // noinspection ConstantConditions
        if (
                info.getReturnValueZ()
                && self instanceof LivingEntity entity
                && UmbrellaItem.isHoldingUmbrellaUpright(entity)
        ) {
            info.setReturnValue(false);
        }
    }
}
