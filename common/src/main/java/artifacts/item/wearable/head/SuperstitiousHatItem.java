package artifacts.item.wearable.head;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SuperstitiousHatItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get() == 1) {
            tooltip.add(tooltipLine("single_level"));
        } else {
            tooltip.add(tooltipLine("multiple_levels", ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get()));
        }
    }

    @Override
    public int getLootingLevel() {
        return Math.max(0, ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get());
    }
}
