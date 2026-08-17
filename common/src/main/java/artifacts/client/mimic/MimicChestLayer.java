package artifacts.client.mimic;

import artifacts.client.mimic.model.MimicChestLayerModel;
import artifacts.client.mimic.model.MimicModel;
import artifacts.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class MimicChestLayer extends RenderLayer<MimicEntity, MimicModel> {

    private final MimicChestLayerModel chestModel;
    private final MimicChestMaterials chestMaterials;

    public MimicChestLayer(RenderLayerParent<MimicEntity, MimicModel> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);
        chestModel = new MimicChestLayerModel(modelSet.bakeLayer(MimicChestLayerModel.LAYER_LOCATION));
        chestMaterials = new MimicChestMaterials();
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!mimic.isInvisible()) {
            matrixStack.pushPose();

            matrixStack.mulPose(Axis.XP.rotationDegrees(180));
            matrixStack.translate(-0.5, -1.5, -0.5);

            getParentModel().copyPropertiesTo(chestModel);
            chestModel.prepareMobModel(mimic, limbSwing, limbSwingAmount, partialTicks);
            chestModel.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer builder = chestMaterials.getChestSprite(mimic).buffer(buffer, RenderType::entityCutout);
            chestModel.renderToBuffer(matrixStack, builder, packedLight, LivingEntityRenderer.getOverlayCoords(mimic, 0), 0xFFFFFFFF);

            matrixStack.popPose();
        }
    }
}
