package artifacts.data;

import artifacts.Artifacts;
import artifacts.data.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Artifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArtifactsData {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        BlockTags blockTags = new BlockTags(packOutput, lookupProvider, existingFileHelper);
        LootModifiers lootModifiers = new LootModifiers(packOutput);

        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ItemTags(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), lootModifiers);
        generator.addProvider(event.includeServer(), new LootTables(packOutput, existingFileHelper, lootModifiers));
        generator.addProvider(event.includeServer(), new EntityTypeTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new MobEffectTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new SoundDefinitions(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new Advancements(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeClient(), new ItemModels(packOutput, existingFileHelper));

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(generator.getPackOutput(), event.getLookupProvider(), createLevelProvider(), Set.of(Artifacts.MOD_ID)));
    }

    public static RegistrySetBuilder createLevelProvider() {
        RegistrySetBuilder builder = new RegistrySetBuilder();
        builder.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::create);
        builder.add(Registries.PLACED_FEATURE, PlacedFeatures::create);
        return builder;
    }
}
