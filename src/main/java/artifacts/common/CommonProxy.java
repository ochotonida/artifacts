package artifacts.common;

import artifacts.common.network.PacketBottledCloudJump;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public static final SimpleNetworkWrapper NETWORK_HANDLER_INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("artifacts");

    public void init() {
        NETWORK_HANDLER_INSTANCE.registerMessage(PacketBottledCloudJump.PacketHandler.class, PacketBottledCloudJump.class, 0, Side.SERVER);
    }

    public void registerItemRenderer(Item item, int meta, String name) {

    }
}
