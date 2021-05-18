package artifacts.common.capability.swimhandler;

import net.minecraft.entity.player.ServerPlayerEntity;

public interface ISwimHandler {

    boolean isSwimming();

    boolean isSinking();

    boolean isWet();

    void setSwimming(boolean shouldSwim);

    void setSinking(boolean shouldSink);

    void setWet(boolean hasTouchedWater);

    void syncSwimming();

    void syncSinking(ServerPlayerEntity player);
}
