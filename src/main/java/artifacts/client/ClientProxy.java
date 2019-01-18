package artifacts.client;

import artifacts.Artifacts;
import artifacts.common.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Artifacts.MODID + ":" + name, "inventory"));
    }
}
