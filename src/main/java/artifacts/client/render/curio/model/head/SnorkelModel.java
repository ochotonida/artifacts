package artifacts.client.render.curio.model.head;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SnorkelModel extends HatModel {

    public SnorkelModel() {
        super(RenderType::entityTranslucent, 64, 32);

        ModelRenderer tube = new ModelRenderer(this);
        tube.xRot = 45 * (float) Math.PI / 180;
        head.addChild(tube);

        // horizontal tube
        head.texOffs(32, 0);
        head.addBox(-2, -1.5F, -6, 8, 2, 2);

        // diagonal tube
        tube.texOffs(0, 16);
        tube.addBox(4.01F, -5, -3, 2, 2, 12);
    }
}
