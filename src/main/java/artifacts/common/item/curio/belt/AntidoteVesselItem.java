package artifacts.common.item.curio.belt;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModTags;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.Map;

public class AntidoteVesselItem extends CurioItem {

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this)) {
            Map<MobEffect, MobEffectInstance> effects = new HashMap<>();

            int maxEffectDuration = ModConfig.server.antidoteVessel.maxEffectDuration.get();

            slotContext.entity().getActiveEffectsMap().forEach((effect, instance) -> {
                if (ModTags.ANTIDOTE_VESSEL_CANCELLABLE.contains(effect) && instance.getDuration() > maxEffectDuration) {
                    effects.put(effect, instance);
                }
            });

            effects.forEach((effect, instance) -> {
                damageStack(slotContext, stack);
                slotContext.entity().removeEffectNoUpdate(effect);
                if (maxEffectDuration > 0) {
                    slotContext.entity().addEffect(new MobEffectInstance(effect, maxEffectDuration, instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
                }
            });
        }
    }
}
