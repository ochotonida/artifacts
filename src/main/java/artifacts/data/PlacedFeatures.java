package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.world.CampsiteCountPlacement;
import artifacts.common.world.CeilingHeightFilter;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatures {

    public static final ResourceKey<PlacedFeature> UNDERGROUND_CAMPSITE = Artifacts.key(Registries.PLACED_FEATURE, "underground_campsite");

    public static void create(BootstapContext<PlacedFeature> context) {
        HolderGetter<Feature<?>> featureRegistry = context.lookup(Registries.FEATURE);
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> campsite =configuredFeatures.getOrThrow(ConfiguredFeatures.CAMPSITE);

        PlacedFeature undergroundCampsite = new PlacedFeature(
                campsite,
                List.of(
                        CampsiteCountPlacement.campsiteCount(),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(-60),
                                VerticalAnchor.absolute(40)
                        ),
                        EnvironmentScanPlacement.scanningFor(
                                Direction.DOWN,
                                BlockPredicate.solid(),
                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                8
                        ),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                        CeilingHeightFilter.maxCeilingHeight(6),
                        BiomeFilter.biome()
                )
        );

        context.register(UNDERGROUND_CAMPSITE, undergroundCampsite);
    }
}
