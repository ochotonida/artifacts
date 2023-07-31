package artifacts.forge;

import artifacts.Artifacts;
import artifacts.ArtifactsClient;
import artifacts.config.ModConfig;
import artifacts.entity.MimicEntity;
import artifacts.forge.capability.SwimHandler;
import artifacts.forge.event.ArtifactEventHandler;
import artifacts.forge.network.NetworkHandler;
import artifacts.forge.registry.ModLootModifiers;
import artifacts.registry.ModEntityTypes;
import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Artifacts.MOD_ID)
public class ArtifactsForge {

    public ArtifactsForge() {
        EventBuses.registerModEventBus(Artifacts.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        Artifacts.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArtifactsClient::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArtifactsForgeClient::new);

        registerConfig();

        SwimHandler.setup();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLootModifiers.LOOT_MODIFIERS.register(modBus);

        modBus.addListener(this::commonSetup);
        ArtifactEventHandler.register();

        modBus.addListener(ArtifactsForge::registerAttributes);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.MIMIC.get(), MimicEntity.createMobAttributes().build());
    }

    private void registerConfig() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get()
                )
        );
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }
}
