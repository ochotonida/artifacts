package artifacts.item.wearable.belt;

import artifacts.capability.SwimHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModKeyMappings;
import artifacts.registry.ModSoundEvents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.List;

public class HeliumFlamingoItem extends WearableArtifactItem {

    public HeliumFlamingoItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        tooltip.add(tooltipLine("swimming"));
        tooltip.add(tooltipLine("keymapping", ModKeyMappings.getHeliumFlamingoKey().getTranslatedKeyMessage()));
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(ModSoundEvents.POP.get(), 1, 0.7F);
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        event.player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                handler -> {
                    int maxFlightTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() * 20);
                    int rechargeTime = Math.max(20, ModGameRules.HELIUM_FLAMINGO_RECHARGE_DURATION.get() * 20);

                    if (handler.isSwimming()) {
                        if (!isEquippedBy(event.player)
                                || handler.getSwimTime() > maxFlightTime
                                || event.player.isInWater() && !event.player.isSwimming() && !SwimHandler.isSinking(event.player)
                                || (!event.player.isInWater() || SwimHandler.isSinking(event.player)) && event.player.onGround()) {
                            handler.setSwimming(false);
                            if (!event.player.onGround() && !event.player.isInWater()) {
                                event.player.playSound(ModSoundEvents.POP.get(), 0.5F, 0.75F);
                            }
                        }

                        if (isEquippedBy(event.player) && !event.player.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
                            if (!event.player.getAbilities().invulnerable) {
                                handler.setSwimTime(handler.getSwimTime() + 1);
                            }
                        }
                    } else if (handler.getSwimTime() < 0) {
                        handler.setSwimTime(
                                handler.getSwimTime() < -rechargeTime
                                        ? -rechargeTime
                                        : handler.getSwimTime() + 1
                        );
                    }
                }
        );
    }
}
