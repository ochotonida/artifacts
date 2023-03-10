package artifacts.common.network;

import artifacts.common.item.curio.head.NightVisionGogglesItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleNightVisionGogglesPacket {

    public ToggleNightVisionGogglesPacket(FriendlyByteBuf buffer) {

    }

    public ToggleNightVisionGogglesPacket() {

    }

    void encode(FriendlyByteBuf buffer) {

    }

    void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            context.get().enqueueWork(() -> NightVisionGogglesItem.toggle(player));
        }
        context.get().setPacketHandled(true);
    }
}
