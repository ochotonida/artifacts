package artifacts.forge.item.wearable.necklace;

import artifacts.forge.event.ArtifactEventHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.function.Supplier;

public abstract class PendantItem extends WearableArtifactItem {

    private final Supplier<Integer> strikeChance;

    public PendantItem(Supplier<Integer> strikeChance) {
        ArtifactEventHandler.addListener(this, LivingAttackEvent.class, this::onLivingAttack);
        this.strikeChance = strikeChance;
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(event.getSource());
        if (!wearer.level().isClientSide()
                && event.getAmount() >= 1
                && attacker != null
                && wearer.getRandom().nextDouble() < strikeChance.get() / 100D) {
            applyEffect(wearer, attacker);
        }
    }

    protected abstract void applyEffect(LivingEntity target, LivingEntity attacker);

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }
}
