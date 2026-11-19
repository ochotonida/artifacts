package artifacts.neoforge;

import artifacts.Artifacts;
import artifacts.ArtifactsClient;
import artifacts.client.CooldownOverlayRenderer;
import artifacts.client.item.ArtifactRenderers;
import artifacts.client.mimic.MimicRenderer;
import artifacts.integration.ModCompat;
import artifacts.neoforge.client.HeliumFlamingoOverlayRenderer;
import artifacts.neoforge.integration.curios.CuriosCompatClient;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModKeyMappings;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;

public class ArtifactsNeoForgeClient {

    public ArtifactsNeoForgeClient(IEventBus modBus) {
        ArtifactsClient.setup();

        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::registerGuiLayers);
        modBus.addListener(this::registerLayerDefinitions);
        modBus.addListener(this::registerEntityRenderers);
        modBus.addListener((RegisterKeyMappingsEvent event) -> ModKeyMappings.register(event::register));
        modBus.addListener(this::registerConditionalItemModelProperties);

        if (ModCompat.CURIOS.isLoaded()) {
            CuriosCompatClient.setup(modBus);
        }

        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post _) -> ArtifactsClient.onClientTick(Minecraft.getInstance()));
        NeoForge.EVENT_BUS.addListener(this::onPlayerLoggingOut);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> {
                    ArtifactRenderers.register();
                    ArtifactsClient.onClientStarted();
                }
        );
    }

    private void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, Artifacts.id("helium_flamingo_charge"), HeliumFlamingoOverlayRenderer::render);
        event.registerAbove(VanillaGuiLayers.HOTBAR, Artifacts.id("artifact_cooldowns"), CooldownOverlayRenderer::render);
    }

    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ArtifactsClient.registerLayerDefinitions(event::registerLayerDefinition);
    }

    private void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.MIMIC.get(), MimicRenderer::new);
    }

    private void registerConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        ArtifactsClient.registerConditionalItemModelProperties(event::register);
    }

    private void onPlayerLoggingOut(ClientPlayerNetworkEvent.LoggingOut ignored) {
        Artifacts.onClientDisconnect();
    }
}
