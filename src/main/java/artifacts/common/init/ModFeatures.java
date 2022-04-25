package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.world.CampsiteFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(Registry.FEATURE_REGISTRY, Artifacts.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAMPSITE = REGISTRY.register("campsite", CampsiteFeature::new);

    public static PlacedFeature UNDERGROUND_CAMPSITE;

    public static void register() {
        ConfiguredFeature<?, ?> configuredFeature = Registry.register(
                BuiltinRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(Artifacts.MODID, "campsite"),
                new ConfiguredFeature<>(CAMPSITE.get(), FeatureConfiguration.NONE)
        );

        ResourceKey<ConfiguredFeature<?, ?>> featureKey = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(configuredFeature).orElseThrow();
        Holder<ConfiguredFeature<?, ?>> featureHolder = BuiltinRegistries.CONFIGURED_FEATURE.getHolderOrThrow(featureKey);

        UNDERGROUND_CAMPSITE = Registry.register(
                BuiltinRegistries.PLACED_FEATURE,
                new ResourceLocation(Artifacts.MODID, "underground_campsite"),
                new PlacedFeature(featureHolder,
                        List.of(
                                RarityFilter.onAverageOnceEvery(ModConfig.common.campsiteRarity.get()),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                         VerticalAnchor.aboveBottom(ModConfig.common.campsiteMinY.get()),
                                         VerticalAnchor.aboveBottom(ModConfig.common.campsiteMaxY.get())
                                ),
                                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                                BiomeFilter.biome()
                        )
                )
        );
    }
}