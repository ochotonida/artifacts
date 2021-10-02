package artifacts.client.render.curio.renderer;

import artifacts.Artifacts;
import artifacts.client.render.curio.model.HandsModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.client.ForgeRenderTypes;

public class GlowingGloveCurioRenderer extends GloveCurioRenderer {

    private final ResourceLocation defaultGlowTexture;
    private final ResourceLocation slimGlowTexture;

    public GlowingGloveCurioRenderer(String name) {
        super(name);
        defaultGlowTexture = new ResourceLocation(Artifacts.MODID, String.format("textures/entity/curio/glove/%s/%s_default_glow.png", name, name));
        slimGlowTexture = new ResourceLocation(Artifacts.MODID, String.format("textures/entity/curio/glove/%s/%s_slim_glow.png", name, name));
    }

    private ResourceLocation getGlowTexture(boolean hasSlimArms) {
        return hasSlimArms ? slimGlowTexture : defaultGlowTexture;
    }

    @Override
    protected void renderArm(HandsModel model, PoseStack matrixStack, MultiBufferSource buffer, HumanoidArm handSide, int light, boolean hasSlimArms, boolean hasFoil) {
        super.renderArm(model, matrixStack, buffer, handSide, light, hasSlimArms, hasFoil);
        RenderType renderType = ForgeRenderTypes.getUnlitTranslucent(getGlowTexture(hasSlimArms));
        VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        model.renderHand(handSide, matrixStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @Override
    protected void renderFirstPersonArm(HandsModel model, ModelPart arm, PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasSlimArms, boolean hasFoil) {
        super.renderFirstPersonArm(model, arm, matrixStack, buffer, light, hasSlimArms, hasFoil);
        VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, ForgeRenderTypes.getUnlitTranslucent(getGlowTexture(hasSlimArms)), false, hasFoil);
        arm.render(matrixStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY);
    }
}
