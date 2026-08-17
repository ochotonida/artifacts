package artifacts.neoforge;

import artifacts.Artifacts;
import artifacts.ArtifactsClient;
import artifacts.client.CooldownOverlayRenderer;
import artifacts.client.item.ArtifactRenderers;
import artifacts.client.mimic.MimicRenderer;
import artifacts.integration.ModCompat;
import artifacts.neoforge.client.ArmRenderHandler;
import artifacts.neoforge.client.HeliumFlamingoOverlayRenderer;
import artifacts.neoforge.client.UmbrellaArmPoseHandler;
import artifacts.neoforge.integration.curios.CuriosCompatClient;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
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

        boolean isCuriosLoaded = ModCompat.CURIOS.isLoaded();
        boolean isTrinketsLoaded = ModCompat.TRINKETS.isLoaded();

        if (isCuriosLoaded || isTrinketsLoaded) {
            ArmRenderHandler.setup();
        }
        if (isCuriosLoaded) {
            CuriosCompatClient.setup(modBus);
        }

        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post event) -> ArtifactsClient.onClientTick(Minecraft.getInstance()));
    }

    @SuppressWarnings("deprecation")
    public void onClientSetup(FMLClientSetupEvent event) {
        UmbrellaArmPoseHandler.setup();

        event.enqueueWork(
                () -> {
                    ItemProperties.register(
                            ModItems.UMBRELLA.value(),
                            Artifacts.id("blocking"),
                            (ItemPropertyFunction) (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0
                    );
                    ArtifactsClient.registerItemPropertyFunctions((item, id, f) -> ItemProperties.register(item, id, (ItemPropertyFunction) f));
                    ArtifactRenderers.register();
                    ArtifactsClient.onClientStarted();
                }
        );
    }

    public void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, Artifacts.id("helium_flamingo_charge"), HeliumFlamingoOverlayRenderer::render);
        event.registerAbove(VanillaGuiLayers.HOTBAR, Artifacts.id("artifact_cooldowns"), CooldownOverlayRenderer::render);
    }

    public void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ArtifactsClient.registerLayerDefinitions(event::registerLayerDefinition);
    }

    public void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.MIMIC.get(), MimicRenderer::new);
    }
}
