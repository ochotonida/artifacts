package artifacts.client.render.curio.model.belt;

public class ObsidianSkullModel extends BeltModel {

    public ObsidianSkullModel() {
        super(4.5F, -4F, -0.5F);

        // skull
        charm.texOffs(0, 16);
        charm.addBox(-2.5F, 0, 0, 5, 3, 4);

        // teeth
        charm.texOffs(18, 16);
        charm.addBox(-1.5F, 3, 0, 1, 1, 2);
        charm.texOffs(18, 19);
        charm.addBox(0.5F, 3, 0, 1, 1, 2);
    }
}
