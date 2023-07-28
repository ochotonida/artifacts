package artifacts.forge.mixin.item.wearable.luckyscarf;

import artifacts.item.wearable.head.SuperstitiousHatItem;
import artifacts.registry.ModGameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mixin(SuperstitiousHatItem.class)
public abstract class LuckyScarfItemMixin implements ICurioItem {

    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
        return Math.max(0, ModGameRules.LUCKY_SCARF_FORTUNE_BONUS.get());
    }
}
