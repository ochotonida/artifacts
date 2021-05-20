package artifacts.client.render.curio.model.feet;

public class FlippersModel extends LegsModel {

    public FlippersModel() {
        super(0.5F, 64, 64);

        // flippers
        leftLeg.texOffs(0, 16);
        leftLeg.addBox(-2, 11.5F, -16, 9, 0, 20);
        rightLeg.texOffs(0, 36);
        rightLeg.addBox(-7, 11.5F, -16, 9, 0, 20);
    }
}
