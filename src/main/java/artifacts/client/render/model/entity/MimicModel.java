package artifacts.client.render.model.entity;

import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MimicModel extends EntityModel<MimicEntity> {

    protected ModelRenderer upperTeeth;
    protected ModelRenderer lowerTeeth;
    protected ModelRenderer upperMouthOverlay;
    protected ModelRenderer lowerMouthOverlay;

    public MimicModel() {
        textureWidth = 64;
        textureHeight = 32;

        upperTeeth = new ModelRenderer(this, 0, 0);
        lowerTeeth = new ModelRenderer(this, 0, 15);
        upperMouthOverlay = new ModelRenderer(this, 24, 0);
        lowerMouthOverlay = new ModelRenderer(this, 36, 15);

        upperTeeth.addBox(-6, 0, -13, 12, 3, 12);
        lowerTeeth.addBox(-6, -4, -13, 12, 3, 12);
        upperMouthOverlay.addBox(-6, 0, -13, 12, 0, 12, 0.02F);
        lowerMouthOverlay.addBox(-6, -1, -13, 12, 0, 12, 0.02F);

        upperTeeth.setRotationPoint(0, 15, 7);
        lowerTeeth.setRotationPoint(0, 15, 7);
        upperMouthOverlay.setRotationPoint(0, 15, 7);
        lowerMouthOverlay.setRotationPoint(0, 15, 7);
    }

    @Override
    public void setRotationAngles(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void setLivingAnimations(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (mimic.ticksInAir > 0) {
            upperTeeth.rotateAngleX = upperMouthOverlay.rotateAngleX = Math.max(-60, (mimic.ticksInAir - 1 + partialTicks) * -6) * 0.0174533F;
            lowerTeeth.rotateAngleX = lowerMouthOverlay.rotateAngleX = Math.min(30, (mimic.ticksInAir - 1 + partialTicks) * 3) * 0.0174533F;
        } else {
            upperTeeth.rotateAngleX = upperMouthOverlay.rotateAngleX = 0;
            lowerTeeth.rotateAngleX = lowerMouthOverlay.rotateAngleX = 0;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        upperTeeth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lowerTeeth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        upperMouthOverlay.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lowerMouthOverlay.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
