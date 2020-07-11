package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class NightVisionGogglesModel extends BipedModel<LivingEntity> {

    public NightVisionGogglesModel() {
        super(0.5F, 0, 64, 64);

        setVisible(false);
        bipedHead.showModel = true;
        bipedHeadwear.showModel = true;

        ModelRenderer goggles = new ModelRenderer(this, 0, 37);
        ModelRenderer eyeLeft = new ModelRenderer(this, 0, 32);
        ModelRenderer eyeRight = new ModelRenderer(this, 10, 32);

        goggles.addBox(-4, -6, -5 + 0.05F, 8, 4, 1);
        eyeLeft.addBox(1.5F, -5, -8 + 0.05F, 2, 2, 3);
        eyeRight.addBox(-3.5F, -5, -8 + 0.05F, 2, 2, 3);

        bipedHead.addChild(goggles);
        bipedHead.addChild(eyeLeft);
        bipedHead.addChild(eyeRight);
    }
}
