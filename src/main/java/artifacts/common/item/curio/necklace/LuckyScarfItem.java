package artifacts.common.item.curio.necklace;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class LuckyScarfItem extends CurioItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(Consumer<MutableComponent> tooltip) {
        if (ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get() == 1) {
            tooltip.accept(tooltipLine("single"));
        } else {
            tooltip.accept(tooltipLine("multiple", ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get()));
        }
    }

    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
        return Math.max(0, ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get());
    }
}
