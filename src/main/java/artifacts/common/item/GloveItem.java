package artifacts.common.item;

import artifacts.client.render.model.curio.hands.GloveModel;
import artifacts.client.render.model.curio.hands.HandsModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;

public abstract class GloveItem extends CurioItem {

    private Object modelSlim;

    protected static boolean hasSmallArms(Entity entity) {
        return entity instanceof AbstractClientPlayerEntity && ((AbstractClientPlayerEntity) entity).getModelName().equals("slim");
    }

    protected ResourceLocation getTexture(boolean smallArms) {
        return smallArms ? getSlimTexture() : getTexture();
    }

    protected abstract ResourceLocation getSlimTexture();

    @OnlyIn(Dist.CLIENT)
    protected HandsModel getModel(boolean smallArms) {
        return (smallArms ? getSlimModel() : (HandsModel) getModel());
    }

    @OnlyIn(Dist.CLIENT)
    protected final HandsModel getSlimModel() {
        if (modelSlim == null) {
            modelSlim = createModel(true);
        }
        return (HandsModel) modelSlim;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected final HandsModel createModel() {
        return createModel(false);
    }

    @OnlyIn(Dist.CLIENT)
    protected HandsModel createModel(boolean smallArms) {
        return new GloveModel(smallArms);
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        boolean smallArms = hasSmallArms(entity);
        HandsModel model = getModel(smallArms);
        model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
        ICurio.RenderHelper.followBodyRotations(entity, model);
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(buffer, model.renderType(getTexture(smallArms)), false, stack.hasFoil());
        model.renderHand(index % 2 == 0, matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderArm(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, AbstractClientPlayerEntity player, HandSide side, boolean hasFoil) {
        if (!player.isSpectator()) {
            boolean smallArms = hasSmallArms(player);
            HandsModel model = getModel(smallArms);

            ModelRenderer arm = side == HandSide.LEFT ? model.leftArm : model.rightArm;

            model.setAllVisible(false);
            arm.visible = true;

            model.crouching = false;
            model.attackTime = model.swimAmount = 0;
            model.setupAnim(player, 0, 0, 0, 0, 0);
            arm.xRot = 0;

            arm.render(matrixStack, ItemRenderer.getFoilBuffer(buffer, model.renderType(getTexture(smallArms)), false, hasFoil), combinedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
