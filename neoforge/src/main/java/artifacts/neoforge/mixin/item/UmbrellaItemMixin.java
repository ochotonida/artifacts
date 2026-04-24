package artifacts.neoforge.mixin.item;

import artifacts.Artifacts;
import artifacts.item.UmbrellaItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(UmbrellaItem.class)
public abstract class UmbrellaItemMixin extends Item {

    public UmbrellaItemMixin(Properties properties) {
        super(properties);
        throw new IllegalArgumentException();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(toolAction) && Artifacts.CONFIG.items.umbrella.isShield.get();
    }
}
