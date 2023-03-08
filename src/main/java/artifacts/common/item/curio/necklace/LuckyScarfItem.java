package artifacts.common.item.curio.necklace;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.curios.api.SlotContext;

public class LuckyScarfItem extends CurioItem {

    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
        return Math.max(0, ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get());
    }
}
