package artifacts.forge.client;

import artifacts.Artifacts;
import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

public class ArtifactCooldownOverlayRenderer {

    @SuppressWarnings("unused")
    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (!Artifacts.CONFIG.client.enableCooldownOverlay || !(gui.getMinecraft().getCameraEntity() instanceof Player player)) {
            return;
        }

        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
            int y = screenHeight - 16 - 3;
            int cooldownOverlayOffset = Artifacts.CONFIG.client.cooldownOverlayOffset;
            int step = 20;
            int start = screenWidth / 2 + 91 + cooldownOverlayOffset;

            if (cooldownOverlayOffset < 0) {
                step = -20;
                start = screenWidth / 2 - 91 - 16 + cooldownOverlayOffset;
            }

            int k = 0;

            for (Map.Entry<String, ICurioStacksHandler> entry : handler.getCurios().entrySet()) {
                IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof WearableArtifactItem && player.getCooldowns().isOnCooldown(stack.getItem())) {
                        int x = start + step * k++;
                        guiGraphics.renderItem(player, stack, x, y, k + 1);
                        guiGraphics.renderItemDecorations(gui.getMinecraft().font, stack, x, y);
                    }
                }
            }
        });
    }
}
