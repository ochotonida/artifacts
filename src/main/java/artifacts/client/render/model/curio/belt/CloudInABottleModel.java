package artifacts.client.render.model.curio.belt;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class CloudInABottleModel extends BeltModel {

    protected final ModelRenderer cloud;

    public CloudInABottleModel() {
        super(RenderType::entityTranslucent);

        ModelRenderer jar = new ModelRenderer(this);
        jar.setPos(4, 9, -3);
        jar.yRot = -0.5F;
        body.addChild(jar);

        // jar
        jar.texOffs(0, 16);
        jar.addBox(-2, 0, -2, 4, 5, 4);

        // lid
        jar.texOffs(0, 25);
        jar.addBox(-1, -1, -1, 2, 1, 2);

        // cloud
        cloud = new ModelRenderer(this).texOffs(8, 25);
        cloud.addBox(-1, 1.5F, -1, 2, 2, 2);
        jar.addChild(cloud);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        cloud.yRot = (ageInTicks) / 50;
        cloud.y = MathHelper.cos((ageInTicks) / 30) / 2;
    }
}
