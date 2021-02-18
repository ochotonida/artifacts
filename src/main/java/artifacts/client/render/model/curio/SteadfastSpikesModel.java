package artifacts.client.render.model.curio;


import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SteadfastSpikesModel extends PlayerModel<LivingEntity> {

    public SteadfastSpikesModel() {
        super(0.5F, false);

        ModelRenderer clawLeft1 = new ModelRenderer(this, 32, 0);
        ModelRenderer clawRight1 = new ModelRenderer(this, 43, 0);
        ModelRenderer clawLeft2 = new ModelRenderer(this, 32, 8);
        ModelRenderer clawRight2 = new ModelRenderer(this, 43, 8);
        clawLeft1.addBox(-1.5F, 9, -7, 1, 3, 5);
        clawRight1.addBox(-1.5F, 9, -7, 1, 3, 5);
        clawLeft2.addBox(0.5F, 9, -7, 1, 3, 5);
        clawRight2.addBox(0.5F, 9, -7, 1, 3, 5);
        bipedLeftLeg.addChild(clawLeft1);
        bipedRightLeg.addChild(clawRight1);
        bipedLeftLeg.addChild(clawLeft2);
        bipedRightLeg.addChild(clawRight2);

        setVisible(false);
        bipedLeftLeg.showModel = true;
        bipedRightLeg.showModel = true;
        bipedLeftLegwear.showModel = true;
        bipedRightLegwear.showModel = true;
    }
}
