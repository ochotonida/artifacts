package artifacts.client.model.entity;

import artifacts.common.entity.EntityMimic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelMimic extends ModelBase {

    public ModelRenderer chestLid;
    public ModelRenderer chestBelow;
    public ModelRenderer chestKnob;
    public ModelRenderer chestLidTeeth;
    public ModelRenderer chestBelowTeeth;

    public ModelMimic() {
        textureWidth = 128;
        textureHeight = 64;

        chestBelow = new ModelRenderer(this, 0, 19);
        chestBelowTeeth = new ModelRenderer(this, 56, 13);
        chestLid = new ModelRenderer(this, 0, 0);
        chestLidTeeth = new ModelRenderer(this, 56, 0);
        chestKnob = new ModelRenderer(this, 0, 0);

        chestBelow.setRotationPoint(0, 14, 7);
        chestBelowTeeth.setRotationPoint(0, 14, 7);
        chestLid.setRotationPoint(0, 15, 7);
        chestLidTeeth.setRotationPoint(0, 15, 7);
        chestKnob.setRotationPoint(0, 15, 7);

        chestBelow.addBox(-7, 0, -14, 14, 10, 14);
        chestBelowTeeth.addBox(-6, -1, -13, 12, 1, 12);
        chestLid.addBox(-7, -5, -14, 14, 5, 14);
        chestLidTeeth.addBox(-6, 0, -13, 12, 1, 12);
        chestKnob.addBox(-1, -2, -15, 2, 4, 1);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        chestKnob.render(scale);
        chestBelow.render(scale);
        chestBelowTeeth.render(scale);
        chestLid.render(scale);
        chestLidTeeth.render(scale);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTickTime);

        if (((EntityMimic) entity).ticksInAir > 0) {
            float angle = Math.min(60, (((EntityMimic) entity).ticksInAir - 1 + partialTickTime) * 6);
            chestLid.rotateAngleX = -angle * 0.0174533F;
            chestLidTeeth.rotateAngleX = -angle * 0.0174533F;
            chestKnob.rotateAngleX = -angle * 0.0174533F;
        } else {
            chestLid.rotateAngleX = 0;
            chestLidTeeth.rotateAngleX = 0;
            chestKnob.rotateAngleX = 0;
        }

        if (((EntityMimic) entity).ticksInAir > 0) {
            float angle = Math.max(-30, (((EntityMimic) entity).ticksInAir - 1 + partialTickTime) * -3F);
            chestBelow.rotateAngleX = -angle * 0.0174533F;
            chestBelowTeeth.rotateAngleX = -angle * 0.0174533F;
        } else {
            chestBelow.rotateAngleX = 0;
            chestBelowTeeth.rotateAngleX = 0;
        }
    }
}
