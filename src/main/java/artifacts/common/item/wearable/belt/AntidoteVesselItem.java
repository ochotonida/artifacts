package artifacts.common.item.wearable.belt;

import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModTags;
import artifacts.common.item.wearable.WearableArtifactItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.Map;

public class AntidoteVesselItem extends WearableArtifactItem {

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.ANTIDOTE_VESSEL_ENABLED.get();
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!ModGameRules.ANTIDOTE_VESSEL_ENABLED.get()) {
            return;
        }
        Map<MobEffect, MobEffectInstance> effects = new HashMap<>();

        int maxEffectDuration = Math.max(0, ModGameRules.ANTIDOTE_VESSEL_MAX_EFFECT_DURATION.get() * 20);
        slotContext.entity().getActiveEffectsMap().forEach((effect, instance) -> {
            if (ModTags.isInTag(effect, ModTags.ANTIDOTE_VESSEL_CANCELLABLE) && instance.getDuration() > maxEffectDuration) {
                effects.put(effect, instance);
            }
        });

        effects.forEach((effect, instance) -> {
            slotContext.entity().removeEffectNoUpdate(effect);
            if (maxEffectDuration > 0) {
                slotContext.entity().addEffect(new MobEffectInstance(effect, maxEffectDuration, instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
            }
        });
    }
}
