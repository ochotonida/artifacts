package artifacts.network;

import artifacts.Artifacts;
import artifacts.config.value.ConfigValue;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpdateItemConfigPacket(ConfigValue<?> value) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateItemConfigPacket> TYPE = new CustomPacketPayload.Type<>(Artifacts.id("update_item_configs"));

    public static final StreamCodec<ByteBuf, UpdateItemConfigPacket> CODEC = ByteBufCodecs.STRING_UTF8.dispatch(
            packet -> packet.value.getId(),
            id -> Artifacts.CONFIG.items.getValues().get(id).type().directConfigStreamCodec(id).map(
                    UpdateItemConfigPacket::new,
                    packet -> cast(packet.value())
            )
    );

    @SuppressWarnings("unchecked")
    private static <T> ConfigValue<T> cast(ConfigValue<?> value) {
        return (ConfigValue<T>) value;
    }

    void apply(NetworkHandler.PayloadContext ignored) {
        apply(value.getId(), value);
    }

    private <T> void apply(String key, ConfigValue<T> value) {
        Artifacts.LOGGER.debug("Received updated config value for {} from server", key);
        Artifacts.CONFIG.items.getValues(value.type()).get(key).set(value.get());
    }

    @Override
    public CustomPacketPayload.Type<UpdateItemConfigPacket> type() {
        return TYPE;
    }
}
