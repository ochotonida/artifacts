package artifacts.item.wearable.belt;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AntidoteVesselItem extends WearableArtifactItem {

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BOTTLE_FILL;
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.ANTIDOTE_VESSEL_ENABLED.get();
    }

    @Override
    public void wornTick(LivingEntity entity, ItemStack stack) {
        if (!ModGameRules.ANTIDOTE_VESSEL_ENABLED.get()) {
            return;
        }
        Map<MobEffect, MobEffectInstance> effects = new HashMap<>();

        int maxEffectDuration = Math.max(0, ModGameRules.ANTIDOTE_VESSEL_MAX_EFFECT_DURATION.get() * 20);
        entity.getActiveEffectsMap().forEach((effect, instance) -> {
            if (ModTags.isInTag(effect, ModTags.ANTIDOTE_VESSEL_CANCELLABLE) && instance.getDuration() > maxEffectDuration) {
                effects.put(effect, instance);
            }
        });

        effects.forEach((effect, instance) -> {
            entity.removeEffectNoUpdate(effect);
            if (maxEffectDuration > 0) {
                entity.addEffect(new MobEffectInstance(effect, maxEffectDuration, instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
            }
        });
    }
}
