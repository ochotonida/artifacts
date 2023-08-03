package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class LuckyScarfItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get() == 1) {
            tooltip.add(tooltipLine("single_level"));
        } else {
            tooltip.add(tooltipLine("multiple_levels", ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get()));
        }
    }

    @Override
    public int getFortuneLevel() {
        return Math.max(0, ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get());
    }
}
