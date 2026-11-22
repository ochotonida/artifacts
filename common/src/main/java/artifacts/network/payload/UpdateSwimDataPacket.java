package artifacts.network.payload;

import artifacts.Artifacts;
import artifacts.component.SwimData;
import artifacts.network.PayloadContext;
import artifacts.platform.PlatformServices;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record UpdateSwimDataPacket(SwimData swimData) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateSwimDataPacket> TYPE = new CustomPacketPayload.Type<>(Artifacts.id("update_swim_data"));

    public static final StreamCodec<FriendlyByteBuf, UpdateSwimDataPacket> CODEC = SwimData.STREAM_CODEC
            .map(UpdateSwimDataPacket::new, UpdateSwimDataPacket::swimData);

    public void apply(PayloadContext context) {
        Player player = context.player();
        if (player != null) {
            context.queue(() -> {
                SwimData swimData = PlatformServices.getPlatformHelper().getSwimData(player);
                if (swimData != null) {
                    swimData.update(swimData());
                }
            });
        }
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
