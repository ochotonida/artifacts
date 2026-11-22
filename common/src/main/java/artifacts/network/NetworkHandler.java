package artifacts.network;

import artifacts.network.payload.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class NetworkHandler {

    public static final List<PayloadHandler<?>> SERVERBOUND_HANDLERS = new ArrayList<>();
    public static final List<PayloadHandler<?>> CLIENTBOUND_HANDLERS = new ArrayList<>();

    public static void registerPayloads() {
        registerClientbound(PlaySoundAtPlayerPacket.TYPE, PlaySoundAtPlayerPacket.CODEC, PlaySoundAtPlayerPacket::apply);
        registerClientbound(UpdateConfigValuePacket.TYPE, UpdateConfigValuePacket.CODEC, UpdateConfigValuePacket::apply);
        registerClientbound(UpdateSwimDataPacket.TYPE, UpdateSwimDataPacket.CODEC, UpdateSwimDataPacket::apply);

        registerServerbound(DoubleJumpPacket.TYPE, DoubleJumpPacket.CODEC, DoubleJumpPacket::apply);
        registerServerbound(ToggleSwimFlyingPacket.TYPE, ToggleSwimFlyingPacket.CODEC, ToggleSwimFlyingPacket::apply);
        registerServerbound(ToggleKeyPressedPacket.TYPE, ToggleKeyPressedPacket.CODEC, ToggleKeyPressedPacket::apply);
    }

    private static <T extends CustomPacketPayload> void registerServerbound(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, Receiver<T> receiver) {
        SERVERBOUND_HANDLERS.add(new PayloadHandler<>(type, codec, receiver));
    }

    private static <T extends CustomPacketPayload> void registerClientbound(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, Receiver<T> receiver) {
        CLIENTBOUND_HANDLERS.add(new PayloadHandler<>(type, codec, receiver));
    }

    public static void sendToServer(CustomPacketPayload payload) {
        ClientPacketListener listener = Objects.requireNonNull(Minecraft.getInstance().getConnection());
        listener.send(new ServerboundCustomPayloadPacket(payload));
    }

    public static void sendToPlayers(Iterable<ServerPlayer> players, CustomPacketPayload payload) {
        for (ServerPlayer player : players) {
            sendToPlayer(player, payload);
        }
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        sendToClient(player.connection::send, payload);
    }

    public static void sendToClient(Consumer<Packet<?>> connection, CustomPacketPayload payload) {
        connection.accept(new ClientboundCustomPayloadPacket(payload));
    }

    public record PayloadHandler<T extends CustomPacketPayload>(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            Receiver<T> receiver
    ) { }

    @FunctionalInterface
    public interface Receiver<T extends CustomPacketPayload> {
        void receive(T value, PayloadContext context);
    }
}
