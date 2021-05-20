package artifacts.client.render.curio.model.feet;

public class SteadfastSpikesModel extends SleevedLegsModel {

    public SteadfastSpikesModel() {
        super(0.5F, 64, 32);

        // claws
        leftLeg.texOffs(32, 0);
        leftLeg.addBox(-1.5F, 9, -7, 1, 3, 5);
        rightLeg.texOffs(43, 0);
        rightLeg.addBox(-1.5F, 9, -7, 1, 3, 5);
        leftLeg.texOffs(32, 8);
        leftLeg.addBox(0.5F, 9, -7, 1, 3, 5);
        rightLeg.texOffs(43, 8);
        rightLeg.addBox(0.5F, 9, -7, 1, 3, 5);
    }
}
