package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.RendererModel;

public class ClawsModel extends GloveModel {

    public ClawsModel(boolean smallArms) {
        super(smallArms);

        RendererModel clawLeftUpper1 = new RendererModel(this, 0, 6);
        RendererModel clawRightUpper1 = new RendererModel(this, 0, 38);
        RendererModel clawLeftUpper2 = new RendererModel(this, 8, 6);
        RendererModel clawRightUpper2 = new RendererModel(this, 8, 38);
        RendererModel clawLeftLower1 = new RendererModel(this, 0, 0);
        RendererModel clawRightLower1 = new RendererModel(this, 0, 32);
        RendererModel clawLeftLower2 = new RendererModel(this, 8, 0);
        RendererModel clawRightLower2 = new RendererModel(this, 8, 32);
        clawLeftUpper1.addBox(3, 10, -1.5F, 1, 4, 1);
        clawRightUpper1.addBox(-4, 10, -1.5F, 1, 4, 1);
        clawLeftUpper2.addBox(3, 10, 0.5F, 1, 4, 1);
        clawRightUpper2.addBox(-4, 10, 0.5F, 1, 4, 1);
        clawLeftLower1.addBox(0, 10, -1.5F, 3, 5, 1);
        clawRightLower1.addBox(-3, 10, -1.5F, 3, 5, 1);
        clawLeftLower2.addBox(0, 10, 0.5F, 3, 5, 1);
        clawRightLower2.addBox(-3, 10, 0.5F, 3, 5, 1);
        bipedLeftArm.addChild(clawLeftUpper1);
        bipedRightArm.addChild(clawRightUpper1);
        bipedLeftArm.addChild(clawLeftUpper2);
        bipedRightArm.addChild(clawRightUpper2);
        bipedLeftArm.addChild(clawLeftLower1);
        bipedRightArm.addChild(clawRightLower1);
        bipedLeftArm.addChild(clawLeftLower2);
        bipedRightArm.addChild(clawRightLower2);
    }
}
