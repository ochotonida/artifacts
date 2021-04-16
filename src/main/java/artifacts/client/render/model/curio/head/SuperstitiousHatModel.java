package artifacts.client.render.model.curio.head;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SuperstitiousHatModel extends BipedModel<LivingEntity> {

    public SuperstitiousHatModel() {
        super(0, 0, 64, 32);
        setAllVisible(false);

        head = new ModelRenderer(this);

        // hat
        head.texOffs(0, 0);
        head.addBox(-4, -16, -4, 8, 8, 8);

        // brim
        head.texOffs(0, 16);
        head.addBox(-5, -9, -5, 10, 1, 10);
    }
}
