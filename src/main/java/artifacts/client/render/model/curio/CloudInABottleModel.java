package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class CloudInABottleModel extends BipedModel<LivingEntity> {

    protected final ModelRenderer cloud;

    public CloudInABottleModel() {
        super(RenderType::getEntityTranslucent, 0.5F, 0, 32, 32);

        bipedBody = new ModelRenderer(this, 0, 0);
        ModelRenderer jar = new ModelRenderer(this, 0, 16);
        ModelRenderer lid = new ModelRenderer(this, 0, 25);
        cloud = new ModelRenderer(this).setTextureOffset(8, 25);

        bipedBody.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        jar.addBox(-2, 0, -2, 4, 5, 4);
        lid.addBox(-1, -1, -1, 2, 1, 2);
        cloud.addBox(-1, 1.5F, -1, 2, 2, 2);

        jar.setRotationPoint(4, 9, -3);
        jar.rotateAngleY = -0.5F;

        jar.addChild(lid);
        jar.addChild(cloud);
        bipedBody.addChild(jar);

        setVisible(false);
        bipedBody.showModel = true;
    }

    @Override
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        cloud.rotateAngleY = (ageInTicks) / 50;
        cloud.rotationPointY = MathHelper.cos((ageInTicks) / 30) / 2;
    }
}
