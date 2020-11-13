package artifacts.client.render.model.entity;

import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MimicChestLayerModel extends EntityModel<MimicEntity> {

    protected ModelRenderer bottom;
    protected ModelRenderer lid;
    protected ModelRenderer latch;

    public MimicChestLayerModel() {
        bottom = new ModelRenderer(64, 64, 0, 19);
        lid = new ModelRenderer(64, 64, 0, 0);
        latch = new ModelRenderer(64, 64, 0, 0);

        bottom.addBox(1, -9, 0, 14, 10, 14);
        lid.addBox(1, 0, 0, 14, 5, 14);
        latch.addBox(7, -1, 15, 2, 4, 1);

        bottom.setRotationPoint(0, 9, 1);
        lid.setRotationPoint(0, 9, 1);
        latch.setRotationPoint(0, 8, 0);
    }

    @Override
    public void setRotationAngles(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void setLivingAnimations(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (mimic.ticksInAir > 0) {
            lid.rotateAngleX = latch.rotateAngleX = Math.max(-60, (mimic.ticksInAir - 1 + partialTicks) * -6) * 0.0174533F;
            bottom.rotateAngleX = Math.min(30, (mimic.ticksInAir - 1 + partialTicks) * 3) * 0.0174533F;
        } else {
            lid.rotateAngleX = latch.rotateAngleX = 0;
            bottom.rotateAngleX = 0;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lid.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        latch.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
