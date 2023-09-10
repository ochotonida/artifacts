package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.util.DamageSourceHelper;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Supplier;

public abstract class PendantItem extends WearableArtifactItem {

    private final Supplier<Integer> strikeChance;
    private final Supplier<Integer> cooldown;

    public PendantItem(Supplier<Integer> strikeChance, Supplier<Integer> cooldown) {
        EntityEvent.LIVING_HURT.register(this::onLivingHurt);
        this.strikeChance = strikeChance;
        this.cooldown = cooldown;
    }

    protected EventResult onLivingHurt(LivingEntity entity, DamageSource damageSource, float amount) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
        if (
                isEquippedBy(entity)
                && !entity.level().isClientSide()
                && amount >= 1
                && attacker != null
                && !isOnCooldown(entity)
                && entity.getRandom().nextDouble() < strikeChance.get() / 100D
        ) {
            applyEffect(entity, attacker);
            addCooldown(entity, cooldown.get());
        }
        return EventResult.pass();
    }

    protected abstract void applyEffect(LivingEntity target, LivingEntity attacker);

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }
}
