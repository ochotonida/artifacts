package artifacts.network;

import artifacts.capability.SwimHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwimPacket {

    private final boolean shouldSwim;

    @SuppressWarnings("unused")
    public SwimPacket(FriendlyByteBuf buffer) {
        shouldSwim = buffer.readBoolean();
    }

    public SwimPacket(boolean shouldSwim) {
        this.shouldSwim = shouldSwim;
    }

    @SuppressWarnings("unused")
    void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(shouldSwim);
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        Player player = context.get().getSender();
        if (player != null) {
            context.get().enqueueWork(() -> player.getCapability(SwimHandler.CAPABILITY).ifPresent(handler -> handler.setSwimming(shouldSwim)));
        }
        context.get().setPacketHandled(true);
    }
}
