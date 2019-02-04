package artifacts.client;

import artifacts.Artifacts;
import artifacts.client.model.GlovesRenderLayer;
import artifacts.client.model.LayerSnorkel;
import artifacts.client.renderer.RenderHallowStar;
import artifacts.client.renderer.RenderMimic;
import artifacts.common.CommonProxy;
import artifacts.common.entity.EntityHallowStar;
import artifacts.common.entity.EntityMimic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.Map;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityMimic.class, RenderMimic.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityHallowStar.class, RenderHallowStar.FACTORY);
    }

    @Override
    public void init() {
        super.init();

        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        RenderPlayer render;
        render = skinMap.get("default");
        render.addLayer(new GlovesRenderLayer(false, render));
        render.addLayer(new LayerSnorkel(render));

        render = skinMap.get("slim");
        render.addLayer(new GlovesRenderLayer(true, render));
        render.addLayer(new LayerSnorkel(render));
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Artifacts.MODID + ":" + name, "inventory"));
    }
}
