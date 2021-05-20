package artifacts.client.render.curio.model.hands;

public class GloveModel extends HandsModel {

    public GloveModel(boolean smallArms) {
        this(smallArms, 32, 32);
    }

    public GloveModel(boolean smallArms, int textureWidth, int textureHeight) {
        super(textureWidth, textureHeight);

        if (smallArms) {
            leftArm.setPos(5, 2.5F, 0);
            rightArm.setPos(-5, 2.5F, 0);

            // arms
            leftArm.texOffs(0, 0);
            leftArm.addBox(-1, -2, -2, 3, 12, 4, 0.5F);
            rightArm.texOffs(16, 0);
            rightArm.addBox(-2, -2, -2, 3, 12, 4, 0.5F);

            // sleeves
            leftArm.texOffs(0, 16);
            leftArm.addBox(-1, -2, -2, 3, 12, 4, 0.5F + 0.25F);
            rightArm.texOffs(16, 16);
            rightArm.addBox(-2, -2, -2, 3, 12, 4, 0.5F + 0.25F);
        } else {
            leftArm.setPos(5, 2, 0);
            rightArm.setPos(-5, 2, 0);

            // arms
            leftArm.texOffs(0, 0);
            leftArm.addBox(-1, -2, -2, 4, 12, 4, 0.5F);
            rightArm.texOffs(16, 0);
            rightArm.addBox(-3, -2, -2, 4, 12, 4, 0.5F);

            // sleeves
            leftArm.texOffs(0, 16);
            leftArm.addBox(-1, -2, -2, 4, 12, 4, 0.5F + 0.25F);
            rightArm.texOffs(16, 16);
            rightArm.addBox(-3, -2, -2, 4, 12, 4, 0.5F + 0.25F);
        }
    }
}
