package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class CloudInABottleModel extends BipedModel<LivingEntity> {

    protected final ModelRenderer cloud;

    public CloudInABottleModel() {
        super(RenderType::entityTranslucent, 0.5F, 0, 32, 32);

        body = new ModelRenderer(this, 0, 0);
        ModelRenderer jar = new ModelRenderer(this, 0, 16);
        ModelRenderer lid = new ModelRenderer(this, 0, 25);
        cloud = new ModelRenderer(this).texOffs(8, 25);

        body.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        jar.addBox(-2, 0, -2, 4, 5, 4);
        lid.addBox(-1, -1, -1, 2, 1, 2);
        cloud.addBox(-1, 1.5F, -1, 2, 2, 2);

        jar.setPos(4, 9, -3);
        jar.yRot = -0.5F;

        jar.addChild(lid);
        jar.addChild(cloud);
        body.addChild(jar);

        setAllVisible(false);
        body.visible = true;
    }

    @Override
    public void setupAnim(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        cloud.yRot = (ageInTicks) / 50;
        cloud.y = MathHelper.cos((ageInTicks) / 30) / 2;
    }
}
