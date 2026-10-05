package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.registry.ModFeatures;
import artifacts.world.placement.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatures {

    public static void create(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> configuredFeatures = context.lookup(Registries.FEATURE);

        Holder<Feature> campsite = configuredFeatures.getOrThrow(ConfiguredFeatures.CAMPSITE);
        Holder<Feature> minimalistCampsite = configuredFeatures.getOrThrow(ConfiguredFeatures.MINIMALIST_CAMPSITE);

        PlacedFeature undergroundCampsite = new PlacedFeature(
                campsite,
                createModifiers(false)
        );
        PlacedFeature undergroundMinimalistCampsite = new PlacedFeature(
                minimalistCampsite,
                createModifiers(true)
        );

        context.register(ModFeatures.UNDERGROUND_CAMPSITE, undergroundCampsite);
        context.register(ModFeatures.UNDERGROUND_MINIMALIST_CAMPSITE, undergroundMinimalistCampsite);
    }

    private static List<PlacementModifier> createModifiers(boolean isMinimalist) {
        return List.of(
                new ConfigValueFilter(Artifacts.CONFIG.general.campsite.minimalistCampsites, isMinimalist),
                CampsiteCountPlacement.campsiteCount(),
                RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(),
                CampsiteHeightRangePlacement.campsiteHeightRange(),
                EnvironmentScanPlacement.scanningFor(
                        Direction.DOWN,
                        BlockPredicate.solid(),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        8
                ),
                OffsetPlacement.vertical(ConstantInt.of(1)),
                CeilingHeightFilter.maxCeilingHeight(6),
                new SurfaceFlatnessFilter(),
                BiomeFilter.biome()
        );
    }
}
