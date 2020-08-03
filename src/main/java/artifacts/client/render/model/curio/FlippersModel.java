package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class FlippersModel extends BipedModel<LivingEntity> {

    public FlippersModel() {
        super(0.5F, 0, 64, 96);

        setVisible(false);
        bipedLeftLeg.showModel = true;
        bipedRightLeg.showModel = true;

        RendererModel flipperLeft = new RendererModel(this, 0, 32);
        RendererModel flipperRight = new RendererModel(this, 0, 52);
        flipperLeft.addBox(-2, 11.5F, -16, 9, 1, 20);
        flipperRight.addBox(-7, 11.5F, -16, 9, 1, 20);
        bipedLeftLeg.addChild(flipperLeft);
        bipedRightLeg.addChild(flipperRight);
    }
}
