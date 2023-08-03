package artifacts.fabric;

import artifacts.Artifacts;
import artifacts.fabric.trinket.WearableArtifactTrinket;
import artifacts.item.wearable.WearableArtifactItem;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;

public class ArtifactsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Artifacts.init();
        registerTrinkets();

        // TODO need this so porting lib doesn't crash on world load
        PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get();
    }

    public void registerTrinkets() {
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof WearableArtifactItem)
                .forEach(item -> TrinketsApi.registerTrinket(item, new WearableArtifactTrinket((WearableArtifactItem) item)));
    }
}
