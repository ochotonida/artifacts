package artifacts.fabric;

import artifacts.Artifacts;
import artifacts.event.ArtifactHooks;
import artifacts.fabric.event.ArtifactHooksFabric;
import artifacts.fabric.network.FabricNetworkHandler;
import artifacts.fabric.registry.ModFeaturesFabric;
import artifacts.fabric.registry.ModLootTablesFabric;
import artifacts.fabric.registry.ModResourceConditions;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModGameEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.VibrationFrequencyRegistry;

public class ArtifactsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Artifacts.setup();
        ArtifactHooksFabric.register();
        ModFeaturesFabric.register();
        ModResourceConditions.register();
        FabricNetworkHandler.register();
        ModEntityTypes.registerMobAttributes(FabricDefaultAttributeRegistry::register);

        ServerEntityEvents.ENTITY_LOAD.register((entity, _) -> ArtifactHooks.onEntityAdded(entity));

        ModGameEvents.VIBRATION_FREQUENCIES.forEach((holder, frequency) ->
                VibrationFrequencyRegistry.register(holder.getKey(), frequency)
        );

        ServerLifecycleEvents.SERVER_STARTING.register(Artifacts::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(_ -> Artifacts.onServerStopping());

        ServerConfigurationConnectionEvents.CONFIGURE.register((listener, _) -> Artifacts.onSendConfiguration(listener::send));

        LootTableEvents.MODIFY.register((key, builder, source, _) -> ModLootTablesFabric.onLootTableLoad(key, builder, source));
    }
}
