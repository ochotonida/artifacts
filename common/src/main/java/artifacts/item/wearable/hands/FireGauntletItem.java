package artifacts.item.wearable.hands;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.util.DamageSourceHelper;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class FireGauntletItem extends WearableArtifactItem {

    public FireGauntletItem() {
        EntityEvent.LIVING_HURT.register(this::onLivingHurt);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.FIRE_GAUNTLET_FIRE_DURATION.get() <= 0;
    }

    private EventResult onLivingHurt(LivingEntity entity, DamageSource damageSource, float amount) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
        if (isEquippedBy(attacker) && DamageSourceHelper.isMeleeAttack(damageSource) && !entity.fireImmune()) {
            entity.setSecondsOnFire(Math.max(0, ModGameRules.FIRE_GAUNTLET_FIRE_DURATION.get()));
        }
        return EventResult.pass();
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }
}
