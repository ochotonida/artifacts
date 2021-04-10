package artifacts.common.network;

import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SwimPacket {

    private final boolean shouldSwim;

    @SuppressWarnings("unused")
    public SwimPacket(PacketBuffer buffer) {
        shouldSwim = buffer.readBoolean();
    }

    public SwimPacket(boolean shouldSwim) {
        this.shouldSwim = shouldSwim;
    }

    @SuppressWarnings("unused")
    void encode(PacketBuffer buffer) {
        buffer.writeBoolean(shouldSwim);
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        PlayerEntity player = context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT ? Minecraft.getInstance().player : context.get().getSender();
        if (player != null) {
            context.get().enqueueWork(() -> player.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(handler -> handler.setSwimming(shouldSwim)));
        }
        context.get().setPacketHandled(true);
    }
}
