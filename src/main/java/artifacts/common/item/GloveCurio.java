package artifacts.common.item;

import artifacts.client.render.model.curio.GloveModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class GloveCurio extends Curio {

    private Object model_default;
    private Object model_slim;

    public GloveCurio(Item item) {
        super(item);
    }

    protected static boolean hasSmallArms(Entity entity) {
        return entity instanceof AbstractClientPlayerEntity && ((AbstractClientPlayerEntity) entity).getSkinType().equals("slim");
    }

    protected ResourceLocation getTexture(boolean smallArms) {
        return smallArms ? getSlimTexture() : getTexture();
    }

    protected abstract ResourceLocation getSlimTexture();

    protected GloveModel getModel(boolean smallArms) {
        return (smallArms ? getSlimModel() : getModel());
    }

    protected GloveModel getSlimModel() {
        if (model_slim == null) {
            model_slim = new GloveModel(true);
        }
        return (GloveModel) model_slim;
    }

    @Override
    protected GloveModel getModel() {
        if (model_default == null) {
            model_default = new GloveModel(false);
        }
        return (GloveModel) model_default;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean smallArms = hasSmallArms(entity);
        GloveModel model = getModel(smallArms);
        Curio.RenderHelper.setBodyRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, partialTicks, netHeadYaw, headPitch, model);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(getTexture(smallArms)), false, false);
        model.renderRightArm(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
