package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ShoesModel extends BipedModel<LivingEntity> {

    public ShoesModel(float delta) {
        super(0, 0, 32, 32);

        leftLeg = new ModelRenderer(this, 0, 0);
        rightLeg = new ModelRenderer(this, 16, 0);

        leftLeg.addBox(-2, 0, -2, 4, 12, 4, delta);
        rightLeg.addBox(-2, 0, -2, 4, 12, 4, delta);
        leftLeg.texOffs(0, 16);
        rightLeg.texOffs(16, 16);
        leftLeg.addBox(-2, 12 - 3 + delta * 3 / 4, -3F - delta * 5 / 4, 4, 3, 1, delta, delta / 4, delta / 4);
        rightLeg.addBox(-2, 12 - 3 + delta * 3 / 4, -3F - delta * 5 / 4, 4, 3, 1, delta, delta / 4, delta / 4);

        setAllVisible(false);
        leftLeg.visible = rightLeg.visible = true;
    }
}
