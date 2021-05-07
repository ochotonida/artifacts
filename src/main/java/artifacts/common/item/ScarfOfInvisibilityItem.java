package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.necklace.ScarfModel;
import artifacts.common.config.ModConfig;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScarfOfInvisibilityItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/scarf_of_invisibility.png");

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this) && !entity.level.isClientSide && entity.tickCount % 15 == 0) {
            entity.addEffect(new EffectInstance(Effects.INVISIBILITY, 39, 0, true, false));
        }
        if (entity.tickCount % 20 == 0) {
            damageStack(identifier, index, entity, stack);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new ScarfModel(RenderType::entityTranslucent);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
