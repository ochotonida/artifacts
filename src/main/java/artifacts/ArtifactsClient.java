package artifacts;

import artifacts.client.render.curio.CurioRenderers;
import artifacts.client.render.entity.MimicRenderer;
import artifacts.common.config.ModConfig;
import artifacts.common.init.ModEntities;
import artifacts.common.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ArtifactsClient {

    public ArtifactsClient() {
        ModConfig.registerClient();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::clientSetup);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MIMIC, MimicRenderer::new);
        ItemProperties.register(ModItems.UMBRELLA.get(), new ResourceLocation("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
        CurioRenderers.register();
    }
}
