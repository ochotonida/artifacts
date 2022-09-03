package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.world.CampsiteFeature;
import artifacts.common.world.CeilingHeightFilter;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
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

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, Artifacts.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, Artifacts.MODID);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Artifacts.MODID);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Artifacts.MODID);

    public static final RegistryObject<PlacementModifierType<CeilingHeightFilter>> CEILING_HEIGHT_FILTER = PLACEMENT_MODIFIERS.register("ceiling_height_filter", () -> () -> CeilingHeightFilter.CODEC);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAMPSITE = FEATURES.register("campsite", CampsiteFeature::new);
    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_CAMPSITE = CONFIGURED_FEATURES.register("campsite", () -> new ConfiguredFeature<>(CAMPSITE.get(), FeatureConfiguration.NONE));

    public static PlacedFeature UNDERGROUND_CAMPSITE;

    public static void register() {
        UNDERGROUND_CAMPSITE = Registry.register(
                BuiltinRegistries.PLACED_FEATURE,
                new ResourceLocation(Artifacts.MODID, "underground_campsite"),
                new PlacedFeature(
                        BuiltinRegistries.CONFIGURED_FEATURE.getHolderOrThrow(
                                BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(
                                        CONFIGURED_CAMPSITE.get()
                                ).orElseThrow()
                        ),
                        List.of(
                                CountPlacement.of(ModConfig.common.campsiteCount.get()),
                                RarityFilter.onAverageOnceEvery(ModConfig.common.campsiteRarity.get()),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(ModConfig.common.campsiteMinY.get()),
                                        VerticalAnchor.absolute(ModConfig.common.campsiteMaxY.get())
                                ),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.DOWN,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        ModConfig.common.campsiteScanRange.get()
                                ),
                                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                                CeilingHeightFilter.create(ModConfig.common.campsiteMaxCeilingHeight.get()),
                                BiomeFilter.biome()
                        )
                )
        );
    }
}
