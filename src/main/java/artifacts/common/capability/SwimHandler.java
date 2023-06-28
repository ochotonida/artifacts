package artifacts.common.capability;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.network.NetworkHandler;
import artifacts.common.network.SwimPacket;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;

public class SwimHandler {

    public static final Capability<SwimHandler> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private boolean shouldSwim;
    private boolean hasTouchedWater;
    private int swimTime;

    public boolean isSwimming() {
        return shouldSwim;
    }

    public static boolean isSinking(LivingEntity entity) {
        return !ModConfig.server.isCosmetic(ModItems.CHARM_OF_SINKING.get()) && ModItems.CHARM_OF_SINKING.get().isEquippedBy(entity);
    }

    public boolean isWet() {
        return hasTouchedWater;
    }

    public int getSwimTime() {
        return swimTime;
    }

    public void setSwimming(boolean shouldSwim) {
        if (this.shouldSwim && !shouldSwim) {
            int rechargeTime = ModConfig.server.heliumFlamingo.rechargeTime.get();
            int maxFlightTime = ModConfig.server.heliumFlamingo.maxFlightTime.get();

            setSwimTime((int) (-rechargeTime * getSwimTime() / (float) maxFlightTime));
        }

        this.shouldSwim = shouldSwim;
    }

    public void setWet(boolean hasTouchedWater) {
        this.hasTouchedWater = hasTouchedWater;
    }

    public void setSwimTime(int swimTime) {
        this.swimTime = swimTime;
    }

    public void syncSwimming() {
        NetworkHandler.INSTANCE.sendToServer(new SwimPacket(shouldSwim));
    }

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(SwimHandler::onRegisterCapabilities);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, SwimHandler::onAttachCapabilities);
        MinecraftForge.EVENT_BUS.addListener(SwimHandler::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(SwimHandler::onPlayerSwim);
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SwimHandler.class);
    }

    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            SwimHandlerProvider provider = new SwimHandlerProvider();
            event.addCapability(new ResourceLocation(Artifacts.MODID, "swim_handler"), provider);
        }
    }

    public static void onPlayerSwim(PlayerSwimEvent event) {
        event.getPlayer().getCapability(CAPABILITY).ifPresent(
                handler -> {
                    if (event.getResult() == Event.Result.DEFAULT) {
                        if (handler.isSwimming()) {
                            event.setResult(Event.Result.ALLOW);
                        } else if (isSinking(event.getPlayer())) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }
        );
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        event.player.getCapability(CAPABILITY).ifPresent(
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
