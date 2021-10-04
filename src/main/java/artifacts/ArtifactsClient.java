package artifacts;

import artifacts.client.render.curio.CurioLayers;
import artifacts.client.render.curio.CurioRenderers;
import artifacts.client.render.entity.MimicRenderer;
import artifacts.client.render.entity.model.MimicChestLayerModel;
import artifacts.client.render.entity.model.MimicModel;
import artifacts.common.config.ModConfig;
import artifacts.common.init.ModEntityTypes;
import artifacts.common.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ArtifactsClient {

    public ArtifactsClient() {
        ModConfig.registerClient();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::onRegisterRenderers);
        modBus.addListener(this::onRegisterLayerDefinitions);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(ModItems.UMBRELLA.get(), new ResourceLocation("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
        CurioRenderers.register();
    }

    public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.MIMIC, MimicRenderer::new);
    }

    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CurioLayers.register(event);
        event.registerLayerDefinition(MimicModel.LAYER_LOCATION, MimicModel::createLayer);
        event.registerLayerDefinition(MimicChestLayerModel.LAYER_LOCATION, MimicChestLayerModel::createLayer);
    }
}
