package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class SuperstitiousHatModel extends BipedModel<LivingEntity> {

    public SuperstitiousHatModel() {
        super(0, 0, 64, 32);
        bipedHead = new RendererModel(this, 0, 0);
        bipedHead.addBox(-4, -16, -4, 8, 8, 8);
        RendererModel brim = new RendererModel(this, 0, 16);
        brim.addBox(-5, -9, -5, 10, 1, 10);
        bipedHead.addChild(brim);
        setVisible(false);
        bipedHead.showModel = true;
    }
}
