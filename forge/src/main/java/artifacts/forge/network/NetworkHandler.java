package artifacts.forge.network;

import artifacts.forge.ArtifactsForge;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ArtifactsForge.id("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(0, DoubleJumpPacket.class, DoubleJumpPacket::encode, DoubleJumpPacket::new, DoubleJumpPacket::handle);
        INSTANCE.registerMessage(1, SwimPacket.class, SwimPacket::encode, SwimPacket::new, SwimPacket::handle);
        INSTANCE.registerMessage(2, IntegerGameRuleChangedPacket.class, IntegerGameRuleChangedPacket::encode, IntegerGameRuleChangedPacket::new, IntegerGameRuleChangedPacket::handle);
        INSTANCE.registerMessage(3, BooleanGameRuleChangedPacket.class, BooleanGameRuleChangedPacket::encode, BooleanGameRuleChangedPacket::new, BooleanGameRuleChangedPacket::handle);
        INSTANCE.registerMessage(4, ToggleArtifactPacket.class, ToggleArtifactPacket::encode, ToggleArtifactPacket::new, ToggleArtifactPacket::handle);
    }
}
