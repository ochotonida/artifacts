package artifacts.common.capability;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.network.NetworkHandler;
import artifacts.common.network.SinkPacket;
import artifacts.common.network.SwimPacket;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class SwimHandler implements INBTSerializable<CompoundTag> {

    public static final Capability<SwimHandler> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private boolean shouldSwim;
    private boolean shouldSink;
    private boolean hasTouchedWater;
    private int swimTime;

    public boolean isSwimming() {
        return shouldSwim;
    }

    public boolean isSinking() {
        return shouldSink;
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

    public void setSinking(boolean shouldSink) {
        this.shouldSink = shouldSink;
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

    public void syncSinking(ServerPlayer player) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SinkPacket(shouldSink));
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putBoolean("ShouldSwim", isSwimming());
        compoundNBT.putBoolean("ShouldSink", isSinking());
        compoundNBT.putBoolean("IsWet", isWet());
        compoundNBT.putInt("SwimTime", getSwimTime());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setSwimming(nbt.getBoolean("ShouldSwim"));
        setSinking(nbt.getBoolean("ShouldSink"));
        setWet(nbt.getBoolean("IsWet"));
        setSwimTime(nbt.getInt("SwimTime"));
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
            event.addListener(provider::invalidate);
        }
    }

    public static void onPlayerSwim(PlayerSwimEvent event) {
        event.getPlayer().getCapability(CAPABILITY).ifPresent(
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
