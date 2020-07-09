package artifacts.client.render.model.entity;

import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MimicModel extends EntityModel<MimicEntity> {

    public ModelRenderer lid;
    public ModelRenderer chest;
    public ModelRenderer knob;
    public ModelRenderer upperTeeth;
    public ModelRenderer lowerTeeth;

    public MimicModel() {
        textureWidth = 128;
        textureHeight = 64;

        chest = new ModelRenderer(this, 0, 19);
        lowerTeeth = new ModelRenderer(this, 56, 15);
        lid = new ModelRenderer(this, 0, 0);
        upperTeeth = new ModelRenderer(this, 56, 0);
        knob = new ModelRenderer(this, 0, 0);

        chest.setRotationPoint(0, 14, 7);
        lowerTeeth.setRotationPoint(0, 14, 7);
        lid.setRotationPoint(0, 15, 7);
        upperTeeth.setRotationPoint(0, 15, 7);
        knob.setRotationPoint(0, 15, 7);

        chest.addBox(-7, 0, -14, 14, 10, 14);
        lowerTeeth.addBox(-6, -3, -13, 12, 3, 12);
        lid.addBox(-7, -5, -14, 14, 5, 14);
        upperTeeth.addBox(-6, 0, -13, 12, 3, 12);
        knob.addBox(-1, -2, -15, 2, 4, 1);
    }

    @Override
    public void setRotationAngles(MimicEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void setLivingAnimations(MimicEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (entity.ticksInAir > 0) {
            float angle = Math.min(60, (entity.ticksInAir - 1 + partialTicks) * 6);
            lid.rotateAngleX = -angle * 0.0174533F;
            upperTeeth.rotateAngleX = -angle * 0.0174533F;
            knob.rotateAngleX = -angle * 0.0174533F;
            angle = Math.max(-30, (entity.ticksInAir - 1 + partialTicks) * -3F);
            chest.rotateAngleX = -angle * 0.0174533F;
            lowerTeeth.rotateAngleX = -angle * 0.0174533F;
        } else {
            lid.rotateAngleX = 0;
            upperTeeth.rotateAngleX = 0;
            knob.rotateAngleX = 0;
            chest.rotateAngleX = 0;
            lowerTeeth.rotateAngleX = 0;
        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        knob.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        lowerTeeth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        lid.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        upperTeeth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
