package artifacts.common.network;

import artifacts.common.item.wearable.WearableArtifactItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ToggleArtifactPacket {

    private final WearableArtifactItem item;

    public ToggleArtifactPacket(FriendlyByteBuf buffer) {
        Item item = ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
        if (!(item instanceof WearableArtifactItem wearableArtifactItem)) {
            throw new IllegalStateException();
        }
        this.item = wearableArtifactItem;
    }

    public ToggleArtifactPacket(WearableArtifactItem item) {
        this.item = item;
    }

    void encode(FriendlyByteBuf buffer) {
        //noinspection ConstantConditions
        buffer.writeResourceLocation(ForgeRegistries.ITEMS.getKey(item));
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            context.get().enqueueWork(() -> item.toggleItem(player));
        }
        context.get().setPacketHandled(true);
    }
}
