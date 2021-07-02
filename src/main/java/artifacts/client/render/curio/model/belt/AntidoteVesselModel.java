package artifacts.client.render.curio.model.belt;

public class AntidoteVesselModel extends BeltModel {

    public AntidoteVesselModel() {
        super(4, -3, -0.5F);

        // jar
        charm.texOffs(0, 16);
        charm.addBox(-2, 0, -2, 4, 6, 4);

        // lid
        charm.texOffs(0, 26);
        charm.addBox(-1, -1, -1, 2, 1, 2);
    }
}
