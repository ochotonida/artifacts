package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

public class LuckyScarfItem extends CurioItem {

    public LuckyScarfItem() {
        addListener(BlockEvent.BreakEvent.class, this::onBreakBlock, BlockEvent.BreakEvent::getPlayer);
    }

    private void onBreakBlock(BlockEvent.BreakEvent event, LivingEntity wearer) {
        damageEquippedStacks(wearer);
    }

    @Override
    public int getFortuneBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index) {
        return ModConfig.server.isCosmetic(this) ? 0 : ModConfig.server.luckyScarf.fortuneBonus.get();
    }
}
