package artifacts.common.item.curio.necklace;

import artifacts.common.capability.SwimHandler;
import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class CharmOfSinkingItem extends CurioItem {

    public CharmOfSinkingItem() {
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 20 == 0 && slotContext.entity().isEyeInFluid(FluidTags.WATER)) {
            damageStack(slotContext, stack);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!ModConfig.server.isCosmetic(this) && slotContext.entity() instanceof ServerPlayer) {
            slotContext.entity().getCapability(SwimHandler.CAPABILITY).ifPresent(
                    handler -> {
                        handler.setSinking(true);
                        handler.syncSinking((ServerPlayer) slotContext.entity());
                    }
            );
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (slotContext.entity() instanceof ServerPlayer) {
            slotContext.entity().getCapability(SwimHandler.CAPABILITY).ifPresent(
                    handler -> {
                        handler.setSinking(false);
                        handler.syncSinking((ServerPlayer) slotContext.entity());
                    }
            );
        }
    }
}
