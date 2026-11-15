package artifacts.neoforge;

import artifacts.neoforge.data.*;
import artifacts.neoforge.data.advancement.ArtifactsAdvancementSubProvider;
import artifacts.neoforge.data.loot.*;
import artifacts.neoforge.data.tags.*;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ArtifactsData {

    // not sure why client & server data are combined in a single method when there's a separate event for each,
    // but from what I recall the docs did the same thing
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getReloadableLookupProvider();

        // data pack resources
        event.addProvider(new BlockTags(packOutput, registries));
        event.addProvider(new ItemTags(packOutput, registries));
        event.addProvider(new LootModifiers(packOutput, registries));
        event.addProvider(new EntityTypeTags(packOutput, registries));
        event.addProvider(new MobEffectTags(packOutput, registries));
        event.addProvider(new DamageTypeTags(packOutput, registries));
        event.addProvider(new GameEventTags(packOutput, registries));
        event.addProvider(new DataMaps(packOutput, registries));

        // client resources
        event.addProvider(new SoundDefinitions(packOutput));
        event.addProvider(new Language(packOutput));
        event.addProvider(new ItemModels(packOutput));
    }

    public static void gatherDatapackRegistryData(GatherDataRegistryEntriesEvent event) {
        event.add(Registries.FEATURE, Features::create);
        event.add(Registries.PLACED_FEATURE, PlacedFeatures::create);
        event.add(Registries.LOOT_TABLE, createLootTableProvider());
        event.add(Registries.ADVANCEMENT, createAdvancementProvider());
        event.add(Recipes.create());
    }

    private static SingleRegistryBootstrap<LootTable> createLootTableProvider() {
        return new LootTableProvider(Set.of(), List.of(
                // utility loot tables referenced by other loot tables
                // not sure if EMPTY or ALL_PARAMS is the right one to use here
                new LootTableProvider.SubProviderEntry(ArtifactsItemLoot::new, LootContextParamSets.EMPTY),

                // campsite chest & barrel loot
                new LootTableProvider.SubProviderEntry(ArtifactsChestLoot::new, LootContextParamSets.CHEST),

                // artifacts worn by spawned entities
                new LootTableProvider.SubProviderEntry(ArtifactsEntityEquipmentLoot::new, LootContextParamSets.EQUIPMENT),

                // mimic loot
                new LootTableProvider.SubProviderEntry(ArtifactsEntityLoot::new, LootContextParamSets.ENTITY),

                // injected loot
                new LootTableProvider.SubProviderEntry(ArtifactsInjectedStructureLoot::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(ArtifactsInjectedEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(ArtifactsInjectedArchaeologyLoot::new, LootContextParamSets.ARCHAEOLOGY)
        ));
    }

    private static SingleRegistryBootstrap<Advancement> createAdvancementProvider() {
        return new AdvancementProvider(List.of(
                ArtifactsAdvancementSubProvider::new
        ));
    }
}
