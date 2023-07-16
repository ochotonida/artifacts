package artifacts.forge.client.item.renderer;

import artifacts.forge.ArtifactsForge;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.ForgeRenderTypes;

public class GlowingArtifactRenderer extends ArtifactRenderer {

    private final ResourceLocation glowTexture;

    public GlowingArtifactRenderer(String name, HumanoidModel<LivingEntity> model) {
        super(String.format("%s/%s", name, name), model);
        this.glowTexture = ArtifactsForge.id("textures/entity/curio/%s/%s_glow.png", name, name);
    }

    private ResourceLocation getGlowTexture() {
        return glowTexture;
    }

    @Override
    protected void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, boolean hasFoil) {
        super.render(poseStack, multiBufferSource, light, hasFoil);
        RenderType renderType = ForgeRenderTypes.getUnlitTranslucent(getGlowTexture());
        VertexConsumer builder = ItemRenderer.getFoilBuffer(multiBufferSource, renderType, false, hasFoil);
        getModel().renderToBuffer(poseStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
