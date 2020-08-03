package artifacts.client.render.model.curio;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class PanicNecklaceModel extends BipedModel<LivingEntity> {

    public PanicNecklaceModel() {
        super(0, 0, 64, 64);

        setVisible(false);

        bipedBody = new RendererModel(this, 0, 0);
        RendererModel gem1 = new RendererModel(this, 52, 0);
        RendererModel gem2 = new RendererModel(this, 58, 0);
        RendererModel gem3 = new RendererModel(this, 52, 3);
        RendererModel gem4 = new RendererModel(this, 60, 4);

        bipedBody.addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1);
        gem1.addBox(-2.5F, 5.5F, -5, 2, 2, 1);
        gem2.addBox(0.5F, 5.5F, -5, 2, 2, 1);
        gem3.addBox(-1.5F, 6.5F, -5, 3, 2, 1);
        gem4.addBox(-0.5F, 8.5F, -5, 1, 1, 1);

        bipedBody.addChild(gem1);
        bipedBody.addChild(gem2);
        bipedBody.addChild(gem3);
        bipedBody.addChild(gem4);
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
