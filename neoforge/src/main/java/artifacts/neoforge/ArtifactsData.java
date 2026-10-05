package artifacts.neoforge;

import artifacts.Artifacts;
import artifacts.neoforge.data.*;
import artifacts.neoforge.data.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ArtifactsData {

    public static void gatherServerData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getReloadableLookupProvider();

        LootModifiers lootModifiers = new LootModifiers(packOutput, registries);

        event.addProvider(new BlockTags(packOutput, registries));
        event.addProvider(new ItemTags(packOutput, registries));
        event.addProvider(lootModifiers);
        event.addProvider(new LootTables(lootModifiers));
        event.addProvider(new EntityTypeTags(packOutput, registries));
        event.addProvider(new MobEffectTags(packOutput, registries));
        event.addProvider(new DamageTypeTags(packOutput, registries));
        event.addProvider(new GameEventTags(packOutput, registries));
        event.addProvider(new SoundDefinitions(packOutput));
        event.addProvider(new Advancements(packOutput, registries));
        event.addProvider(new DataMaps(packOutput, registries));
        event.addProvider(new Recipes.Runner(packOutput, registries));

        var worldGen = new DatapackBuiltinEntriesProvider(packOutput, "test", createLevelProvider(), registries, Set.of(Artifacts.MOD_ID));
        event.addProvider(worldGen);

        event.addProvider(new Language(packOutput));
        event.addProvider(new ItemModels(packOutput));
    }

    public static RegistrySetBuilder createLevelProvider() {
        RegistrySetBuilder builder = new RegistrySetBuilder();
        builder.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::create);
        builder.add(Registries.PLACED_FEATURE, PlacedFeatures::create);
        return builder;
    }
}
