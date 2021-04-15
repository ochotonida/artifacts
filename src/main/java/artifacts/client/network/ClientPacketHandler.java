package artifacts.client.network;

import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.network.SinkPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class ClientPacketHandler {

    public static void handleSinkPacket(SinkPacket packet) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(handler -> handler.setSinking(packet.shouldSink));
        }
    }
}
