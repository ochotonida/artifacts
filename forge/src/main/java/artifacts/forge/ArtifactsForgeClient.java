package artifacts.forge;

import artifacts.Artifacts;
import artifacts.client.mimic.MimicRenderer;
import artifacts.client.mimic.model.MimicChestLayerModel;
import artifacts.client.mimic.model.MimicModel;
import artifacts.forge.client.HeliumFlamingoOverlayRenderer;
import artifacts.forge.client.HurtSoundEventHandler;
import artifacts.forge.client.InputEventHandler;
import artifacts.forge.client.UmbrellaArmPoseHandler;
import artifacts.forge.client.item.ArtifactLayers;
import artifacts.forge.client.item.ArtifactRenderers;
import artifacts.forge.client.item.renderer.ArmRenderHandler;
import artifacts.forge.registry.ModItems;
import artifacts.forge.registry.ModKeyMappings;
import artifacts.registry.ModEntityTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ArtifactsForgeClient {

    public ArtifactsForgeClient() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::onRegisterRenderers);
        modBus.addListener(this::onRegisterLayerDefinitions);
        modBus.addListener(this::onRegisterGuiOverlays);
        modBus.addListener(ModKeyMappings::register);

        ArmRenderHandler.setup();
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> ItemProperties.register(
                        ModItems.UMBRELLA.get(),
                        Artifacts.id("blocking"),
                        (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0)
        );
        ArtifactRenderers.register();
        InputEventHandler.setup();
        HurtSoundEventHandler.setup();
        UmbrellaArmPoseHandler.setup();
    }

    public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.MIMIC.get(), MimicRenderer::new);
    }

    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ArtifactLayers.register(event);
        event.registerLayerDefinition(MimicModel.LAYER_LOCATION, MimicModel::createLayer);
        event.registerLayerDefinition(MimicChestLayerModel.LAYER_LOCATION, MimicChestLayerModel::createLayer);
    }

    public void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(), "helium_flamingo_charge", HeliumFlamingoOverlayRenderer::render);
    }
}
