package artifacts.client.render.model.curio.feet;

public class KittySlippersModel extends SlippersModel {

    public KittySlippersModel() {
        // ears
        leftLeg.texOffs(32, 9);
        leftLeg.addBox(-2, 7.51F, -4, 1, 1, 2);
        rightLeg.texOffs(32, 25);
        rightLeg.addBox(-2, 7.51F, -4, 1, 1, 2);
        leftLeg.texOffs(38, 9);
        leftLeg.addBox(1, 7.51F, -4, 1, 1, 2);
        rightLeg.texOffs(38, 25);
        rightLeg.addBox(1, 7.51F, -4, 1, 1, 2);

        // nose
        leftLeg.texOffs(44, 9);
        leftLeg.addBox(-1.5F, 10.51F, -8, 3, 2, 1);
        rightLeg.texOffs(44, 25);
        rightLeg.addBox(-1.5F, 10.51F, -8, 3, 2, 1);
    }
}
