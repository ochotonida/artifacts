package artifacts.data;

import artifacts.Artifacts;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Artifacts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        BlockTags blockTags = new BlockTags(generator, existingFileHelper);
        LootModifiers lootModifiers = new LootModifiers(generator);

        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ItemTags(generator, blockTags, existingFileHelper));
        generator.addProvider(event.includeServer(), lootModifiers);
        generator.addProvider(event.includeServer(), new LootTables(generator, existingFileHelper, lootModifiers));
        generator.addProvider(event.includeServer(), new EntityTypeTags(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new MobEffectTags(generator, existingFileHelper));

        generator.addProvider(event.includeClient(), new ItemModels(generator, existingFileHelper));

        RegistryAccess registryAccess = RegistryAccess.builtinCopy();
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        // noinspection deprecation
        BiomeModifier modifier = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                BuiltinRegistries.BIOME.getOrCreateTag(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(BuiltinRegistries.PLACED_FEATURE.getHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Artifacts.MODID, "underground_campsite")))),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES
        );

        Map<ResourceLocation, BiomeModifier> map = Map.of(new ResourceLocation(Artifacts.MODID, "add_campsite"), modifier);

        JsonCodecProvider<BiomeModifier> provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, Artifacts.MODID, registryOps, ForgeRegistries.Keys.BIOME_MODIFIERS, map);

        generator.addProvider(event.includeServer(), provider);
    }
}
