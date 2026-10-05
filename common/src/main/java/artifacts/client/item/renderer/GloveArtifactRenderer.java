package artifacts.client.item.renderer;

import artifacts.client.item.ArmsModelSet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelType;
import org.jetbrains.annotations.Nullable;

public class GloveArtifactRenderer extends ArtifactRenderer {

    private final Identifier wideTexture;
    private final Identifier slimTexture;
    private final Identifier wideGlowTexture;
    private final Identifier slimGlowTexture;
    private final ArmsModelSet<HumanoidModel<HumanoidRenderState>> models;

    protected GloveArtifactRenderer(
            Identifier wideTexture,
            Identifier slimTexture,
            @Nullable Identifier wideGlowTexture,
            @Nullable Identifier slimGlowTexture,
            ArmsModelSet<HumanoidModel<HumanoidRenderState>> models
    ) {
        this.wideTexture = wideTexture;
        this.slimTexture = slimTexture;
        this.wideGlowTexture = wideGlowTexture;
        this.slimGlowTexture = slimGlowTexture;
        this.models = models;
    }

    public static GloveArtifactRenderer create(String name, ArmsModelSet<HumanoidModel<HumanoidRenderState>> models) {
        return create("%s/%s_wide".formatted(name, name), "%s/%s_slim".formatted(name, name), models);
    }

    public static GloveArtifactRenderer create(String wideTextureName, String slimTextureName, ArmsModelSet<HumanoidModel<HumanoidRenderState>> models) {
        return new GloveArtifactRenderer(getTextureId(wideTextureName), getTextureId(slimTextureName), null, null, models);
    }

    public static GloveArtifactRenderer createGlowing(String name, ArmsModelSet<HumanoidModel<HumanoidRenderState>> models) {
        return new GloveArtifactRenderer(
                getTextureId("%s/%s_wide".formatted(name, name)),
                getTextureId("%s/%s_slim".formatted(name, name)),
                getTextureId("%s/%s_wide_overlay".formatted(name, name)),
                getTextureId("%s/%s_slim_overlay".formatted(name, name)),
                models
        );
    }

    private static PlayerModelType getModelType(LivingEntityRenderState renderState) {
        return renderState instanceof AvatarRenderState avatarRenderState ? avatarRenderState.skin.model() : PlayerModelType.WIDE;
    }

    private static HumanoidArm getArm(HumanoidRenderState renderState, int slotIndex) {
        InteractionHand hand = slotIndex % 2 == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        return hand == InteractionHand.MAIN_HAND ? renderState.mainArm : renderState.mainArm.getOpposite();
    }

    @Override
    protected Identifier getTexture(HumanoidRenderState renderState) {
        if (getModelType(renderState) == PlayerModelType.SLIM) {
            return slimTexture;
        }
        return wideTexture;
    }

    @Override
    protected @Nullable Identifier getFullBrightOverlayTexture(HumanoidRenderState renderState) {
        if (getModelType(renderState) == PlayerModelType.SLIM) {
            return slimGlowTexture;
        }
        return wideGlowTexture;
    }

    @Override
    protected HumanoidModel<HumanoidRenderState> getModel(HumanoidRenderState renderState, int slotIndex) {
        return models.get(getArm(renderState, slotIndex), getModelType(renderState));
    }

    private HumanoidModel<HumanoidRenderState> getModel(HumanoidRenderState renderState, HumanoidArm arm) {
        return models.get(arm, getModelType(renderState));
    }

    public final void renderFirstPersonArm(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, AbstractClientPlayer player, HumanoidArm arm, boolean hasFoil) {
        if (player.isSpectator()) {
            return;
        }
        /* FIXME
        // there's no render state available, so we have to extract one
        // there might be a better way to do this
        AvatarRenderer<AbstractClientPlayer> playerRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getPlayerRenderer(player);
        AvatarRenderState renderState = playerRenderer.createRenderState(player, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true));

        HumanoidModel<HumanoidRenderState> model = getModel(renderState, arm);

        // animate artifacts and then reset arms to default position
        model.setupAnim(renderState);
        model.leftArm.resetPose();
        model.rightArm.resetPose();

        // see AvatarRenderer::renderHand
        model.leftArm.zRot = -0.1F;
        model.rightArm.zRot = 0.1F;

        ModelPart modelPart = arm == HumanoidArm.LEFT ? model.leftArm : model.rightArm;

        Identifier texture = getTexture(renderState);
        Identifier glowTexture = getFullBrightOverlayTexture(renderState);

        renderModelPartWithFoil(model, modelPart, poseStack, submitNodeCollector, texture, packedLight, hasFoil);
        if (glowTexture != null) {
            renderModelPartWithFoil(model, modelPart, poseStack, submitNodeCollector, glowTexture, LightCoordsUtil.FULL_BRIGHT, hasFoil);
        }
        */
    }

    private void renderModelPartWithFoil(Model<?> model, ModelPart modelPart, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Identifier texture, int packedLight, boolean hasFoil) {
        RenderType renderType = model.renderType(texture);
        submitNodeCollector.order(0).submitModelPart(modelPart, poseStack, renderType, packedLight, OverlayTexture.NO_OVERLAY, null);
        if (hasFoil) {
            submitNodeCollector.order(1).submitModelPart(modelPart, poseStack, RenderTypes.trimmedArmorGlint(), packedLight, OverlayTexture.NO_OVERLAY, null);
        }
    }
}
