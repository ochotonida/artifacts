package artifacts.forge.item.wearable.hands;

import artifacts.forge.event.ArtifactEventHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class FireGauntletItem extends WearableArtifactItem {

    public FireGauntletItem() {
        ArtifactEventHandler.addListener(this, LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.FIRE_GAUNTLET_FIRE_DURATION.get() <= 0;
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource()) && !event.getEntity().fireImmune()) {
            event.getEntity().setSecondsOnFire(Math.max(0, ModGameRules.FIRE_GAUNTLET_FIRE_DURATION.get()));
        }
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }
}
