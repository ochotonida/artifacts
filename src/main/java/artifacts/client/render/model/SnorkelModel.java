package artifacts.client.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class SnorkelModel extends EntityModel<LivingEntity> {

    public ModelRenderer snorkel;
    public ModelRenderer goggles;
    public ModelRenderer gogglesOverlay;
    protected boolean showGoggles;

    public SnorkelModel() {
        textureHeight = 32;
        textureWidth = 64;
        snorkel = new ModelRenderer(this, 0, 14);
        goggles = new ModelRenderer(this, 28, 0);
        gogglesOverlay = new ModelRenderer(this, 28, 16);
        ModelRenderer snorkelTubeThing = new ModelRenderer(this, 0, 0);
        snorkel.addChild(snorkelTubeThing);

        snorkel.addBox(-2, -1.5F, -6, 8, 2, 2, 0);
        goggles.addBox(-4, -7, -4, 8, 8, 8, 1);
        gogglesOverlay.addBox(-4, -7, -4, 8, 8, 8, 1);
        snorkelTubeThing.addBox(4.01F, -5, -3, 2, 2, 12, 0);
        snorkelTubeThing.rotateAngleX = 0.7853F;
    }

    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void setModelVisibilities(LivingEntity entity) {
        showGoggles = !entity.hasItemInSlot(EquipmentSlotType.HEAD);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        snorkel.render(matrixStack, vertexBuilder, light, overlay);

        if (showGoggles) {
            goggles.render(matrixStack, vertexBuilder, light, overlay);

            //gogglesOverlay.render(matrixStack, vertexBuilder, light, overlay);
        }
    }
}
