package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class PendantItem extends WearableArtifactItem {

    private final Supplier<Double> strikeChance;
    private final Supplier<Integer> cooldown;

    public static final List<BiConsumer<LivingEntity, Entity>> LISTENERS = new ArrayList<>();

    public PendantItem(Supplier<Double> strikeChance, Supplier<Integer> cooldown) {
        LISTENERS.add(this::onLivingHurt);
        this.strikeChance = strikeChance;
        this.cooldown = cooldown;
    }

    public double getStrikeChance() {
        return strikeChance.get();
    }

    protected void onLivingHurt(LivingEntity entity, Entity attacker) {
        if (
                isEquippedBy(entity)
                && !entity.level().isClientSide()
                && attacker != null
                && !isOnCooldown(entity)
                && entity.getRandom().nextDouble() < getStrikeChance()
                && attacker instanceof LivingEntity livingEntity
        ) {
            applyEffect(entity, livingEntity);
            addCooldown(entity, cooldown.get());
        }
    }

    protected abstract void applyEffect(LivingEntity target, LivingEntity attacker);

    @Override
    protected boolean hasNonCosmeticEffects() {
        return getStrikeChance() > 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }
}
