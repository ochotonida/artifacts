package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.model.ModelRenderer;

public class ClawsModel extends GloveModel {

    public ClawsModel(boolean smallArms) {
        super(smallArms);

        ModelRenderer clawLeftUpper1 = new ModelRenderer(this, 0, 6);
        ModelRenderer clawRightUpper1 = new ModelRenderer(this, 0, 38);
        ModelRenderer clawLeftUpper2 = new ModelRenderer(this, 8, 6);
        ModelRenderer clawRightUpper2 = new ModelRenderer(this, 8, 38);
        ModelRenderer clawLeftLower1 = new ModelRenderer(this, 0, 0);
        ModelRenderer clawRightLower1 = new ModelRenderer(this, 0, 32);
        ModelRenderer clawLeftLower2 = new ModelRenderer(this, 8, 0);
        ModelRenderer clawRightLower2 = new ModelRenderer(this, 8, 32);
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
