package artifacts.world.placement;

import artifacts.Artifacts;
import artifacts.config.value.ConfigValue;
import artifacts.registry.ModPlacementModifierTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ConfigValueFilter extends PlacementFilter {

    public static final MapCodec<ConfigValueFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Artifacts.CONFIG.general.campsite.codec().fieldOf("value").forGetter(f -> f.value),
            Codec.BOOL.fieldOf("expected").forGetter(f -> f.expected)
    ).apply(instance, ConfigValueFilter::new));

    private final ConfigValue<Boolean> value;
    private final boolean expected;

    private ConfigValueFilter(ConfigValue<Boolean> value, boolean expected) {
        this.value = value;
        this.expected = expected;
    }

    public static ConfigValueFilter checkValue(ConfigValue<Boolean> value, boolean expected) {
        return new ConfigValueFilter(value, expected);
    }

    @Override
    protected boolean shouldPlace(PlacementContext placementContext, RandomSource randomSource, BlockPos blockPos) {
        return value.get() ^ !expected;
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifierTypes.CONFIG_VALUE_FILTER.value();
    }
}
