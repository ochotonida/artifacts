package artifacts.common.capability.swimhandler;

import artifacts.Artifacts;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SwimHandlerCapability {

    @CapabilityInject(ISwimHandler.class)
    public static Capability<ISwimHandler> INSTANCE = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ISwimHandler.class, new Storage(), SwimHandler::new);
    }

    public static class Storage implements Capability.IStorage<ISwimHandler> {

        @Override
        public Tag writeNBT(Capability<ISwimHandler> capability, ISwimHandler handler, Direction side) {
            CompoundTag compoundNBT = new CompoundTag();
            compoundNBT.putBoolean("ShouldSwim", handler.isSwimming());
            compoundNBT.putBoolean("ShouldSink", handler.isSinking());
            compoundNBT.putBoolean("IsWet", handler.isWet());
            compoundNBT.putInt("SwimTime", handler.getSwimTime());
            return compoundNBT;
        }

        @Override
        public void readNBT(Capability<ISwimHandler> capability, ISwimHandler handler, Direction side, Tag nbt) {
            if (nbt instanceof CompoundTag) {
                CompoundTag compoundNBT = (CompoundTag) nbt;
                handler.setSwimming(compoundNBT.getBoolean("ShouldSwim"));
                handler.setSinking(compoundNBT.getBoolean("ShouldSink"));
                handler.setWet(compoundNBT.getBoolean("IsWet"));
                handler.setSwimTime(compoundNBT.getInt("SwimTime"));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    public static class CapabilityEventHandler {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                SwimHandlerProvider provider = new SwimHandlerProvider();
                event.addCapability(new ResourceLocation(Artifacts.MODID, "swim_handler"), provider);
                event.addListener(provider::invalidate);
            }
        }

        @SubscribeEvent
        public static void onPlayerSwim(PlayerSwimEvent event) {
            event.getPlayer().getCapability(INSTANCE).ifPresent(
                    handler -> {
                        if (event.getResult() == Event.Result.DEFAULT) {
                            if (handler.isSwimming()) {
                                event.setResult(Event.Result.ALLOW);
                            } else if (handler.isSinking()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    }
            );
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase != TickEvent.Phase.START) {
                return;
            }

            event.player.getCapability(INSTANCE).ifPresent(
                    handler -> {
                        if (event.player.isInWater() || event.player.isInLava()) {
                            if (!handler.isWet()) {
                                handler.setWet(true);
                            }
                        } else if (event.player.isOnGround() || event.player.getAbilities().flying) {
                            handler.setWet(false);
                        }
                    }
            );
        }
    }
}
