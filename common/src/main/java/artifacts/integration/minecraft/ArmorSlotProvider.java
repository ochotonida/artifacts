package artifacts.integration.minecraft;

import artifacts.equipment.EquipmentSlotAccess;
import artifacts.equipment.EquipmentSlotProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiFunction;

public class ArmorSlotProvider implements EquipmentSlotProvider {

    @Override
    public <T> T reduceEquipment(LivingEntity entity, T init, BiFunction<EquipmentSlotAccess, T, T> f) {
        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            if (EquipmentSlotGroup.HAND.test(slot)) {
                continue;
            }
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                EquipmentSlotAccess slotAccess = new SlotAccess(entity, stack, slot);
                init = f.apply(slotAccess, init);
            }
        }
        return init;
    }

    @Override
    public ItemStack tryEquip(LivingEntity entity, ItemStack stack, boolean allowSwapping) {
        return stack;
    }

    private record SlotAccess(LivingEntity entity, ItemStack stack, EquipmentSlot slot) implements EquipmentSlotAccess {

        @Override
        public ItemStack get() {
            return stack;
        }

        @Override
        public void broadcastBreakEvent(LivingEntity entity) {
            entity.onEquippedItemBroken(stack, slot);
        }
    }
}
