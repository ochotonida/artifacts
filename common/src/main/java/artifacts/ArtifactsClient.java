package artifacts;

import artifacts.client.CloudInABottleInputHandler;
import artifacts.client.ToggleKeyHandler;
import artifacts.client.item.ArtifactLayers;
import artifacts.client.mimic.MimicRenderer;
import artifacts.client.mimic.model.MimicChestLayerModel;
import artifacts.client.mimic.model.MimicModel;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;

public class ArtifactsClient {

    public static void init() {
        ModKeyMappings.register();
        registerLayerDefinitions();
        registerRenderers();
        ClientLifecycleEvent.CLIENT_STARTED.register(clientState -> onClientStarted());
    }

    public static void onClientStarted() {
        if (!ModItems.NIGHT_VISION_GOGGLES.getRegistrySupplier().isPresent()) {
            Artifacts.LOGGER.error("Detected broken mod state, skipping input registration");
            return;
        }
        ToggleKeyHandler.register();
        CloudInABottleInputHandler.register();
        HeliumFlamingoInputEventHandler.register();
        ItemPropertiesRegistry.register(
                ModItems.UMBRELLA.get(),
                Artifacts.id("blocking"),
                (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0
        );
    }

    public static void registerLayerDefinitions() {
        ArtifactLayers.register();
        EntityModelLayerRegistry.register(MimicModel.LAYER_LOCATION, MimicModel::createLayer);
        EntityModelLayerRegistry.register(MimicChestLayerModel.LAYER_LOCATION, MimicChestLayerModel::createLayer);
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntityTypes.MIMIC, MimicRenderer::new);
    }
}
