package artifacts.common.init;

import artifacts.common.network.PacketBottledCloudJump;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetworkHandler {

    public static final SimpleNetworkWrapper NETWORK_HANDLER_INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("artifacts");

    public static void init() {
        NETWORK_HANDLER_INSTANCE.registerMessage(PacketBottledCloudJump.PacketHandler.class, PacketBottledCloudJump.class, 0, Side.SERVER);
    }

}
