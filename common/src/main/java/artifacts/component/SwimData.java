package artifacts.component;

import artifacts.network.NetworkHandler;
import artifacts.network.SwimPacket;
import artifacts.registry.ModGameRules;

public class SwimData {

    protected boolean shouldSwim;
    protected boolean hasTouchedWater;
    protected int swimTime;

    public boolean isSwimming() {
        return shouldSwim;
    }

    public boolean isWet() {
        return hasTouchedWater;
    }

    public int getSwimTime() {
        return swimTime;
    }

    public void setSwimming(boolean shouldSwim) {
        if (this.shouldSwim && !shouldSwim) {
            int rechargeTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_RECHARGE_DURATION.get() * 20);
            int maxFlightTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() * 20);

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
        NetworkHandler.CHANNEL.sendToServer(new SwimPacket(shouldSwim));
    }
}
