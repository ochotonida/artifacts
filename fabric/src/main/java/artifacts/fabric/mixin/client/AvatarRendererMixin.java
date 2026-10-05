package artifacts.fabric.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin {

    @Inject(method = "renderLeftHand", at = @At("TAIL"))
    private void renderLeftGlove(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, boolean hasSleeve, CallbackInfo ci) {
        artifacts$renderArm(poseStack, submitNodeCollector, lightCoords, HumanoidArm.LEFT);
    }

    @Inject(method = "renderRightHand", at = @At("TAIL"))
    private void renderRightGlove(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, boolean hasSleeve, CallbackInfo ci) {
        artifacts$renderArm(poseStack, submitNodeCollector, lightCoords, HumanoidArm.RIGHT);
    }

    @Unique
    private static void artifacts$renderArm(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int light, HumanoidArm handSide) {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            // FIXME EquipmentRenderingManager.renderFirstPersonArm(matrixStack, submitNodeCollector, light, player, handSide);
        }
    }
}
