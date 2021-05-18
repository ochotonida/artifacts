package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.head.VillagerHatModel;
import artifacts.common.config.ModConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerHatItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/villager_hat.png");

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new VillagerHatModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this) && !livingEntity.level.isClientSide && livingEntity.tickCount % 15 == 0) {
            int heroOfTheVillageLevel = ModConfig.server.villagerHat.heroOfTheVillageLevel.get() - 1;
            livingEntity.addEffect(new EffectInstance(Effects.HERO_OF_THE_VILLAGE, 39, heroOfTheVillageLevel, true, false));
        }
    }
}
