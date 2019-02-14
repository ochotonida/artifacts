package artifacts.client;

import artifacts.Artifacts;
import artifacts.client.model.layer.*;
import artifacts.client.renderer.RenderHallowStar;
import artifacts.client.renderer.RenderMimic;
import artifacts.common.IProxy;
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
public class ClientProxy implements IProxy {

    @Override
    public void preInit() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMimic.class, RenderMimic.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityHallowStar.class, RenderHallowStar.FACTORY);
    }

    @Override
    public void init() {
        addRenderLayers();
    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Artifacts.MODID + ":" + name, "inventory"));
    }

    private static void addRenderLayers() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();

        RenderPlayer renderPlayer = skinMap.get("default");
        renderPlayer.addLayer(new LayerGloves(false, renderPlayer));
        renderPlayer.addLayer(new LayerSnorkel(renderPlayer));
        renderPlayer.addLayer(new LayerDrinkingHat(renderPlayer));
        renderPlayer.addLayer(new LayerNightVisionGoggles(renderPlayer));
        renderPlayer.addLayer(new LayerShirt(renderPlayer, false));

        renderPlayer = skinMap.get("slim");
        renderPlayer.addLayer(new LayerGloves(true, renderPlayer));
        renderPlayer.addLayer(new LayerSnorkel(renderPlayer));
        renderPlayer.addLayer(new LayerDrinkingHat(renderPlayer));
        renderPlayer.addLayer(new LayerNightVisionGoggles(renderPlayer));
        renderPlayer.addLayer(new LayerShirt(renderPlayer, true));
    }
}
