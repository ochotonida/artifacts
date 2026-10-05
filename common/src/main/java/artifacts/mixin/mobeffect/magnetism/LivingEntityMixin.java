package artifacts.mixin.mobeffect.magnetism;

import artifacts.extensions.mobeffect.magnetism.ItemEntityExtensions;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "drop", at = @At("RETURN"))
    private void setItemThrower(ItemStack itemStack, boolean thrownFromHand, Prediction prediction, CallbackInfoReturnable<ItemEntity> cir) {
        if (thrownFromHand && cir.getReturnValue() != null) {
            ((ItemEntityExtensions) cir.getReturnValue()).artifacts$setThrower((LivingEntity) (Object) this);
        }
    }
}
