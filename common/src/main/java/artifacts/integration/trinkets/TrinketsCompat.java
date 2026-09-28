package artifacts.integration.trinkets;

import artifacts.equipment.EquipmentSlotManager;
import artifacts.event.ArtifactHooks;
import artifacts.util.DamageSourceHelper;
import eu.pb4.trinkets.api.TrinketDropRule;
import eu.pb4.trinkets.api.event.TrinketDropCallback;
import eu.pb4.trinkets.api.event.TrinketEquipmentChangedCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;

public class TrinketsCompat {

    public static void setup() {
        EquipmentSlotManager.register(new TrinketsSlotProvider());

        TrinketEquipmentChangedCallback.EVENT.register(
                (oldStack, newStack, _, entity) -> ArtifactHooks.onItemChanged(entity, oldStack, newStack)
        );
        TrinketDropCallback.EVENT.register(TrinketsCompat::onDropItem);
    }

    public static TrinketDropRule onDropItem(TrinketDropRule dropRule, ItemStack stack, SlotAccess slotAccess, LivingEntity entity) {
        if (dropRule == TrinketDropRule.DEFAULT && DamageSourceHelper.shouldDestroyWornItemOnDeath(entity, stack)) {
            return TrinketDropRule.DESTROY;
        }
        return dropRule;
    }
}
