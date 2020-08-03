package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class RunningShoesModel extends BipedModel<LivingEntity> {

    public RunningShoesModel() {
        super(1, 0, 32, 32);

        bipedLeftLeg = new RendererModel(this, 0, 0);
        bipedRightLeg = new RendererModel(this, 16, 0);
        RendererModel leftShoeTip = new RendererModel(this, 0, 16);
        RendererModel rightShoeTip = new RendererModel(this, 16, 16);

        bipedLeftLeg.addBox(0, 0, -2, 4, 12, 4, 0.5F);
        bipedRightLeg.addBox(-4, 0, -2, 4, 12, 4, 0.5F);
        leftShoeTip.addBox(0, 9F, -3.625F, 4, 3, 1, 0.5F);
        rightShoeTip.addBox(-4, 9F, -3.625F, 4, 3, 1, 0.5F);

        bipedLeftLeg.addChild(leftShoeTip);
        bipedRightLeg.addChild(rightShoeTip);

        setVisible(false);
        bipedLeftLeg.showModel = bipedRightLeg.showModel = true;
    }
}
