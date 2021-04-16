package artifacts.client.render.model.curio.feet;

import net.minecraft.client.renderer.model.ModelRenderer;

public class BunnyHoppersModel extends SlippersModel {

    public BunnyHoppersModel() {
        ModelRenderer ear1Left = new ModelRenderer(this, 52, 0);
        ModelRenderer ear1Right = new ModelRenderer(this, 52, 16);
        ModelRenderer ear2Left = new ModelRenderer(this, 58, 0);
        ModelRenderer ear2Right = new ModelRenderer(this, 58, 16);
        ear1Left.yRot = -0.2617994F;
        ear1Right.yRot = -0.2617994F;
        ear2Left.yRot = 0.2617994F;
        ear2Right.yRot = 0.2617994F;
        leftLeg.addChild(ear1Left);
        rightLeg.addChild(ear1Right);
        leftLeg.addChild(ear2Left);
        rightLeg.addChild(ear2Right);

        ear1Left.addBox(-3.15F, 3.51F, -3.01F, 2, 5, 1);
        ear1Right.addBox(-3.15F, 3.51F, -3, 2, 5, 1);
        ear2Left.addBox(1.15F, 3.51F, -3.01F, 2, 5, 1);
        ear2Right.addBox(1.15F, 3.51F, -3, 2, 5, 1);

        // nose
        leftLeg.texOffs(32, 9);
        leftLeg.addBox(-0.5F, 10, -7.5F, 1, 1, 1);
        rightLeg.texOffs(32, 25);
        rightLeg.addBox(-0.5F, 10, -7.5F, 1, 1, 1);

        // tail
        leftLeg.texOffs(52, 6);
        leftLeg.addBox(-1, 9, 2, 2, 2, 2);
        rightLeg.texOffs(52, 22);
        rightLeg.addBox(-1, 9, 2, 2, 2, 2);
    }
}
