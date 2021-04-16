package artifacts.client.render.model.curio.necklace;

public class CrossNecklaceModel extends CenteredNecklaceModel {

    public CrossNecklaceModel() {
        // cross vertical
        body.texOffs(52, 0);
        body.addBox(-0.5F, 4.5F, -5, 1, 4, 1);

        // cross horizontal
        body.texOffs(56, 0);
        body.addBox(-1.5F, 5.5F, -5, 3, 1, 1);
    }
}
