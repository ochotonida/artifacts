package artifacts.client.render.curio.model.feet;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public abstract class LegsModel extends BipedModel<LivingEntity> {

    public LegsModel(float delta, int textureWidth, int textureHeight) {
        super(0, 0, textureWidth, textureHeight);
        setAllVisible(false);

        leftLeg = new ModelRenderer(this);
        rightLeg = new ModelRenderer(this);

        // legs
        leftLeg.texOffs(0, 0);
        leftLeg.addBox(-2, 0, -2, 4, 12, 4, delta);
        rightLeg.texOffs(16, 0);
        rightLeg.addBox(-2, 0, -2, 4, 12, 4, delta);
    }
}
