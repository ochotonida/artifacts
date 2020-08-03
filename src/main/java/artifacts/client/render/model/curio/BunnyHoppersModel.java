package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class BunnyHoppersModel extends BipedModel<LivingEntity> {

    public BunnyHoppersModel() {
        super(0.51F);
        RendererModel head1 = new RendererModel(this, 0, 0);
        RendererModel head2 = new RendererModel(this, 32, 0);
        RendererModel earLeft1 = new RendererModel(this, 20, 0);
        RendererModel earLeft2 = new RendererModel(this, 52, 0);
        RendererModel earRight1 = new RendererModel(this, 26, 0);
        RendererModel earRight2 = new RendererModel(this, 58, 0);
        RendererModel bipedLeftLegwear = new RendererModel(this, 16, 16);
        RendererModel bipedRightLegwear = new RendererModel(this, 48, 16);
        RendererModel nose1 = new RendererModel(this, 0, 9);
        RendererModel nose2 = new RendererModel(this, 32, 9);
        RendererModel tail1 = new RendererModel(this, 20, 6);
        RendererModel tail2 = new RendererModel(this, 52, 6);

        head1.addBox(-2.5F, 8.51F, -7.01F, 5, 4, 5);
        head2.addBox(-2.5F, 8.51F, -7, 5, 4, 5);
        earRight1.addBox(1.15F, 3.51F, -3.01F, 2, 5, 1);
        earRight2.addBox(1.15F, 3.51F, -3, 2, 5, 1);
        earRight1.rotateAngleY = 0.2617994F;
        earRight2.rotateAngleY = 0.2617994F;
        earLeft1.addBox(-3.15F, 3.51F, -3.01F, 2, 5, 1);
        earLeft2.addBox(-3.15F, 3.51F, -3, 2, 5, 1);
        earLeft1.rotateAngleY = -0.2617994F;
        earLeft2.rotateAngleY = -0.2617994F;
        bipedLeftLegwear.addBox(-2, 0, -2, 4, 12, 4, 0.75F);
        bipedRightLegwear.addBox(-2, 0, -2, 4, 12, 4, 0.75F);
        nose1.addBox(-0.5F, 10, -7.5F, 1, 1, 1);
        nose2.addBox(-0.5F, 10, -7.5F, 1, 1, 1);
        tail1.addBox(-1, 9, 2, 2, 2, 2);
        tail2.addBox(-1, 9, 2, 2, 2, 2);

        bipedLeftLeg.addChild(head1);
        bipedRightLeg.addChild(head2);
        bipedLeftLeg.addChild(earRight1);
        bipedRightLeg.addChild(earRight2);
        bipedLeftLeg.addChild(earLeft1);
        bipedRightLeg.addChild(earLeft2);
        bipedLeftLeg.addChild(bipedLeftLegwear);
        bipedRightLeg.addChild(bipedRightLegwear);
        bipedLeftLeg.addChild(nose1);
        bipedRightLeg.addChild(nose2);
        bipedLeftLeg.addChild(tail1);
        bipedRightLeg.addChild(tail2);

        setVisible(false);
        bipedLeftLeg.showModel = bipedRightLeg.showModel = true;
    }
}
