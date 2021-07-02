package artifacts.client.render.curio.model.belt;

import net.minecraft.client.renderer.RenderType;

public class CrystalHeartModel extends BeltModel {

    public CrystalHeartModel() {
        super(RenderType::entityTranslucent, 2.5F, -3.01F, 0);

        // heart parts
        charm.texOffs(0, 16);
        charm.addBox(-2.5F, 0, 0, 2, 3, 1);
        charm.texOffs(6, 16);
        charm.addBox(0.5F, 0, 0, 2, 3, 1);
        charm.texOffs(0, 20);
        charm.addBox(-0.5F, 1, 0, 1, 4, 1);
        charm.texOffs(4, 20);
        charm.addBox(-1.5F, 3, 0, 1, 1, 1);
        charm.texOffs(8, 20);
        charm.addBox(0.5F, 3, 0, 1, 1, 1);
    }
}
