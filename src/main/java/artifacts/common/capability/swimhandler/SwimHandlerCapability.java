package artifacts.common.capability.swimhandler;

import artifacts.Artifacts;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
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
        public INBT writeNBT(Capability<ISwimHandler> capability, ISwimHandler instance, Direction side) {
            return new CompoundNBT();
        }

        @Override
        public void readNBT(Capability<ISwimHandler> capability, ISwimHandler instance, Direction side, INBT nbt) {

        }
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    public static class CapabilityEventHandler {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity) {
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
            event.player.getCapability(INSTANCE).ifPresent(
                    handler -> {
                        if (event.player.isInWater()) {
                            if (!handler.isWet()) {
                                handler.setWet(true);
                            }
                        } else if (event.player.isOnGround()) {
                            handler.setWet(false);
                        }
                    }
            );
        }
    }
}
