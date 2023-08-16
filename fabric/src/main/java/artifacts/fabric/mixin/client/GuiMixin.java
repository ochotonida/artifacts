package artifacts.fabric.mixin.client;

import artifacts.Artifacts;
import artifacts.item.wearable.WearableArtifactItem;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    private int screenHeight;

    @Shadow
    private int screenWidth;

    @Final
    @Shadow
    private Minecraft minecraft;

    @Shadow
    protected abstract Player getCameraPlayer();

    @Inject(method = "renderHotbar", at = @At(value = "TAIL"))
    private void renderFlamingoAir(float f, GuiGraphics guiGraphics, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (!Artifacts.CONFIG.client.enableCooldownOverlay || player == null) {
            return;
        }

        TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
            int y = screenHeight - 16 - 3;
            int cooldownOverlayOffset = Artifacts.CONFIG.client.cooldownOverlayOffset;
            int step = 20;
            int start = screenWidth / 2 + 91 + cooldownOverlayOffset;

            if (cooldownOverlayOffset < 0) {
                step = -20;
                start = screenWidth / 2 - 91 - 16 + cooldownOverlayOffset;
            }

            int k = 0;

            for (Map<String, TrinketInventory> map : component.getInventory().values()) {
                for (TrinketInventory inventory : map.values()) {
                    for (int i = 0; i < inventory.getContainerSize(); i++) {
                        ItemStack stack = inventory.getItem(i);
                        if (!stack.isEmpty() && stack.getItem() instanceof WearableArtifactItem && player.getCooldowns().isOnCooldown(stack.getItem())) {
                            int x = start + step * k++;
                            guiGraphics.renderItem(player, stack, x, y, k + 1);
                            guiGraphics.renderItemDecorations(minecraft.font, stack, x, y);
                        }
                    }
                }
            }
        });
    }
}
