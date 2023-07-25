package artifacts.forge.network;

import artifacts.forge.item.wearable.WearableArtifactItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleArtifactPacket {

    private final WearableArtifactItem item;

    public ToggleArtifactPacket(FriendlyByteBuf buffer) {
        Item item = BuiltInRegistries.ITEM.get(buffer.readResourceLocation());
        if (!(item instanceof WearableArtifactItem wearableArtifactItem)) {
            throw new IllegalStateException();
        }
        this.item = wearableArtifactItem;
    }

    public ToggleArtifactPacket(WearableArtifactItem item) {
        this.item = item;
    }

    void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(BuiltInRegistries.ITEM.getKey(item));
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            context.get().enqueueWork(() -> item.toggleItem(player));
        }
        context.get().setPacketHandled(true);
    }
}
