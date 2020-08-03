package artifacts.client.render.model.entity;

import artifacts.common.entity.MimicEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class MimicModel extends EntityModel<MimicEntity> {

    public RendererModel lid;
    public RendererModel chest;
    public RendererModel knob;
    public RendererModel upperTeeth;
    public RendererModel lowerTeeth;

    public MimicModel() {
        textureWidth = 128;
        textureHeight = 64;

        chest = new RendererModel(this, 0, 19);
        lowerTeeth = new RendererModel(this, 56, 15);
        lid = new RendererModel(this, 0, 0);
        upperTeeth = new RendererModel(this, 56, 0);
        knob = new RendererModel(this, 0, 0);

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
    public void render(MimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        knob.render(scale);
        chest.render(scale);
        lowerTeeth.render(scale);
        lid.render(scale);
        upperTeeth.render(scale);
    }
}
