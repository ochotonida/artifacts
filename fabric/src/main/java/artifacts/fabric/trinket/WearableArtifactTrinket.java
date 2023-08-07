package artifacts.fabric.trinket;

import artifacts.item.wearable.WearableArtifactItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WearableArtifactTrinket implements Trinket {

    private final WearableArtifactItem item;

    public WearableArtifactTrinket(WearableArtifactItem item) {
        this.item = item;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        item.wornTick(entity, stack);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            item.onEquip(entity, stack);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            item.onUnequip(entity, stack);
        }
    }
}
