package artifacts.client.render.curio.model.belt;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class HeliumFlamingoModel extends BipedModel<LivingEntity> {

    public HeliumFlamingoModel() {
        super(0, 0, 64, 64);
        setAllVisible(false);

        body = new ModelRenderer(this);
        body.texOffs(16, 36).addBox(-1, 1, -14, 2, 3, 5);
        body.texOffs(0, 18).addBox(4, 9, -7, 4, 4, 14);
        body.texOffs(0, 0).addBox(-8, 9, -7, 4, 4, 14);
        body.texOffs(36, 0).addBox(-4, 9, 3, 8, 4, 4);
        body.texOffs(36, 8).addBox(-4, 9, -7, 8, 4, 4);
        body.texOffs(0, 36).addBox(-2, 1, -9, 4, 11, 4);
    }
}
