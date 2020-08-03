package artifacts.common.item;

import artifacts.client.render.model.curio.GloveModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class GloveCurio extends Curio {

    protected Object modelDefault;
    protected Object modelSlim;

    public GloveCurio(Item item) {
        super(item);
    }

    @OnlyIn(Dist.CLIENT)
    protected static boolean hasSmallArms(Entity entity) {
        return entity instanceof AbstractClientPlayerEntity && ((AbstractClientPlayerEntity) entity).getSkinType().equals("slim");
    }

    @OnlyIn(Dist.CLIENT)
    protected ResourceLocation getTexture(boolean smallArms) {
        return smallArms ? getSlimTexture() : getTexture();
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract ResourceLocation getSlimTexture();

    @OnlyIn(Dist.CLIENT)
    protected GloveModel getModel(boolean smallArms) {
        return (smallArms ? getSlimModel() : getModel());
    }

    @OnlyIn(Dist.CLIENT)
    protected GloveModel getSlimModel() {
        if (modelSlim == null) {
            modelSlim = new GloveModel(true);
        }
        return (GloveModel) modelSlim;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected GloveModel getModel() {
        if (modelDefault == null) {
            modelDefault = new GloveModel(false);
        }
        return (GloveModel) modelDefault;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void doRender(String identifier, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean smallArms = hasSmallArms(entity);
        GloveModel model = getModel(smallArms);
        Minecraft.getInstance().getTextureManager().bindTexture(getTexture(smallArms));
        model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        RenderHelper.followBodyRotations(entity, model);
        model.renderHand(true, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
