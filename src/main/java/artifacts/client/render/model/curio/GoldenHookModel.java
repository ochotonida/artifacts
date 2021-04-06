package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.model.ModelRenderer;

public class GoldenHookModel extends GloveModel {

    public GoldenHookModel(boolean smallArms) {
        super(smallArms);

        ModelRenderer hookRight = new ModelRenderer(this, 0, 0);
        ModelRenderer hookLeft = new ModelRenderer(this, 0, 9);
        ModelRenderer hookBaseRight = new ModelRenderer(this, 0, 6);
        ModelRenderer hookBaseLeft = new ModelRenderer(this, 0, 15);

        hookRight.addBox(smallArms ? -3 : -3.5F, 12, -0.5F, 5, 5, 1);
        hookLeft.addBox(smallArms ? -2 : -1.5F, 12, -0.5F, 5, 5, 1);
        hookBaseRight.addBox(smallArms ? -1 : -1.5F, 10, -0.5F, 1, 2, 1);
        hookBaseLeft.addBox(smallArms ? 0 : 0.5F, 10, -0.5F, 1, 2, 1);

        rightArm.addChild(hookRight);
        leftArm.addChild(hookLeft);
        rightArm.addChild(hookBaseRight);
        leftArm.addChild(hookBaseLeft);
    }
}
