package artifacts.client;

import artifacts.Artifacts;
import artifacts.capability.SwimHandler;
import artifacts.registry.ModGameRules;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class HeliumFlamingoOverlayRenderer {

    private static final ResourceLocation HELIUM_FLAMINGO_ICON = Artifacts.id("textures/gui/icons.png");

    public static void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean isEnabled = !ModGameRules.isInitialized() || ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() > 0;
            if (isEnabled && minecraft.getCameraEntity() instanceof LivingEntity player) {
                player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                        handler -> renderOverlay(gui, poseStack, screenWidth, screenHeight, handler.getSwimTime())
                );
            }
        }
    }

    private static void renderOverlay(ForgeGui gui, PoseStack poseStack, int screenWidth, int screenHeight, int swimTime) {
        gui.setupOverlayRenderState(true, false, HELIUM_FLAMINGO_ICON);
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
            ForgeGui.blit(poseStack, left - i * 8 - 9, top, -90, (i < full ? 0 : 9), 0, 9, 9, 32, 16);
        }
        gui.rightHeight += 10;

        RenderSystem.disableBlend();
    }
}
