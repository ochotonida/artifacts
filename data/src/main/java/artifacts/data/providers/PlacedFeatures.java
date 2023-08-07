package artifacts.data.providers;

import artifacts.registry.ModFeatures;
import artifacts.world.placement.CampsiteCountPlacement;
import artifacts.world.placement.CampsiteHeightRangePlacement;
import artifacts.world.placement.CeilingHeightFilter;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatures {

    public static void create(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> campsite = configuredFeatures.getOrThrow(ConfiguredFeatures.CAMPSITE);

        PlacedFeature undergroundCampsite = new PlacedFeature(
                campsite,
                List.of(
                        CampsiteCountPlacement.campsiteCount(),
                        InSquarePlacement.spread(),
                        CampsiteHeightRangePlacement.campsiteHeightRange(),
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

        context.register(ModFeatures.UNDERGROUND_CAMPSITE, undergroundCampsite);
    }
}
