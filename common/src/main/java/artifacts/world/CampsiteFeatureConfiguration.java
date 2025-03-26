package artifacts.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CampsiteFeatureConfiguration(
        BlockStateProvider litCampfires,
        BlockStateProvider unlitCampfires,
        BlockStateProvider decorations,
        BlockStateProvider craftingStations,
        BlockStateProvider furnaces,
        BlockStateProvider furnaceChimneys,
        BlockStateProvider beds,
        BlockStateProvider lightSources,
        BlockStateProvider unlitLightSources,
        BlockStateProvider floor
) implements FeatureConfiguration {

    public static final Codec<CampsiteFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockStateProvider.CODEC.fieldOf("lit_campfires").forGetter(CampsiteFeatureConfiguration::litCampfires),
                    BlockStateProvider.CODEC.fieldOf("unlit_campfires").forGetter(CampsiteFeatureConfiguration::unlitCampfires),
                    BlockStateProvider.CODEC.fieldOf("decorations").forGetter(CampsiteFeatureConfiguration::decorations),
                    BlockStateProvider.CODEC.fieldOf("crafting_stations").forGetter(CampsiteFeatureConfiguration::craftingStations),
                    BlockStateProvider.CODEC.fieldOf("furnaces").forGetter(CampsiteFeatureConfiguration::furnaces),
                    BlockStateProvider.CODEC.fieldOf("furnace_chimneys").forGetter(CampsiteFeatureConfiguration::furnaceChimneys),
                    BlockStateProvider.CODEC.fieldOf("beds").forGetter(CampsiteFeatureConfiguration::beds),
                    BlockStateProvider.CODEC.fieldOf("light_sources").forGetter(CampsiteFeatureConfiguration::lightSources),
                    BlockStateProvider.CODEC.fieldOf("unlit_light_sources").forGetter(CampsiteFeatureConfiguration::unlitLightSources),
                    BlockStateProvider.CODEC.fieldOf("floor").forGetter(CampsiteFeatureConfiguration::floor)
            ).apply(instance, CampsiteFeatureConfiguration::new)
    );
}
