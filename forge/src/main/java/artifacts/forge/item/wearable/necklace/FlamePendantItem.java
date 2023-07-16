package artifacts.forge.item.wearable.necklace;

import artifacts.forge.registry.ModGameRules;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class FlamePendantItem extends PendantItem {

    public FlamePendantItem() {
        super(ModGameRules.FLAME_PENDANT_STRIKE_CHANCE);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.FLAME_PENDANT_FIRE_DURATION.get() <= 0 || ModGameRules.FLAME_PENDANT_STRIKE_CHANCE.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        tooltip.add(tooltipLine("strike_chance"));
        if (ModGameRules.FLAME_PENDANT_DO_GRANT_FIRE_RESISTANCE.get()) {
            tooltip.add(tooltipLine("fire_resistance"));
        }
    }

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (!attacker.fireImmune() && attacker.attackable() && ModGameRules.FLAME_PENDANT_FIRE_DURATION.get() > 0) {
            if (ModGameRules.FLAME_PENDANT_DO_GRANT_FIRE_RESISTANCE.get()) {
                target.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, ModGameRules.FLAME_PENDANT_FIRE_DURATION.get() * 20, 0, false, false, true));
            }
            attacker.setSecondsOnFire(ModGameRules.FLAME_PENDANT_FIRE_DURATION.get());
        }
    }
}
