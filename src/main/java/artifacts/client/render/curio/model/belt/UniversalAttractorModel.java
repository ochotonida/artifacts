package artifacts.client.render.curio.model.belt;

public class UniversalAttractorModel extends BeltModel {

    public UniversalAttractorModel() {
        super(2.5F, -3, 0);

        // magnet
        charm.texOffs(0, 16);
        charm.addBox(-2.5F, 0, 0, 5, 2, 1);
        charm.texOffs(0, 19);
        charm.addBox(-2.5F, 2, 0, 2, 4, 1);
        charm.texOffs(6, 19);
        charm.addBox(0.5F, 2, 0, 2, 4, 1);
    }
}
