package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CrystalHeartModel extends BipedModel<LivingEntity> {

    public CrystalHeartModel() {
        super(RenderType::entityTranslucent, 0.5F, 0, 32, 32);

        body = new ModelRenderer(this, 0, 0);
        ModelRenderer heart1 = new ModelRenderer(this, 0, 16);
        ModelRenderer heart2 = new ModelRenderer(this, 6, 16);
        ModelRenderer heart3 = new ModelRenderer(this, 0, 20);
        ModelRenderer heart4 = new ModelRenderer(this, 4, 20);
        ModelRenderer heart5 = new ModelRenderer(this, 8, 20);

        body.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        heart1.addBox(-2.5F, 0, 0, 2, 3, 1);
        heart2.addBox(0.5F, 0, 0, 2, 3, 1);
        heart3.addBox(-0.5F, 1, 0, 1, 4, 1);
        heart4.addBox(-1.5F, 3, 0, 1, 1, 1);
        heart5.addBox(0.5F, 3, 0, 1, 1, 1);

        heart1.setPos(2.5F, 9, -3);

        heart1.addChild(heart2);
        heart1.addChild(heart3);
        heart1.addChild(heart4);
        heart1.addChild(heart5);
        body.addChild(heart1);

        setAllVisible(false);
        body.visible = true;
    }
}
