package artifacts.client.render.model.curio;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ScarfModel extends BipedModel<LivingEntity> {

    private final RendererModel bipedCape;

    public ScarfModel() {
        super(0.5F, 0, 64, 32);
        bipedCape = new RendererModel(this, 32, 0);
        bipedCape.addBox(-5, 0, 0, 5, 12, 2);
        bipedBody = new RendererModel(this, 0, 16);
        bipedBody.addBox(-6.01F, -2, -4, 12, 6, 8);
    }

    @Override
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        bipedCape.copyModelAngles(bipedBody);
        bipedCape.rotationPointZ += 1.99F;
    }

    @Override
    public void setLivingAnimations(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (entity instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) entity;
            double x = MathHelper.lerp(partialTicks, player.prevChasingPosX, player.chasingPosX) - MathHelper.lerp(partialTicks, player.prevPosX, player.posX);
            double y = MathHelper.lerp(partialTicks, player.prevChasingPosY, player.chasingPosY) - MathHelper.lerp(partialTicks, player.prevPosY, player.posY);
            double z = MathHelper.lerp(partialTicks, player.prevChasingPosZ, player.chasingPosZ) - MathHelper.lerp(partialTicks, player.prevPosZ, player.posZ);
            float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset);
            double d3 = MathHelper.sin(f * ((float) Math.PI / 180));
            double d4 = -MathHelper.cos(f * ((float) Math.PI / 180));
            float f1 = (float) y * 10;
            f1 = MathHelper.clamp(f1, -6, 32);
            float f2 = (float) (x * d3 + z * d4) * 100;
            f2 = MathHelper.clamp(f2, 0, 150);
            if (f2 < 0) {
                f2 = 0;
            }

            float f4 = MathHelper.lerp(partialTicks, player.prevCameraYaw, player.cameraYaw);
            f1 = f1 + MathHelper.sin(MathHelper.lerp(partialTicks, player.prevDistanceWalkedModified, player.distanceWalkedModified) * 6) * 32 * f4;

            bipedCape.rotateAngleX += (6 + f2 / 2 + f1) / 180 * (float) Math.PI;
        }
    }

    @Override
    public void render(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        bipedCape.render(scale);
        bipedBody.render(scale);
        bipedHead.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
}
