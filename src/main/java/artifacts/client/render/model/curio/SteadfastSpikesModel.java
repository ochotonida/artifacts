package artifacts.client.render.model.curio;


import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SteadfastSpikesModel extends PlayerModel<LivingEntity> {

    public SteadfastSpikesModel() {
        super(0.5F, false);

        ModelRenderer clawLeftLower1 = new ModelRenderer(this, 32, 0);
        ModelRenderer clawRightLower1 = new ModelRenderer(this, 32, 32);
        ModelRenderer clawLeftLower2 = new ModelRenderer(this, 44, 0);
        ModelRenderer clawRightLower2 = new ModelRenderer(this, 44, 32);
        ModelRenderer clawLeftUpper1 = new ModelRenderer(this, 32, 8);
        ModelRenderer clawRightUpper1 = new ModelRenderer(this, 32, 40);
        ModelRenderer clawLeftUpper2 = new ModelRenderer(this, 44, 8);
        ModelRenderer clawRightUpper2 = new ModelRenderer(this, 44, 40);
        clawLeftLower1.addBox(-1.5F, 10, -7, 1, 3, 5);
        clawRightLower1.addBox(-1.5F, 10, -7, 1, 3, 5);
        clawLeftLower2.addBox(0.5F, 10, -7, 1, 3, 5);
        clawRightLower2.addBox(0.5F, 10, -7, 1, 3, 5);
        clawLeftUpper1.addBox(-1.5F, 9, -6, 1, 1, 4);
        clawRightUpper1.addBox(-1.5F, 9, -6, 1, 1, 4);
        clawLeftUpper2.addBox(0.5F, 9, -6, 1, 1, 4);
        clawRightUpper2.addBox(0.5F, 9, -6, 1, 1, 4);
        bipedLeftLeg.addChild(clawLeftLower1);
        bipedRightLeg.addChild(clawRightLower1);
        bipedLeftLeg.addChild(clawLeftLower2);
        bipedRightLeg.addChild(clawRightLower2);
        bipedLeftLeg.addChild(clawLeftUpper1);
        bipedRightLeg.addChild(clawRightUpper1);
        bipedLeftLeg.addChild(clawLeftUpper2);
        bipedRightLeg.addChild(clawRightUpper2);

        setVisible(false);
        bipedLeftLeg.showModel = true;
        bipedRightLeg.showModel = true;
        bipedLeftLegwear.showModel = true;
        bipedRightLegwear.showModel = true;
    }
}
