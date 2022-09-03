package artifacts;

import artifacts.client.render.curio.CurioLayers;
import artifacts.client.render.curio.CurioRenderers;
import artifacts.client.render.curio.renderer.ArmRenderHandler;
import artifacts.client.render.entity.MimicRenderer;
import artifacts.client.render.entity.model.MimicChestLayerModel;
import artifacts.client.render.entity.model.MimicModel;
import artifacts.common.capability.SwimHandler;
import artifacts.common.config.ModConfig;
import artifacts.common.init.ModEntityTypes;
import artifacts.common.init.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ArtifactsClient {

    private static final ResourceLocation HELIUM_FLAMINGO_ICON = new ResourceLocation(Artifacts.MODID, "textures/gui/icons.png");

    public ArtifactsClient() {
        ModConfig.registerClient();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::onRegisterRenderers);
        modBus.addListener(this::onRegisterLayerDefinitions);

        ArmRenderHandler.setup();
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> ItemProperties.register(
                        ModItems.UMBRELLA.get(),
                        new ResourceLocation(Artifacts.MODID, "blocking"),
                        (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0)
        );
        CurioRenderers.register();
        registerHeliumFlamingoOverlay();
    }

    public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.MIMIC.get(), MimicRenderer::new);
    }

    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CurioLayers.register(event);
        event.registerLayerDefinition(MimicModel.LAYER_LOCATION, MimicModel::createLayer);
        event.registerLayerDefinition(MimicChestLayerModel.LAYER_LOCATION, MimicChestLayerModel::createLayer);
    }

    public void registerHeliumFlamingoOverlay() {
        // TODO
        // GuiOverlayManager.registerOverlayAbove(ForgeGui.AIR_LEVEL_ELEMENT, "Helium Flamingo Overlay", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
        //     if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements())
        //     {
        //         gui.setupOverlayRenderState(true, false, HELIUM_FLAMINGO_ICON);
        //         renderHeliumFlamingoOverlay(screenWidth, screenHeight, poseStack, gui);
        //     }
        // });
    }

    public void renderHeliumFlamingoOverlay(int width, int height, PoseStack poseStack, ForgeGui gui) {
        Minecraft minecraft = Minecraft.getInstance();

        if (
                ModConfig.server.isCosmetic(ModItems.HELIUM_FLAMINGO.get())
                || !(minecraft.getCameraEntity() instanceof LivingEntity player)
        ) {
            return;
        }

        player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                handler -> {
                    int left = width / 2 + 91;
                    int top = height - gui.rightHeight;

                    int swimTime = Math.abs(handler.getSwimTime());
                    int maxProgressTime;

                    if (swimTime == 0) {
                        return;
                    } else if (handler.getSwimTime() > 0) {
                        maxProgressTime = ModConfig.server.heliumFlamingo.maxFlightTime.get();
                    } else {
                        maxProgressTime = ModConfig.server.heliumFlamingo.rechargeTime.get();
                    }

                    float progress = 1 - swimTime / (float) maxProgressTime;

                    int full = Mth.ceil((progress - 2D / maxProgressTime) * 10);
                    int partial = Mth.ceil(progress * 10) - full;

                    for (int i = 0; i < full + partial; ++i) {
                        ForgeGui.blit(poseStack, left - i * 8 - 9, top, -90, (i < full ? 0 : 9), 0, 9, 9, 32, 16);
                    }
                    gui.rightHeight += 10;

                    RenderSystem.disableBlend();
                }
        );
    }
}
