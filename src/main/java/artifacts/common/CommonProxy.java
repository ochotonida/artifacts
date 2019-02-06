package artifacts.common;

import artifacts.Artifacts;
import artifacts.common.entity.EntityHallowStar;
import artifacts.common.entity.EntityMimic;
import artifacts.common.network.PacketBottledCloudJump;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public static final SimpleNetworkWrapper NETWORK_HANDLER_INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("artifacts");

    public void preInit() {
        EntityRegistry.registerModEntity(new ResourceLocation(Artifacts.MODID, "mimic"), EntityMimic.class, Artifacts.MODID + ".mimic", 0, Artifacts.instance, 64, 3, true, 0xb27725, 0x261701);
        EntityRegistry.registerModEntity(new ResourceLocation(Artifacts.MODID, "hallow_star"), EntityHallowStar.class, Artifacts.MODID + ".hallow_star", 1, Artifacts.instance, 64, 3, true);
    }

    public void init() {
        NETWORK_HANDLER_INSTANCE.registerMessage(PacketBottledCloudJump.PacketHandler.class, PacketBottledCloudJump.class, 0, Side.SERVER);
    }

    public void registerItemRenderer(Item item, int meta, String name) {

    }
}
