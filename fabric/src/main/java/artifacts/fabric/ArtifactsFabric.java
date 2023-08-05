package artifacts.fabric;

import artifacts.Artifacts;
import artifacts.fabric.event.ArtifactEventsFabric;
import artifacts.fabric.event.SwimEventsFabric;
import artifacts.fabric.registry.ModLootModifiers;
import artifacts.fabric.trinket.WearableArtifactTrinket;
import artifacts.item.wearable.WearableArtifactItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;

public class ArtifactsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Artifacts.init();
        registerTrinkets();

        ModLootModifiers.register();

        ArtifactEventsFabric.register();
        SwimEventsFabric.register();
    }

    public void registerTrinkets() {
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof WearableArtifactItem)
                .forEach(item -> TrinketsApi.registerTrinket(item, new WearableArtifactTrinket((WearableArtifactItem) item)));
    }
}
