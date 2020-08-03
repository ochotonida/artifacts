package artifacts.client.render.model.curio;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class PendantModel extends BipedModel<LivingEntity> {

    public PendantModel() {
        super(0, 0, 64, 64);

        setVisible(false);

        bipedBody = new RendererModel(this, 0, 0);
        RendererModel gem = new RendererModel(this, 50, 0);

        bipedBody.addBox(-(2 * 8) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8, 2 * 12 + 1, 2 * 4 + 1);
        gem.addBox(-1, 4.5F, -5, 2, 2, 1);

        bipedBody.addChild(gem);
    }

    @Override
    public void render(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
        bipedBody.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
