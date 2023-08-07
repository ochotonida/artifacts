package artifacts.fabric.mixin.item.wearable.heliumflamingo.client;

import artifacts.client.HeliumFlamingoOverlay;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

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
    private void renderFlamingoAir(GuiGraphics guiGraphics, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player == null) {
            return;
        }

        HeliumFlamingoOverlay.renderOverlay(- getStatusBarHeightOffset(player), guiGraphics, screenWidth, screenHeight);
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
