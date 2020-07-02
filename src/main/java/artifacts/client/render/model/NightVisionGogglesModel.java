package artifacts.client.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class NightVisionGogglesModel extends EntityModel<LivingEntity> {

    public ModelRenderer goggles;
    public ModelRenderer eyeLeftOverlay;
    public ModelRenderer eyeRightOverlay;

    public NightVisionGogglesModel() {
        goggles = new ModelRenderer(this, 0, 21);
        eyeLeftOverlay = new ModelRenderer(this, 20, 16);
        eyeRightOverlay = new ModelRenderer(this, 30, 16);

        ModelRenderer headBand = new ModelRenderer(this, 0, 0);
        ModelRenderer eyeLeft = new ModelRenderer(this, 0, 16);
        ModelRenderer eyeRight = new ModelRenderer(this, 10, 16);

        goggles.addBox(-4, -6, -5 + 0.05F, 8, 4, 1);
        eyeLeftOverlay.addBox(1 + 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        eyeRightOverlay.addBox(-3 - 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        eyeLeft.addBox(1 + 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        eyeRight.addBox(-3 - 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        headBand.addBox(-4, -8, -4, 8, 8, 8, 0.9F);

        goggles.addChild(eyeLeft);
        goggles.addChild(eyeRight);
        goggles.addChild(headBand);
    }

    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        goggles.render(matrixStack, vertexBuilder, light, overlay);
        eyeLeftOverlay.render(matrixStack, vertexBuilder, 0xF000F0, overlay);
        eyeRightOverlay.render(matrixStack, vertexBuilder, 0xF000F0, overlay);
    }
}
