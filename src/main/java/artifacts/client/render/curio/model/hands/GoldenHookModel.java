package artifacts.client.render.curio.model.hands;

public class GoldenHookModel extends GloveModel {

    public GoldenHookModel(boolean smallArms) {
        super(smallArms, 64, 32);

        // hook
        leftArm.texOffs(32, 0);
        leftArm.addBox(smallArms ? -2 : -1.5F, 12, -0.5F, 5, 5, 1);
        rightArm.texOffs(48, 0);
        rightArm.addBox(smallArms ? -3 : -3.5F, 12, -0.5F, 5, 5, 1);

        // hook base
        leftArm.texOffs(32, 6);
        leftArm.addBox(smallArms ? 0 : 0.5F, 10, -0.5F, 1, 2, 1);
        rightArm.texOffs(48, 6);
        rightArm.addBox(smallArms ? -1 : -1.5F, 10, -0.5F, 1, 2, 1);
    }
}
