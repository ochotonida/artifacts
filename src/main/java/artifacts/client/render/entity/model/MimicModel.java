package artifacts.client.render.entity.model;

import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public class MimicModel extends EntityModel<MimicEntity> {

    protected final ModelPart upperTeeth;
    protected final ModelPart lowerTeeth;
    protected final ModelPart upperMouthOverlay;
    protected final ModelPart lowerMouthOverlay;

    public MimicModel() {
        texWidth = 64;
        texHeight = 32;

        upperTeeth = new ModelPart(this, 0, 0);
        lowerTeeth = new ModelPart(this, 0, 15);
        upperMouthOverlay = new ModelPart(this, 24, 0);
        lowerMouthOverlay = new ModelPart(this, 36, 15);

        upperTeeth.addBox(-6, 0, -13, 12, 3, 12);
        lowerTeeth.addBox(-6, -4, -13, 12, 3, 12);
        upperMouthOverlay.addBox(-6, 0, -13, 12, 0, 12, 0.02F);
        lowerMouthOverlay.addBox(-6, -1, -13, 12, 0, 12, 0.02F);

        upperTeeth.setPos(0, 15, 7);
        lowerTeeth.setPos(0, 15, 7);
        upperMouthOverlay.setPos(0, 15, 7);
        lowerMouthOverlay.setPos(0, 15, 7);
    }

    @Override
    public void setupAnim(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void prepareMobModel(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (mimic.ticksInAir > 0) {
            upperTeeth.xRot = upperMouthOverlay.xRot = Math.max(-60, (mimic.ticksInAir - 1 + partialTicks) * -6) * 0.0174533F;
            lowerTeeth.xRot = lowerMouthOverlay.xRot = Math.min(30, (mimic.ticksInAir - 1 + partialTicks) * 3) * 0.0174533F;
        } else {
            upperTeeth.xRot = upperMouthOverlay.xRot = 0;
            lowerTeeth.xRot = lowerMouthOverlay.xRot = 0;
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        upperTeeth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lowerTeeth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        upperMouthOverlay.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lowerMouthOverlay.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
