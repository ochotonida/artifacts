package artifacts.client.render.curio.model.head;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DrinkingHatModel extends HatModel {

    public DrinkingHatModel() {
        super(RenderType::entityTranslucent, 64, 32);

        ModelRenderer straws = new ModelRenderer(this);
        straws.xRot = 45 * (float) Math.PI / 180;
        head.addChild(straws);

        // hat shade
        head.texOffs(32, 11);
        head.addBox(-4, -6, -8, 8, 1, 4);

        // cans
        head.texOffs(32, 0);
        head.addBox(4, -11, -1, 3, 6, 3);
        head.texOffs(44, 0);
        head.addBox(-7, -11, -1, 3, 6, 3);

        // straws
        straws.texOffs(0, 16);
        straws.addBox(5, -4, -3, 1, 1, 8);
        straws.texOffs(18, 16);
        straws.addBox(-6, -4, -3, 1, 1, 8);

        // straw middle
        head.texOffs(32, 9);
        head.addBox(-6, -1, -5, 12, 1, 1);
    }
}
