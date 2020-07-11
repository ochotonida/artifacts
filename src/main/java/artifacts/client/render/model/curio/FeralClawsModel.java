package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.model.ModelRenderer;

public class FeralClawsModel extends GloveModel {

    public FeralClawsModel(boolean smallArms) {
        super(smallArms);

        ModelRenderer clawLeft1 = new ModelRenderer(this, 0, 0);
        ModelRenderer clawRight1 = new ModelRenderer(this, 0, 32);
        ModelRenderer clawLeft2 = new ModelRenderer(this, 10, 0);
        ModelRenderer clawRight2 = new ModelRenderer(this, 10, 32);
        clawLeft1.addBox(0, 10, -1.5F, 4, 5, 1);
        clawRight1.addBox(-4, 10, -1.5F, 4, 5, 1);
        clawLeft2.addBox(0, 10, 0.5F, 4, 5, 1);
        clawRight2.addBox(-4, 10, 0.5F, 4, 5, 1);
        bipedLeftArm.addChild(clawLeft1);
        bipedRightArm.addChild(clawRight1);
        bipedLeftArm.addChild(clawLeft2);
        bipedRightArm.addChild(clawRight2);
    }
}
