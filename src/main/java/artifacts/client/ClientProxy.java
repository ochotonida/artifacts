package artifacts.client;

import artifacts.Artifacts;
import artifacts.client.renderer.RenderMimic;
import artifacts.common.CommonProxy;
import artifacts.common.entity.EntityMimic;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityMimic.class, RenderMimic.FACTORY);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Artifacts.MODID + ":" + name, "inventory"));
    }
}
