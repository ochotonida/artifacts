package artifacts.fabric.mixin.client;

import artifacts.Artifacts;
import artifacts.client.item.renderer.GloveArtifactRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "renderLeftHand", at = @At("TAIL"))
    private void renderLeftGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo callbackInfo) {
        renderArm(matrixStack, buffer, light, player, HumanoidArm.LEFT);
    }

    @Inject(method = "renderRightHand", at = @At("TAIL"))
    private void renderRightGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo callbackInfo) {
        renderArm(matrixStack, buffer, light, player, HumanoidArm.RIGHT);
    }

    @Unique
    private static void renderArm(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm handSide) {
        if (!Artifacts.CONFIG.client.showFirstPersonGloves) {
            return;
        }

        String groupId = handSide == player.getMainArm() ? "hand" : "offhand";
        TrinketsApi.getTrinketComponent(player).ifPresent(component -> component
                .getAllEquipped()
                .stream()
                .filter(pair -> pair.getA().inventory().getSlotType().getGroup().equals(groupId))
                .map(Tuple::getB)
                .forEach(stack -> {
                    GloveArtifactRenderer gloveRenderer = GloveArtifactRenderer.getGloveRenderer(stack);
                    if (gloveRenderer != null) {
                        gloveRenderer.renderFirstPersonArm(matrixStack, buffer, light, player, handSide, stack.hasFoil());
                    }
                }));
    }
}
