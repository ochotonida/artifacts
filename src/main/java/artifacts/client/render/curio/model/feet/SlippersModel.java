package artifacts.client.render.curio.model.feet;

public abstract class SlippersModel extends SleevedLegsModel {

    public SlippersModel() {
        super(0.51F, 64, 32);

        // heads
        leftLeg.texOffs(32, 0);
        leftLeg.addBox(-2.5F, 8.51F, -7.01F, 5, 4, 5);
        rightLeg.texOffs(32, 16);
        rightLeg.addBox(-2.5F, 8.51F, -7, 5, 4, 5);
    }
}
