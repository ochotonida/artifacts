package artifacts.client.render.model.curio.hands;

public class ClawsModel extends HandsModel {

    public ClawsModel(boolean smallArms) {
        super(32, 16);

        int smallArmsOffset = smallArms ? 1 : 0;

        // claw 1 lower
        leftArm.texOffs(0, 0);
        leftArm.addBox(-smallArmsOffset, 10, -1.5F, 3, 5, 1);
        rightArm.texOffs(8, 0);
        rightArm.addBox(-3 + smallArmsOffset, 10, -1.5F, 3, 5, 1);

        // claw 2 lower
        leftArm.texOffs(0, 6);
        leftArm.addBox(-smallArmsOffset, 10, 0.5F, 3, 5, 1);
        rightArm.texOffs(8, 6);
        rightArm.addBox(-3 + smallArmsOffset, 10, 0.5F, 3, 5, 1);

        // claw 1 upper
        leftArm.texOffs(16, 0);
        leftArm.addBox(3 - smallArmsOffset, 10, -1.5F, 1, 4, 1);
        rightArm.texOffs(20, 0);
        rightArm.addBox(-4 + smallArmsOffset, 10, -1.5F, 1, 4, 1);

        // claw 2 upper
        leftArm.texOffs(16, 6);
        leftArm.addBox(3 - smallArmsOffset, 10, 0.5F, 1, 4, 1);
        rightArm.texOffs(20, 6);
        rightArm.addBox(-4 + smallArmsOffset, 10, 0.5F, 1, 4, 1);
    }
}
