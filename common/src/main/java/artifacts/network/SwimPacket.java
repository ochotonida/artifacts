package artifacts.network;

import artifacts.component.SwimData;
import artifacts.platform.PlatformServices;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class SwimPacket {

    private final boolean shouldSwim;

    public SwimPacket(FriendlyByteBuf buffer) {
        shouldSwim = buffer.readBoolean();
    }

    public SwimPacket(boolean shouldSwim) {
        this.shouldSwim = shouldSwim;
    }

    void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(shouldSwim);
    }

    void apply(Supplier<NetworkManager.PacketContext> context) {
        Player player = context.get().getPlayer();
        if (player != null) {
            context.get().queue(() -> {
                SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
                if (swimData != null) {
                    swimData.setSwimming(shouldSwim);
                }
            });
        }
    }
}
