package artifacts.client.render.model.curio.belt;

import net.minecraft.client.renderer.model.ModelRenderer;

public class ObsidianSkullModel extends BeltModel {

    public ObsidianSkullModel() {
        ModelRenderer skull = new ModelRenderer(this);
        skull.setPos(4.5F, 9, -4);
        skull.yRot = -0.5F;
        body.addChild(skull);

        // skull
        skull.texOffs(0, 16);
        skull.addBox(-2.5F, 0, 0, 5, 3, 4);

        // teeth
        skull.texOffs(18, 16);
        skull.addBox(-1.5F, 3, 0, 1, 1, 2);
        skull.texOffs(18, 19);
        skull.addBox(0.5F, 3, 0, 1, 1, 2);
    }
}
