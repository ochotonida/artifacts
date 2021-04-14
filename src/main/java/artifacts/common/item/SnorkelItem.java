package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.SnorkelModel;
import artifacts.common.config.Config;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SnorkelItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/snorkel.png");

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!Config.isCosmetic(this) && !livingEntity.level.isClientSide && livingEntity.tickCount % 15 == 0) {
            livingEntity.addEffect(new EffectInstance(Effects.WATER_BREATHING, 39, 0, true, false));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new SnorkelModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
