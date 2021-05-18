package artifacts.client.render.model.curio.belt;

import net.minecraft.client.renderer.model.ModelRenderer;

public class AntidoteVesselModel extends BeltModel {

    public AntidoteVesselModel() {
        ModelRenderer jar = new ModelRenderer(this);
        jar.setPos(4, 9, -3);
        jar.yRot = -0.5F;
        body.addChild(jar);

        // jar
        jar.texOffs(0, 16);
        jar.addBox(-2, 0, -2, 4, 6, 4);

        // lid
        jar.texOffs(0, 26);
        jar.addBox(-1, -1, -1, 2, 1, 2);
    }
}
