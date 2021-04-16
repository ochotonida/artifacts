package artifacts.client.render.model.curio.feet;

public class ShoesModel extends LegsModel {

    public ShoesModel(float delta) {
        super(delta, 32, 32);

        // shoe tip
        leftLeg.texOffs(0, 16);
        leftLeg.addBox(-2, 12 - 3 + delta * 3 / 4, -3F - delta * 5 / 4, 4, 3, 1, delta, delta / 4, delta / 4);
        rightLeg.texOffs(16, 16);
        rightLeg.addBox(-2, 12 - 3 + delta * 3 / 4, -3F - delta * 5 / 4, 4, 3, 1, delta, delta / 4, delta / 4);
    }
}
