package artifacts.common.capability.swimhandler;

public interface ISwimHandler {

    boolean isSwimming();

    boolean isSinking();

    void setSwimming(boolean shouldSwim);

    void setSinking(boolean shouldSink);

    void syncSwimming();
}
