package artifacts.mixin;

import artifacts.common.item.ArtifactItem;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UnbreakingEnchantment.class)
public abstract class UnbreakingEnchantmentMixin {

    @Inject(method = "canEnchant", at = @At("RETURN"), cancellable = true)
    private void canEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (stack.getItem() instanceof ArtifactItem) {
           info.setReturnValue(false);
        }
    }
}
