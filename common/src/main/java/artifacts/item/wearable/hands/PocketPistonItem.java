package artifacts.item.wearable.hands;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.util.DamageSourceHelper;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class PocketPistonItem extends WearableArtifactItem {

    public PocketPistonItem() {
        EntityEvent.LIVING_HURT.register(this::onLivingHurt);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.POCKET_PISTON_KNOCKBACK_STRENGTH.get() <= 0;
    }

    private EventResult onLivingHurt(LivingEntity entity, DamageSource damageSource, float amount) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
        if (attacker != null && isEquippedBy(attacker)) {
            float knockbackBonus = Math.max(0, ModGameRules.POCKET_PISTON_KNOCKBACK_STRENGTH.get() / 10F);
            entity.knockback(knockbackBonus, Mth.sin((float) (attacker.getYRot() * (Math.PI / 180))), -Mth.cos((float) (attacker.getYRot() * (Math.PI / 180))));
        }
        return EventResult.pass();
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.PISTON_EXTEND;
    }
}
