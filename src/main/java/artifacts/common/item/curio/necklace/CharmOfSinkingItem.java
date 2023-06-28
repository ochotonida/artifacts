package artifacts.common.item.curio.necklace;

import artifacts.common.item.curio.CurioItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;

public class CharmOfSinkingItem extends CurioItem {

    public CharmOfSinkingItem() {
        addListener(EventPriority.HIGH, PlayerEvent.BreakSpeed.class, this::onBreakSpeed);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 20 == 0 && slotContext.entity().isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
            damageStack(slotContext, stack);
        }
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event, LivingEntity wearer) {
        if (wearer.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && !EnchantmentHelper.hasAquaAffinity(wearer)) {
            event.setNewSpeed(event.getNewSpeed() * 5);
        }
    }
}
