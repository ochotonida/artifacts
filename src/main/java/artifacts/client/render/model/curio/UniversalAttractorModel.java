package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class UniversalAttractorModel extends BipedModel<LivingEntity> {

    public UniversalAttractorModel() {
        super(0.5F, 0, 32, 32);

        bipedBody = new ModelRenderer(this, 0, 0);
        ModelRenderer magnet = new ModelRenderer(this, 0, 16);

        ModelRenderer magnet1 = new ModelRenderer(this, 0, 19);
        ModelRenderer magnet2 = new ModelRenderer(this, 6, 19);

        bipedBody.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        magnet.addBox(0, 9, -3, 5, 2, 1);
        magnet1.addBox(0, 11, -3, 2, 4, 1);
        magnet2.addBox(3, 11, -3, 2, 4, 1);

        magnet.addChild(magnet1);
        magnet.addChild(magnet2);
        bipedBody.addChild(magnet);

        setVisible(false);
        bipedBody.showModel = true;
    }
}
