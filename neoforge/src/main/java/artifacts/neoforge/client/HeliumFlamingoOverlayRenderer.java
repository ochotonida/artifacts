package artifacts.neoforge.client;

import artifacts.ArtifactsClient;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.entity.player.Player;

public class HeliumFlamingoOverlayRenderer {

    public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker ignored) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
            Hud hud = Minecraft.getInstance().gui.hud;
            if (!hud.isHidden()) {
                if (ArtifactsClient.getHeliumFlamingoOverlay().renderOverlay(guiGraphics, player, hud.rightHeight)) {
                    hud.rightHeight += 10;
                }
            }
        }
    }
}
