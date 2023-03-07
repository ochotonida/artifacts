package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.curios.api.SlotContext;

public class LuckyScarfItem extends CurioItem {

    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
        return ModConfig.server.isCosmetic(this) ? 0 : ModConfig.server.luckyScarf.fortuneBonus.get();
    }
}
