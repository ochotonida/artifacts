package artifacts.client.render.model.curio.feet;

public abstract class SleevedLegsModel extends LegsModel {

    public SleevedLegsModel(float delta, int textureWidth, int textureHeight) {
        super(delta, textureWidth, textureHeight);

        // pants sleeves
        leftLeg.texOffs(0, 16);
        leftLeg.addBox(-2, 0, -2, 4, 12, 4, delta + 0.25F);
        rightLeg.texOffs(16, 16);
        rightLeg.addBox(-2, 0, -2, 4, 12, 4, delta + 0.25F);
    }
}
