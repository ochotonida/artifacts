package artifacts.common.capability.swimhandler;

public interface ISwimHandler {

    boolean isSwimming();

    boolean isSinking();

    boolean isWet();

    void setSwimming(boolean shouldSwim);

    void setSinking(boolean shouldSink);

    void setWet(boolean hasTouchedWater);

    void syncSwimming();
}
