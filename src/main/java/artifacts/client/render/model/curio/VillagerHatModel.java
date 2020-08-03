package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class VillagerHatModel extends BipedModel<LivingEntity> {

    public VillagerHatModel() {
        super(0.5F, 0, 32, 32);
        RendererModel brim = new RendererModel(this, 0, 16);
        brim.addBox(-8, -5.125F, -8, 16, 0, 16);
        bipedHead.addChild(brim);
        setVisible(false);
        bipedHead.showModel = true;
    }
}
