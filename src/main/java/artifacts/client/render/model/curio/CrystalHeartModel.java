package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class CrystalHeartModel extends BipedModel<LivingEntity> {

    public CrystalHeartModel() {
        super(0.5F, 0, 32, 32);

        bipedBody = new RendererModel(this, 0, 0);
        RendererModel heart1 = new RendererModel(this, 0, 16);
        RendererModel heart2 = new RendererModel(this, 6, 16);
        RendererModel heart3 = new RendererModel(this, 0, 20);
        RendererModel heart4 = new RendererModel(this, 4, 20);
        RendererModel heart5 = new RendererModel(this, 8, 20);

        bipedBody.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        heart1.addBox(-2.5F, 0, 0, 2, 3, 1);
        heart2.addBox(0.5F, 0, 0, 2, 3, 1);
        heart3.addBox(-0.5F, 1, 0, 1, 4, 1);
        heart4.addBox(-1.5F, 3, 0, 1, 1, 1);
        heart5.addBox(0.5F, 3, 0, 1, 1, 1);

        heart1.setRotationPoint(2.5F, 9, -3);

        heart1.addChild(heart2);
        heart1.addChild(heart3);
        heart1.addChild(heart4);
        heart1.addChild(heart5);
        bipedBody.addChild(heart1);

        setVisible(false);
        bipedBody.showModel = true;
    }
}
