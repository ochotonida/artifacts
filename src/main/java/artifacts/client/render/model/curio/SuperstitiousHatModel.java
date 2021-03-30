package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SuperstitiousHatModel extends BipedModel<LivingEntity> {

    public SuperstitiousHatModel() {
        super(0, 0, 64, 32);
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4, -16, -4, 8, 8, 8);
        ModelRenderer brim = new ModelRenderer(this, 0, 16);
        brim.addBox(-5, -9, -5, 10, 1, 10);
        head.addChild(brim);
        setAllVisible(false);
        head.visible = true;
    }
}
