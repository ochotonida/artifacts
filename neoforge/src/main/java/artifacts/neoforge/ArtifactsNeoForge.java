package artifacts.neoforge;

import artifacts.Artifacts;
import artifacts.config.screen.ArtifactsConfigScreen;
import artifacts.integration.ModCompat;
import artifacts.neoforge.event.ArtifactHooksNeoForge;
import artifacts.neoforge.integration.curios.CuriosCompat;
import artifacts.neoforge.network.NeoForgeNetworkHandler;
import artifacts.neoforge.registry.ModAttachmentTypes;
import artifacts.neoforge.registry.ModConditions;
import artifacts.neoforge.registry.ModLootModifiers;
import artifacts.registry.ModEntityTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ConfigurationTask;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

@Mod(Artifacts.MOD_ID)
public class ArtifactsNeoForge {

    private static IEventBus modBus;

    public ArtifactsNeoForge(IEventBus modBus) {
        ArtifactsNeoForge.modBus = modBus;

        Artifacts.setup();
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            new ArtifactsNeoForgeClient(modBus);
        }

        ModConditions.CONDITIONS.register();
        ModLootModifiers.LOOT_MODIFIERS.register();
        ModAttachmentTypes.ATTACHMENT_TYPES.register();

        modBus.addListener(ArtifactsData::gatherData);
        modBus.addListener(ArtifactsData::gatherDatapackRegistryData);
        modBus.addListener(NeoForgeNetworkHandler::registerPayloadHandlers);
        modBus.addListener(this::registerConfigurationTasks);
        NeoForge.EVENT_BUS.addListener((ServerStartingEvent event) -> Artifacts.onServerStarting(event.getServer()));
        NeoForge.EVENT_BUS.addListener((ServerStoppingEvent _) -> Artifacts.onServerStopping());
        modBus.addListener((EntityAttributeCreationEvent event) -> ModEntityTypes.registerMobAttributes(event::put));

        registerConfigScreen();
        ArtifactHooksNeoForge.register();

        if (ModCompat.CURIOS.isLoaded()) {
            CuriosCompat.setup();
        }

        ArtifactsNeoForge.modBus = null;
    }

    private void registerConfigScreen() {
        if (ModCompat.CLOTH_CONFIG.isLoaded()) {
            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (_, parent) -> new ArtifactsConfigScreen(parent).build()
            );
        }
    }

    private void registerConfigurationTasks(RegisterConfigurationTasksEvent event) {
        event.register(new ConfigurationTask() {

            private static final Type TYPE = new Type(Artifacts.id("configuration"));

            @Override
            public void start(Consumer<Packet<?>> consumer) {
                Artifacts.onSendConfiguration(consumer);
                event.getListener().finishCurrentTask(TYPE);
            }

            @Override
            public Type type() {
                return TYPE;
            }
        });
    }

    public static void addDeferredRegister(DeferredRegister<?> register) {
        register.register(modBus);
    }
}
