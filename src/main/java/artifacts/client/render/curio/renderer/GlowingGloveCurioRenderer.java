package artifacts.client.render.curio.renderer;

import artifacts.Artifacts;
import artifacts.client.RenderTypes;
import artifacts.client.render.curio.model.HandsModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;

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
    protected void renderArm(HandsModel model, MatrixStack matrixStack, IRenderTypeBuffer buffer, HandSide handSide, int light, boolean hasSlimArms, boolean hasFoil) {
        super.renderArm(model, matrixStack, buffer, handSide, light, hasSlimArms, hasFoil);
        RenderType renderType = RenderTypes.unlit(getGlowTexture(hasSlimArms));
        IVertexBuilder builder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        model.renderHand(handSide, matrixStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @Override
    protected void renderFirstPersonArm(HandsModel model, ModelRenderer arm, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, boolean hasSlimArms, boolean hasFoil) {
        super.renderFirstPersonArm(model, arm, matrixStack, buffer, light, hasSlimArms, hasFoil);
        IVertexBuilder builder = ItemRenderer.getFoilBuffer(buffer, RenderTypes.unlit(getGlowTexture(hasSlimArms)), false, hasFoil);
        arm.render(matrixStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY);
    }
}
