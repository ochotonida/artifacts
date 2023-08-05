package artifacts.forge.client;

import artifacts.Artifacts;
import artifacts.forge.capability.SwimDataCapability;
import artifacts.registry.ModGameRules;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class HeliumFlamingoOverlayRenderer {

    private static final ResourceLocation HELIUM_FLAMINGO_ICON = Artifacts.id("textures/gui/icons.png");

    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean isEnabled = ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() > 0;
            if (isEnabled && minecraft.getCameraEntity() instanceof LivingEntity player) {
                player.getCapability(SwimDataCapability.CAPABILITY).ifPresent(
                        handler -> renderOverlay(gui, guiGraphics, screenWidth, screenHeight, handler.getSwimTime())
                );
            }
        }
    }

    private static void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, int screenWidth, int screenHeight, int swimTime) {
        gui.setupOverlayRenderState(true, false);
        RenderSystem.enableBlend();

        int left = screenWidth / 2 + 91;
        int top = screenHeight - gui.rightHeight;

        int maxProgressTime;
        if (Math.abs(swimTime) == 0) {
            return;
        } else if (swimTime > 0) {
            maxProgressTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() * 20);
        } else {
            maxProgressTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_RECHARGE_DURATION.get() * 20);
        }

        float progress = 1 - Math.abs(swimTime) / (float) maxProgressTime;

        int full = Mth.ceil((progress - 2D / maxProgressTime) * 10);
        int partial = Mth.ceil(progress * 10) - full;

        for (int i = 0; i < full + partial; ++i) {
            guiGraphics.blit(HELIUM_FLAMINGO_ICON, left - i * 8 - 9, top, -90, (i < full ? 0 : 9), 0, 9, 9, 32, 16);
        }
        gui.rightHeight += 10;

        RenderSystem.disableBlend();
    }
}
