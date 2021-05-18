package artifacts.client.render.model.curio.necklace;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class ScarfModel extends BipedModel<LivingEntity> {

    private final ModelRenderer cloak;

    public ScarfModel() {
        this(RenderType::entityCutoutNoCull);
    }

    public ScarfModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType, 0.5F, 0, 64, 32);
        setAllVisible(false);

        head.visible = true;
        body = new ModelRenderer(this);
        cloak = new ModelRenderer(this);
        cloak.setPos(0, 0, 1.99F);
        body.addChild(cloak);

        // scarf
        body.texOffs(0, 16);
        body.addBox(-6.01F, -2, -4, 12, 6, 8);

        // dangly bit
        cloak.texOffs(32, 0);
        cloak.addBox(-5, 0, 0, 5, 12, 2);
    }

    @Override
    public void prepareMobModel(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (entity instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) entity;
            double x = MathHelper.lerp(partialTicks, player.xCloakO, player.xCloak) - MathHelper.lerp(partialTicks, player.xo, player.getX());
            double y = MathHelper.lerp(partialTicks, player.yCloakO, player.yCloak) - MathHelper.lerp(partialTicks, player.yo, player.getY());
            double z = MathHelper.lerp(partialTicks, player.zCloakO, player.zCloak) - MathHelper.lerp(partialTicks, player.zo, player.getZ());
            float f = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO);
            double d3 = MathHelper.sin(f * ((float) Math.PI / 180));
            double d4 = -MathHelper.cos(f * ((float) Math.PI / 180));
            float f1 = (float) y * 10;
            f1 = MathHelper.clamp(f1, -6, 32);
            float f2 = (float) (x * d3 + z * d4) * 100;
            f2 = MathHelper.clamp(f2, 0, 150);
            if (f2 < 0) {
                f2 = 0;
            }

            float f4 = MathHelper.lerp(partialTicks, player.oBob, player.bob);
            f1 = f1 + MathHelper.sin(MathHelper.lerp(partialTicks, player.walkDistO, player.walkDist) * 6) * 32 * f4;

            cloak.xRot = body.xRot + (6 + f2 / 2 + f1) / 180 * (float) Math.PI;
        }
    }
}
