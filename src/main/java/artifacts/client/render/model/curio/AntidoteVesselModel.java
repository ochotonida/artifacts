package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class AntidoteVesselModel extends BipedModel<LivingEntity> {

    public AntidoteVesselModel() {
        super(0.5F, 0, 32, 32);

        bipedBody = new RendererModel(this, 0, 0);
        RendererModel jar = new RendererModel(this, 0, 16);
        RendererModel lid = new RendererModel(this, 0, 26);

        bipedBody.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        jar.addBox(-2, 0, -2, 4, 6, 4);
        lid.addBox(-1, -1, -1, 2, 1, 2);
        jar.setRotationPoint(4, 9, -3);
        jar.rotateAngleY = -0.5F;

        jar.addChild(lid);
        bipedBody.addChild(jar);

        setVisible(false);
        bipedBody.showModel = true;
    }
}
