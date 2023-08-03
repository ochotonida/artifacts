package artifacts.item.wearable;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class MobEffectItem extends WearableArtifactItem {

    private final MobEffect mobEffect;
    private final int duration;
    protected final Supplier<Integer> amplifier;
    protected final Supplier<Boolean> isEnabled;

    public MobEffectItem(MobEffect mobEffect, Supplier<Boolean> isEnabled) {
        this(mobEffect, () -> 1, 40,  isEnabled);
    }

    public MobEffectItem(MobEffect mobEffect, int duration, Supplier<Boolean> isEnabled) {
        this(mobEffect, () -> 1, duration, isEnabled);
    }

    public MobEffectItem(MobEffect mobEffect, Supplier<Integer> amplifier, int duration) {
        this(mobEffect, amplifier, duration, () -> true);
    }

    private MobEffectItem(MobEffect mobEffect, Supplier<Integer> amplifier, int duration, Supplier<Boolean> isEnabled) {
        this.mobEffect = mobEffect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.isEnabled = isEnabled;
    }

    @Override
    protected boolean isCosmetic() {
        return !isEnabled.get() || amplifier.get() <= 0;
    }

    public boolean isEffectActive(LivingEntity entity) {
        if (!isEnabled.get() || amplifier.get() <= 0) {
            return false;
        }
        return findAllEquippedBy(entity).anyMatch(WearableArtifactItem::isActivated);
    }

    private int getAmplifier() {
        return Math.max(0, Math.min(127, this.amplifier.get() - 1));
    }

    @Override
    public void wornTick(LivingEntity entity, ItemStack stack) {
        if (isEffectActive(entity) && !entity.level().isClientSide()) {
            entity.addEffect(new MobEffectInstance(mobEffect, duration - 1, getAmplifier(), true, false));
        }
    }

    @Override
    public void onUnequip(LivingEntity entity, ItemStack stack) {
        if (isEnabled.get() && !entity.level().isClientSide()) {
            MobEffectInstance effectInstance = entity.getEffect(mobEffect);
            if (effectInstance != null && effectInstance.getAmplifier() == getAmplifier() && !effectInstance.isVisible() && effectInstance.getDuration() < duration) {
                entity.removeEffect(mobEffect);
            }
        }
    }
}
