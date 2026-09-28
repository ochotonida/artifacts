package artifacts;

import artifacts.client.CloudInABottleInputHandler;
import artifacts.client.HeliumFlamingoOverlay;
import artifacts.client.ToggleKeyHandlers;
import artifacts.client.item.ArtifactLayers;
import artifacts.client.item.property.NeedsRepair;
import artifacts.client.mimic.MimicModel;
import artifacts.event.SwimInAirInputHooks;
import artifacts.integration.ModCompat;
import artifacts.integration.trinkets.TrinketsCompatClient;
import artifacts.registry.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;

public class ArtifactsClient {

    private static final HeliumFlamingoOverlay HELIUM_FLAMINGO_OVERLAY = new HeliumFlamingoOverlay();

    public static void setup() {
        if (ModCompat.TRINKETS.isLoaded()) {
            TrinketsCompatClient.setup();
        }
    }

    public static boolean isConnectedToRemoteServer() {
        return !Minecraft.getInstance().hasSingleplayerServer()
                && Minecraft.getInstance().getConnection() != null;
    }

    public static HeliumFlamingoOverlay getHeliumFlamingoOverlay() {
        return HELIUM_FLAMINGO_OVERLAY;
    }

    public static void onClientTick(Minecraft instance) {
        SwimInAirInputHooks.onClientTick(instance);
        CloudInABottleInputHandler.onClientTick(instance);
        ToggleKeyHandlers.onClientTick();
    }

    public static void onClientStarted() {
        if (!ModItems.NIGHT_VISION_GOGGLES.isBound()) {
            Artifacts.LOGGER.error("Detected broken mod state, skipping input registration");
            return;
        }
        ToggleKeyHandlers.init();
    }

    public static void registerLayerDefinitions(ArtifactLayers.LayerRegistration registration) {
        ArtifactLayers.register(registration);
        registration.register(MimicModel.LAYER_LOCATION, MimicModel::createLayer);
        registration.register(MimicModel.CHEST_LAYER_LOCATION, MimicModel::createChestLayer);
    }

    public static void registerConditionalItemModelProperties(
            BiConsumer<Identifier, MapCodec<? extends ConditionalItemModelProperty>> idMapper
    ) {
        idMapper.accept(Artifacts.id("needs_repair"), NeedsRepair.MAP_CODEC);
    }
}
