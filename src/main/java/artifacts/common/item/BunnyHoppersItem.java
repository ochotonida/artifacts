package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.feet.BunnyHoppersModel;
import artifacts.common.config.Config;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class BunnyHoppersItem extends HurtSoundModifyingItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/bunny_hoppers.png");

    public BunnyHoppersItem() {
        super(SoundEvents.RABBIT_HURT);
        addListener(EventPriority.HIGHEST, LivingFallEvent.class, this::onLivingFall);
    }

    public void onLivingFall(LivingFallEvent event) {
        if (Config.SERVER.bunnyHoppers.shouldCancelFallDamage) {
            event.setDamageMultiplier(0);
        }
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!Config.SERVER.isCosmetic(this) && !livingEntity.level.isClientSide && livingEntity.tickCount % 15 == 0 && Config.SERVER.bunnyHoppers.jumpBoostLevel >= 0) {
            livingEntity.addEffect(new EffectInstance(Effects.JUMP, 39, Config.SERVER.bunnyHoppers.jumpBoostLevel, true, false));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new BunnyHoppersModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
