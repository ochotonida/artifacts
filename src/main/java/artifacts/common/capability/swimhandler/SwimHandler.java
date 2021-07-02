package artifacts.common.capability.swimhandler;

import artifacts.common.config.ModConfig;
import artifacts.common.network.NetworkHandler;
import artifacts.common.network.SinkPacket;
import artifacts.common.network.SwimPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class SwimHandler implements ISwimHandler {

    private boolean shouldSwim;
    private boolean shouldSink;
    private boolean hasTouchedWater;
    private int swimTime;

    @Override
    public boolean isSwimming() {
        return shouldSwim;
    }

    @Override
    public boolean isSinking() {
        return shouldSink;
    }

    @Override
    public boolean isWet() {
        return hasTouchedWater;
    }

    @Override
    public int getSwimTime() {
        return swimTime;
    }

    @Override
    public void setSwimming(boolean shouldSwim) {
        if (this.shouldSwim && !shouldSwim) {
            int rechargeTime = ModConfig.server.heliumFlamingo.rechargeTime.get();
            int maxFlightTime = ModConfig.server.heliumFlamingo.maxFlightTime.get();

            setSwimTime((int) (-rechargeTime * getSwimTime() / (float) maxFlightTime));
        }

        this.shouldSwim = shouldSwim;
    }

    @Override
    public void setSinking(boolean shouldSink) {
        this.shouldSink = shouldSink;
    }

    @Override
    public void setWet(boolean hasTouchedWater) {
        this.hasTouchedWater = hasTouchedWater;
    }

    @Override
    public void setSwimTime(int swimTime) {
        this.swimTime = swimTime;
    }

    @Override
    public void syncSwimming() {
        NetworkHandler.INSTANCE.sendToServer(new SwimPacket(shouldSwim));
    }

    @Override
    public void syncSinking(ServerPlayerEntity player) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SinkPacket(shouldSink));
    }
}
