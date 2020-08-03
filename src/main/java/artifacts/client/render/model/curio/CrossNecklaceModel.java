package artifacts.client.render.model.curio;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class CrossNecklaceModel extends BipedModel<LivingEntity> {

    public CrossNecklaceModel() {
        super(0, 0, 64, 64);

        setVisible(false);

        bipedBody = new RendererModel(this, 0, 0);
        RendererModel cross1 = new RendererModel(this, 52, 0);
        RendererModel cross2 = new RendererModel(this, 56, 0);
        RendererModel cross3 = new RendererModel(this, 60, 0);

        bipedBody.addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1);
        cross1.addBox(-0.5F, 4.5F, -5, 1, 4, 1);
        cross2.addBox(-1.5F, 5.5F, -5, 1, 1, 1);
        cross3.addBox(0.5F, 5.5F, -5, 1, 1, 1);

        bipedBody.addChild(cross1);
        bipedBody.addChild(cross2);
        bipedBody.addChild(cross3);
    }

    @Override
    public void render(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
        bipedBody.render(scale);
    }
}
