package artifacts.client;

import artifacts.Artifacts;
import artifacts.component.SwimData;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class HeliumFlamingoOverlay {

    private static final ResourceLocation HELIUM_FLAMINGO_ICON = Artifacts.id("textures/gui/icons.png");

    public static boolean renderOverlay(int height, GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        boolean isEnabled = ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() > 0;
        if (!isEnabled || !(Minecraft.getInstance().getCameraEntity() instanceof LivingEntity player)) {
            return false;
        }
        SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
        if (swimData == null) {
            return false;
        }
        int swimTime = swimData.getSwimTime();

        RenderSystem.enableBlend();

        int left = screenWidth / 2 + 91;
        int top = screenHeight - height;

        int maxProgressTime;
        if (Math.abs(swimTime) == 0) {
            return false;
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

        RenderSystem.disableBlend();
        return true;
    }
}
