package artifacts.client.render.curio.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class WhoopeeCushionModel extends BipedModel<LivingEntity> {

    public WhoopeeCushionModel() {
        super(0, 0, 32, 16);
        setAllVisible(false);

        head = new ModelRenderer(this);

        // cushion
        head.texOffs(0, 0);
        head.addBox(-3, -10, -3, 6, 2, 6);

        // flap
        head.texOffs(0, 8);
        head.addBox(-2, -9.25F, 3, 4, 0, 4);
    }
}
