package artifacts.neoforge;

import artifacts.neoforge.data.*;
import artifacts.neoforge.data.loot.*;
import artifacts.neoforge.data.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ArtifactsData {

    public static void gatherServerData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getReloadableLookupProvider();

        event.addProvider(new BlockTags(packOutput, registries));
        event.addProvider(new ItemTags(packOutput, registries));
        event.addProvider(new LootModifiers(packOutput, registries));
        event.addProvider(new EntityTypeTags(packOutput, registries));
        event.addProvider(new MobEffectTags(packOutput, registries));
        event.addProvider(new DamageTypeTags(packOutput, registries));
        event.addProvider(new GameEventTags(packOutput, registries));
        event.addProvider(new SoundDefinitions(packOutput));
        event.addProvider(new DataMaps(packOutput, registries));

        event.addProvider(new Language(packOutput));
        event.addProvider(new ItemModels(packOutput));
    }

    public static void gatherDatapackRegistryData(GatherDataRegistryEntriesEvent event) {
        event.add(Registries.FEATURE, Features::create);
        event.add(Registries.PLACED_FEATURE, PlacedFeatures::create);
        event.add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ArtifactsChestLoot::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(ArtifactsEntityEquipmentLoot::new, LootContextParamSets.EQUIPMENT),
                new LootTableProvider.SubProviderEntry(ArtifactsEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(ArtifactsInjectedLoot::new, LootContextParamSets.ALL_PARAMS),
                new LootTableProvider.SubProviderEntry(ArtifactsItemLoot::new, LootContextParamSets.ALL_PARAMS)
        )));
        event.add(Registries.ADVANCEMENT, new AdvancementProvider(List.of(ArtifactsAdvancementSubProvider::new)));
        event.add(Recipes.create());
    }
}
