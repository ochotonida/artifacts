package artifacts.network;

import artifacts.item.wearable.WearableArtifactItem;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

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

    void apply(Supplier<NetworkManager.PacketContext> context) {
        if (context.get().getPlayer() instanceof ServerPlayer player) {
            context.get().queue(() -> item.toggleItem(player));
        }
    }
}
