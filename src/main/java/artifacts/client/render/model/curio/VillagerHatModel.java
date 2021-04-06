package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class VillagerHatModel extends BipedModel<LivingEntity> {

    public VillagerHatModel() {
        super(0.5F, 0, 32, 32);
        ModelRenderer brim = new ModelRenderer(this, 0, 16);
        brim.addBox(-8, -5.125F, -8, 16, 0, 16);
        head.addChild(brim);
        setAllVisible(false);
        head.visible = true;
    }
}
