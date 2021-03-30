package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ObsidianSkullModel extends BipedModel<LivingEntity> {

    public ObsidianSkullModel() {
        super(0.5F, 0, 32, 32);

        body = new ModelRenderer(this, 0, 0);
        ModelRenderer skull = new ModelRenderer(this, 0, 16);

        ModelRenderer tooth1 = new ModelRenderer(this, 18, 16);
        ModelRenderer tooth2 = new ModelRenderer(this, 18, 19);

        body.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        skull.addBox(-2.5F, 0, 0, 5, 3, 4);
        tooth1.addBox(-1.5F, 3, 0, 1, 1, 2);
        tooth2.addBox(0.5F, 3, 0, 1, 1, 2);
        skull.setPos(4.5F, 9, -4);

        skull.yRot = -0.5F;

        skull.addChild(tooth1);
        skull.addChild(tooth2);

        body.addChild(skull);

        setAllVisible(false);
        body.visible = true;
    }
}
