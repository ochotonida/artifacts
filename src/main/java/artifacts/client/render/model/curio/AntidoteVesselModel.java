package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class AntidoteVesselModel extends BipedModel<LivingEntity> {

    public AntidoteVesselModel() {
        super(0.5F, 0, 32, 32);

        body = new ModelRenderer(this, 0, 0);
        ModelRenderer jar = new ModelRenderer(this, 0, 16);
        ModelRenderer lid = new ModelRenderer(this, 0, 26);

        body.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        jar.addBox(-2, 0, -2, 4, 6, 4);
        lid.addBox(-1, -1, -1, 2, 1, 2);
        jar.setPos(4, 9, -3);
        jar.yRot = -0.5F;

        jar.addChild(lid);
        body.addChild(jar);

        setAllVisible(false);
        body.visible = true;
    }
}
