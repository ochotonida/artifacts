package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class ObsidianSkullModel extends BipedModel<LivingEntity> {

    public ObsidianSkullModel() {
        super(0.5F, 0, 32, 32);

        bipedBody = new RendererModel(this, 0, 0);
        RendererModel skull = new RendererModel(this, 0, 16);

        RendererModel tooth1 = new RendererModel(this, 18, 16);
        RendererModel tooth2 = new RendererModel(this, 18, 19);

        bipedBody.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        skull.addBox(-2.5F, 0, 0, 5, 3, 4);
        tooth1.addBox(-1.5F, 3, 0, 1, 1, 2);
        tooth2.addBox(0.5F, 3, 0, 1, 1, 2);
        skull.setRotationPoint(4.5F, 9, -4);

        skull.rotateAngleY = -0.5F;

        skull.addChild(tooth1);
        skull.addChild(tooth2);

        bipedBody.addChild(skull);

        setVisible(false);
        bipedBody.showModel = true;
    }
}
