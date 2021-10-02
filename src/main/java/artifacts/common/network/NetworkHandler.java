package artifacts.common.network;

import artifacts.Artifacts;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Artifacts.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(0, DoubleJumpPacket.class, DoubleJumpPacket::encode, DoubleJumpPacket::new, DoubleJumpPacket::handle);
        INSTANCE.registerMessage(1, SwimPacket.class, SwimPacket::encode, SwimPacket::new, SwimPacket::handle);
        INSTANCE.registerMessage(2, SinkPacket.class, SinkPacket::encode, SinkPacket::new, SinkPacket::handle);
    }
}
