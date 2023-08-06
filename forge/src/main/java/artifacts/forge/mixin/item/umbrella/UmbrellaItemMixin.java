package artifacts.forge.mixin.item.umbrella;

import artifacts.item.UmbrellaItem;
import artifacts.registry.ModGameRules;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(UmbrellaItem.class)
public abstract class UmbrellaItemMixin extends Item {

    public UmbrellaItemMixin(Properties properties) {
        super(properties);
        throw new IllegalArgumentException();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction) && ModGameRules.UMBRELLA_IS_SHIELD.get();
    }
}
