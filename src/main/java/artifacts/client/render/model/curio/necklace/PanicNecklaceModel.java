package artifacts.client.render.model.curio.necklace;

public class PanicNecklaceModel extends CenteredNecklaceModel {

    public PanicNecklaceModel() {
        // gem top
        body.texOffs(52, 0);
        body.addBox(-2.5F, 5.5F, -5, 2, 2, 1);
        body.texOffs(58, 0);
        body.addBox(0.5F, 5.5F, -5, 2, 2, 1);

        // gem middle
        body.texOffs(52, 3);
        body.addBox(-1.5F, 6.5F, -5, 3, 2, 1);

        // gem bottom
        body.texOffs(60, 4);
        body.addBox(-0.5F, 8.5F, -5, 1, 1, 1);
    }
}
