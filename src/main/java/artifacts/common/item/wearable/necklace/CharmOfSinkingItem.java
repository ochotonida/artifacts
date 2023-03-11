package artifacts.common.item.wearable.necklace;

import artifacts.common.capability.SwimHandler;
import artifacts.common.init.ModGameRules;
import artifacts.common.item.wearable.WearableArtifactItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;

public class CharmOfSinkingItem extends WearableArtifactItem {

    public CharmOfSinkingItem() {
        addListener(EventPriority.HIGH, PlayerEvent.BreakSpeed.class, this::onBreakSpeed);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.CHARM_OF_SINKING_ENABLED.get();
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event, LivingEntity wearer) {
        if (ModGameRules.CHARM_OF_SINKING_ENABLED.get() && wearer.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && !EnchantmentHelper.hasAquaAffinity(wearer)) {
            event.setNewSpeed(event.getNewSpeed() * 5);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer player) {
            slotContext.entity().getCapability(SwimHandler.CAPABILITY).ifPresent(
                    handler -> {
                        if (ModGameRules.CHARM_OF_SINKING_ENABLED.get()) {
                            if (!handler.isSinking()) {
                                handler.setSinking(true);
                                handler.syncSinking(player);
                            }
                        } else if (handler.isSinking()) {
                            handler.setSinking(false);
                            handler.syncSinking(player);
                        }
                    }
            );
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (slotContext.entity() instanceof ServerPlayer player) {
            slotContext.entity().getCapability(SwimHandler.CAPABILITY).ifPresent(
                    handler -> {
                        handler.setSinking(false);
                        handler.syncSinking(player);
                    }
            );
        }
    }
}
