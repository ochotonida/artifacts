package artifacts.client.render.entity.model;

import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public class MimicChestLayerModel extends EntityModel<MimicEntity> {

    protected final ModelPart bottom;
    protected final ModelPart lid;
    protected final ModelPart latch;

    public MimicChestLayerModel() {
        bottom = new ModelPart(64, 64, 0, 19);
        lid = new ModelPart(64, 64, 0, 0);
        latch = new ModelPart(64, 64, 0, 0);

        bottom.addBox(1, -9, 0, 14, 10, 14);
        lid.addBox(1, 0, 0, 14, 5, 14);
        latch.addBox(7, -1, 15, 2, 4, 1);

        bottom.setPos(0, 9, 1);
        lid.setPos(0, 9, 1);
        latch.setPos(0, 8, 0);
    }

    @Override
    public void setupAnim(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void prepareMobModel(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (mimic.ticksInAir > 0) {
            lid.xRot = latch.xRot = Math.max(-60, (mimic.ticksInAir - 1 + partialTicks) * -6) * 0.0174533F;
            bottom.xRot = Math.min(30, (mimic.ticksInAir - 1 + partialTicks) * 3) * 0.0174533F;
        } else {
            lid.xRot = latch.xRot = 0;
            bottom.xRot = 0;
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lid.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        latch.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
