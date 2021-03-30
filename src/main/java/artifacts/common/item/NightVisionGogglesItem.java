package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.RenderTypes;
import artifacts.client.render.model.curio.NightVisionGogglesModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NightVisionGogglesItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/night_vision_goggles.png");
    private static final ResourceLocation TEXTURE_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/night_vision_goggles_glow.png");

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!livingEntity.level.isClientSide && livingEntity.tickCount % 15 == 0) {
            livingEntity.addEffect(new EffectInstance(Effects.NIGHT_VISION, 319, 0, true, false));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new NightVisionGogglesModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        super.render(identifier, index, matrixStack, renderTypeBuffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
        IVertexBuilder buffer = ItemRenderer.getFoilBuffer(renderTypeBuffer, RenderTypes.unlit(TEXTURE_GLOW), false, stack.hasFoil());
        getModel().renderToBuffer(matrixStack, buffer, 0xF000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
