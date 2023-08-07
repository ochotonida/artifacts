package artifacts.fabric.mixin.item.wearable.heliumflamingo.client;

import artifacts.client.HeliumFlamingoOverlay;
import artifacts.component.SwimData;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent {

    @Shadow
    private int screenHeight;

    @Shadow
    private int screenWidth;

    @Shadow
    protected abstract int getVisibleVehicleHeartRows(int i);

    @Shadow
    protected abstract LivingEntity getPlayerVehicleWithHealth();

    @Shadow
    protected abstract int getVehicleMaxHearts(LivingEntity livingEntity);

    @Shadow
    protected abstract Player getCameraPlayer();

    @Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
    private void renderFlamingoAir(PoseStack poseStack, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player == null) {
            return;
        }

        renderOverlay(- getStatusBarHeightOffset(player), poseStack, screenWidth, screenHeight);
    }

    @Unique
    public boolean renderOverlay(int height, PoseStack poseStack, int screenWidth, int screenHeight) {
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
        RenderSystem.setShaderTexture(0, HeliumFlamingoOverlay.HELIUM_FLAMINGO_ICON);

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
            blit(poseStack, left - i * 8 - 9, top, -90, (i < full ? 0 : 9), 0, 9, 9, 32, 16);
        }

        RenderSystem.disableBlend();
        return true;
    }

    /**
     * Calculate offset for our status bar height, taking rendering of other status bars into account
     */
    @Unique
    private int getStatusBarHeightOffset(Player player) {
        int offset = -49; // Base offset

        LivingEntity livingEntity = this.getPlayerVehicleWithHealth();
        int maxHearts = this.getVehicleMaxHearts(livingEntity);

        // Offset if hunger is rendered
        if (maxHearts == 0) {
            offset -= 10;
        }

        // Offset if mount health is rendered
        offset -= (this.getVisibleVehicleHeartRows(maxHearts) - 1) * 10;

        // Offset if air is rendered
        int maxAir = player.getMaxAirSupply();
        int playerAir = Math.min(player.getAirSupply(), maxAir);
        if (player.isEyeInFluid(FluidTags.WATER) || playerAir < maxAir) {
            offset -= 10;
        }

        return offset;
    }
}
