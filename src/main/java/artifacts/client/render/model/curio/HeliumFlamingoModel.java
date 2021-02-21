package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class HeliumFlamingoModel extends BipedModel<LivingEntity> {

    public HeliumFlamingoModel() {
        super(0, 0, 64, 64);
        bipedBody = new ModelRenderer(this);

        ModelRenderer bone = new ModelRenderer(this);
        bone.setRotationPoint(0, 0, 0);
        bone.setTextureOffset(16, 36).addBox(-1, 1, -14, 2, 3, 5);
        bone.setTextureOffset(0, 18).addBox(4, 9, -7, 4, 4, 14);
        bone.setTextureOffset(0, 0).addBox(-8, 9, -7, 4, 4, 14);
        bone.setTextureOffset(36, 0).addBox(-4, 9, 3, 8, 4, 4);
        bone.setTextureOffset(36, 8).addBox(-4, 9, -7, 8, 4, 4);
        bone.setTextureOffset(0, 36).addBox(-2, 1, -9, 4, 11, 4);

        bipedBody.addChild(bone);

        setVisible(false);
        bipedBody.showModel = true;
    }
}
