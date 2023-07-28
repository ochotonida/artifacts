package artifacts.network;

import artifacts.Artifacts;
import dev.architectury.networking.NetworkChannel;

public class NetworkHandler {

    public static final NetworkChannel CHANNEL = NetworkChannel.create(Artifacts.id("networking_channel"));

    public static void register() {
        CHANNEL.register(BooleanGameRuleChangedPacket.class, BooleanGameRuleChangedPacket::encode, BooleanGameRuleChangedPacket::new, BooleanGameRuleChangedPacket::apply);
        CHANNEL.register(IntegerGameRuleChangedPacket.class, IntegerGameRuleChangedPacket::encode, IntegerGameRuleChangedPacket::new, IntegerGameRuleChangedPacket::apply);
        CHANNEL.register(ToggleArtifactPacket.class, ToggleArtifactPacket::encode, ToggleArtifactPacket::new, ToggleArtifactPacket::apply);
    }
}
