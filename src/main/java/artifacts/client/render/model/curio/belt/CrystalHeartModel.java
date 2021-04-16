package artifacts.client.render.model.curio.belt;

import net.minecraft.client.renderer.RenderType;

public class CrystalHeartModel extends BeltModel {

    public CrystalHeartModel() {
        super(RenderType::entityTranslucent);

        // heart parts
        body.texOffs(0, 16);
        body.addBox(0, 9, -3.01F, 2, 3, 1);
        body.texOffs(6, 16);
        body.addBox(3, 9, -3.01F, 2, 3, 1);
        body.texOffs(0, 20);
        body.addBox(2, 10, -3.01F, 1, 4, 1);
        body.texOffs(4, 20);
        body.addBox(1, 12, -3.01F, 1, 1, 1);
        body.texOffs(8, 20);
        body.addBox(3, 12, -3.01F, 1, 1, 1);
    }
}
