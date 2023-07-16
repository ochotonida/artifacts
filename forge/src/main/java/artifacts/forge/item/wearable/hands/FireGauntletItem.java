package artifacts.forge.item.wearable.hands;

import artifacts.forge.item.wearable.WearableArtifactItem;
import artifacts.forge.registry.ModGameRules;
import artifacts.forge.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class FireGauntletItem extends WearableArtifactItem {

    public FireGauntletItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
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
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_IRON, 1, 1);
    }
}
