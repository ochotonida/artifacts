package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class BunnyHoppersItem extends HurtSoundModifyingItem {

    public BunnyHoppersItem() {
        super(SoundEvents.RABBIT_HURT);
        addListener(EventPriority.HIGH, LivingFallEvent.class, this::onLivingFall);
        addListener(LivingEvent.LivingJumpEvent.class, this::onLivingJump);
    }

    private void onLivingFall(LivingFallEvent event, LivingEntity wearer) {
        if (ModConfig.server.bunnyHoppers.shouldCancelFallDamage.get()) {
            event.setDamageMultiplier(0);
        }
    }

    private void onLivingJump(LivingEvent.LivingJumpEvent event, LivingEntity wearer) {
        damageEquippedStacks(wearer);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        int jumpBoostLevel = ModConfig.server.bunnyHoppers.jumpBoostLevel.get() - 1;
        if (!ModConfig.server.isCosmetic(this) && !livingEntity.level.isClientSide && livingEntity.tickCount % 15 == 0 && jumpBoostLevel >= 0) {
            livingEntity.addEffect(new EffectInstance(Effects.JUMP, 39, jumpBoostLevel, true, false));
        }
    }
}
