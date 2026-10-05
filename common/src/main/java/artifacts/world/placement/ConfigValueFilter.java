package artifacts.world.placement;

import artifacts.Artifacts;
import artifacts.config.value.ConfigValue;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public record ConfigValueFilter(ConfigValue<Boolean> value, boolean expected) implements PlacementFilter {

    public static final MapCodec<ConfigValueFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Artifacts.CONFIG.general.campsite.codec().fieldOf("value").forGetter(f -> f.value),
            Codec.BOOL.fieldOf("expected").forGetter(f -> f.expected)
    ).apply(instance, ConfigValueFilter::new));

    @Override
    public MapCodec<? extends PlacementFilter> codec() {
        return CODEC;
    }

    @Override
    public boolean shouldPlace(PlacementContext placementContext, RandomSource randomSource, BlockPos blockPos) {
        return value.get() ^ !expected;
    }
}
