package artifacts.mixin.item.umbrella.client;

import artifacts.registry.ModDataComponents;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @ModifyExpressionValue(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"))
    private ItemUseAnimation modifyUseAnimation(ItemUseAnimation original, AbstractClientPlayer player, float frameInterp, float xRot, InteractionHand hand, float attack, ItemStack itemStack) {
        if (itemStack.has(ModDataComponents.HANDHELD_GLIDER.get()) && original == ItemUseAnimation.BLOCK) {
            // ItemInHandLayer applies additional transforms when blocking with an item that doesn't subclass ShieldItem
            // The umbrella model itself already defines its blocking transforms
            return ItemUseAnimation.NONE;
        }
        return original;
    }
}
