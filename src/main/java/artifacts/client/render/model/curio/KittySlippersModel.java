package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class KittySlippersModel extends BipedModel<LivingEntity> {

    public KittySlippersModel() {
        super(0.51F);
        ModelRenderer head1 = new ModelRenderer(this, 0, 0);
        ModelRenderer head2 = new ModelRenderer(this, 32, 0);
        ModelRenderer earLeft1 = new ModelRenderer(this, 0, 9);
        ModelRenderer earLeft2 = new ModelRenderer(this, 32, 9);
        ModelRenderer earRight1 = new ModelRenderer(this, 0, 9);
        ModelRenderer earRight2 = new ModelRenderer(this, 32, 9);
        ModelRenderer nose1 = new ModelRenderer(this, 12, 9);
        ModelRenderer nose2 = new ModelRenderer(this, 44, 9);
        ModelRenderer bipedLeftLegwear = new ModelRenderer(this, 16, 16);
        ModelRenderer bipedRightLegwear = new ModelRenderer(this, 48, 16);

        head1.addBox(-2.5F, 8.51F, -7.01F, 5, 4, 5);
        head2.addBox(-2.5F, 8.51F, -7, 5, 4, 5);
        earLeft1.addBox(-2, 7.51F, -4, 1, 1, 2);
        earLeft2.addBox(-2, 7.51F, -4, 1, 1, 2);
        earRight1.addBox(1, 7.51F, -4, 1, 1, 2);
        earRight2.addBox(1, 7.51F, -4, 1, 1, 2);
        nose1.addBox(-1.5F, 10.51F, -8, 3, 2, 1);
        nose2.addBox(-1.5F, 10.51F, -8, 3, 2, 1);
        bipedLeftLegwear.addBox(-2, 0, -2, 4, 12, 4, 0.75F);
        bipedRightLegwear.addBox(-2, 0, -2, 4, 12, 4, 0.75F);

        leftLeg.addChild(head1);
        rightLeg.addChild(head2);
        leftLeg.addChild(nose1);
        rightLeg.addChild(nose2);
        leftLeg.addChild(earLeft1);
        leftLeg.addChild(earRight1);
        rightLeg.addChild(earLeft2);
        rightLeg.addChild(earRight2);
        leftLeg.addChild(bipedLeftLegwear);
        rightLeg.addChild(bipedRightLegwear);

        setAllVisible(false);
        leftLeg.visible = rightLeg.visible = true;
    }
}
