package artifacts.world.placement;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public record SurfaceFlatnessFilter() implements PlacementFilter {

    public static final MapCodec<SurfaceFlatnessFilter> CODEC = MapCodec.unit(new SurfaceFlatnessFilter());

    @Override
    public MapCodec<? extends PlacementFilter> codec() {
        return CODEC;
    }

    @Override
    public boolean shouldPlace(PlacementContext context, RandomSource randomSource, BlockPos origin) {
        BlockGetter level = context.getLevel();
        return BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 0, 2))
                .filter(pos -> level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP))
                .filter(pos -> level.getBlockState(pos).isAir())
                .count() >= 6;
    }
}
