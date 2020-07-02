package artifacts.client.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class DrinkingHatModel extends EntityModel<LivingEntity> {

    public ModelRenderer hat;
    public ModelRenderer hatShade;
    public ModelRenderer straw;

    protected boolean showShade;
    protected boolean showHat;

    public DrinkingHatModel() {
        textureHeight = 32;
        textureWidth = 64;

        hat = new ModelRenderer(this, 26, 9);
        hatShade = new ModelRenderer(this, 0, 20);
        straw = new ModelRenderer(this, 0, 18);

        ModelRenderer canLeft = new ModelRenderer(this, 0, 9);
        ModelRenderer canRight = new ModelRenderer(this, 12, 9);
        ModelRenderer strawLeft = new ModelRenderer(this, 0, 0);
        ModelRenderer strawRight = new ModelRenderer(this, 17, 0);

        straw.addChild(canLeft);
        straw.addChild(canRight);
        straw.addChild(strawLeft);
        straw.addChild(strawRight);

        hat.addBox(-4, -8, -4, 8, 8, 8, 1);
        hatShade.addBox(-4, -6, -8, 8, 1, 4, 0);
        straw.addBox(-6, -1, -5, 12, 1, 1);
        canLeft.addBox(4, -11, -1, 3, 6, 3);
        canRight.addBox(-7, -11, -1, 3, 6, 3);
        strawLeft.addBox(5, -4, -3, 1, 1, 8);
        strawRight.addBox(-6, -4, -3, 1, 1, 8);

        strawLeft.rotateAngleX = 0.7853F;
        strawRight.rotateAngleX = 0.7853F;
    }

    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void setModelVisibilities(LivingEntity entity, boolean hasShade) {
        showHat = !entity.hasItemInSlot(EquipmentSlotType.HEAD);
        showShade = hasShade && showHat;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        straw.render(matrixStack, vertexBuilder, light, overlay);

        if (showShade) {
            hatShade.render(matrixStack, vertexBuilder, light, overlay);
        }
        if (showHat) {
            hat.render(matrixStack, vertexBuilder, light, overlay);
        }
    }
}
