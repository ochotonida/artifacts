package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AntidoteVesselItem extends CurioItem {

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this)) {
            Map<Effect, EffectInstance> effects = new HashMap<>();

            int maxEffectDuration = ModConfig.server.antidoteVessel.maxEffectDuration.get();

            entity.getActiveEffectsMap().forEach((effect, instance) -> {
                Set<Effect> negativeEffects = ModConfig.server.antidoteVessel.negativeEffects;
                if (negativeEffects.contains(effect) && instance.getDuration() > maxEffectDuration) {
                    effects.put(effect, instance);
                }
            });

            effects.forEach((effect, instance) -> {
                damageStack(identifier, index, entity, stack);
                entity.removeEffectNoUpdate(effect);
                if (maxEffectDuration > 0) {
                    entity.addEffect(new EffectInstance(effect, maxEffectDuration, instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
                }
            });
        }
    }
}
