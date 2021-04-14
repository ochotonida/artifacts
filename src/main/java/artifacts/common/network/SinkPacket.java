package artifacts.common.network;

import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SinkPacket {

    private final boolean shouldSink;

    @SuppressWarnings("unused")
    public SinkPacket(PacketBuffer buffer) {
        shouldSink = buffer.readBoolean();
    }

    public SinkPacket(boolean shouldSink) {
        this.shouldSink = shouldSink;
    }

    @SuppressWarnings("unused")
    void encode(PacketBuffer buffer) {
        buffer.writeBoolean(shouldSink);
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            context.get().enqueueWork(() -> player.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(handler -> handler.setSinking(shouldSink)));
        }
        context.get().setPacketHandled(true);
    }
}
