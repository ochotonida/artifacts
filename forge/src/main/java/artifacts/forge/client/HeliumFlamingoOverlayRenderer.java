package artifacts.forge.client;

import artifacts.client.HeliumFlamingoOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class HeliumFlamingoOverlayRenderer {

    @SuppressWarnings("unused")
    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
            if (HeliumFlamingoOverlay.renderOverlay(gui.rightHeight, guiGraphics, screenWidth, screenHeight)) {
                gui.rightHeight += 10;
            }
        }
    }
}
