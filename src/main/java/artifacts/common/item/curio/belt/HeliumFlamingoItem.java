package artifacts.common.item.curio.belt;

import artifacts.common.capability.SwimHandler;
import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class HeliumFlamingoItem extends CurioItem {

    public HeliumFlamingoItem() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(Consumer<MutableComponent> tooltip) {
        tooltip.accept(tooltipLine("swimming"));
        tooltip.accept(tooltipLine("keybinding", Minecraft.getInstance().options.keySprint.getTranslatedKeyMessage()));
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
                                || event.player.isInWater() && !event.player.isSwimming() && !handler.isSinking()
                                || (!event.player.isInWater() || handler.isSinking()) && event.player.isOnGround()) {
                            handler.setSwimming(false);
                            if (!event.player.isOnGround() && !event.player.isInWater()) {
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

    private class ClientEventHandler {

        private boolean wasSprintKeyDown;
        private boolean wasSprintingOnGround;
        private boolean hasTouchedGround;

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void onInputUpdate(MovementInputUpdateEvent event) {
            if (ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() * 20 <= 0) {
                return;
            }

            Player player = event.getEntity();
            boolean isSprintKeyDown = Minecraft.getInstance().options.keySprint.isDown();

            player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                    handler -> {
                        if (!handler.isSwimming()) {
                            if (player.isOnGround()) {
                                hasTouchedGround = true;
                            } else if (!handler.isSwimming()
                                    && handler.getSwimTime() >= 0
                                    && isEquippedBy(player)
                                    && (player.isSwimming()
                                    || isSprintKeyDown
                                    && !wasSprintKeyDown
                                    && !wasSprintingOnGround
                                    && hasTouchedGround
                                    && !player.isOnGround()
                                    && (!player.isInWater() || handler.isSinking())
                                    && !player.isFallFlying()
                                    && !player.getAbilities().flying
                                    && !player.isPassenger())) {
                                handler.setSwimming(true);
                                handler.syncSwimming();
                                hasTouchedGround = false;
                            }
                        } else if (player.getAbilities().flying) {
                            handler.setSwimming(false);
                            handler.syncSwimming();
                            hasTouchedGround = true;
                        }
                    }
            );

            wasSprintKeyDown = isSprintKeyDown;
            if (!isSprintKeyDown) {
                wasSprintingOnGround = false;
            } else if (player.isOnGround()) {
                wasSprintingOnGround = true;
            }
        }
    }
}
