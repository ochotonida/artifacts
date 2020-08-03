package artifacts.client.render.model.curio;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class SnorkelModel extends BipedModel<LivingEntity> {

    public SnorkelModel() {
        super(0.5F, 0, 64, 64);

        setVisible(false);
        bipedHead.showModel = true;
        bipedHeadwear.showModel = true;

        RendererModel snorkelMouthPiece = new RendererModel(this, 0, 46);
        RendererModel snorkelTube = new RendererModel(this, 0, 32);

        snorkelMouthPiece.addBox(-2, -1.5F, -6, 8, 2, 2);
        snorkelTube.addBox(4.01F, -5, -3, 2, 2, 12);

        bipedHead.addChild(snorkelMouthPiece);
        bipedHead.addChild(snorkelTube);

        snorkelTube.rotateAngleX = 0.7853F;
    }

    @Override
    public void render(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
