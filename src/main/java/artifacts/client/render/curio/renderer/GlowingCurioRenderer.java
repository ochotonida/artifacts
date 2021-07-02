package artifacts.client.render.curio.renderer;

import artifacts.Artifacts;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;

public class GlowingCurioRenderer extends SimpleCurioRenderer {

    private final ResourceLocation glowTexture;

    public GlowingCurioRenderer(String name, BipedModel<LivingEntity> model) {
        super(String.format("%s/%s", name, name), model);
        this.glowTexture = new ResourceLocation(Artifacts.MODID, String.format("textures/entity/curio/%s/%s_glow.png", name, name));
    }

    private ResourceLocation getGlowTexture() {
        return glowTexture;
    }

    @Override
    protected void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, boolean hasFoil) {
        super.render(matrixStack, buffer, light, hasFoil);
        RenderType renderType = ForgeRenderTypes.getUnlitTranslucent(getGlowTexture());
        IVertexBuilder builder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        getModel().renderToBuffer(matrixStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
