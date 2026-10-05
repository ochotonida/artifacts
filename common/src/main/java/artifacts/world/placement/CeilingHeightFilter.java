package artifacts.world.placement;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public record CeilingHeightFilter(int maxHeight) implements PlacementFilter {

    public static final MapCodec<CeilingHeightFilter> CODEC = ExtraCodecs.NON_NEGATIVE_INT
            .fieldOf("max_height")
            .xmap(CeilingHeightFilter::new, CeilingHeightFilter::maxHeight);

    @Override
    public MapCodec<? extends PlacementFilter> codec() {
        return CODEC;
    }

    @Override
    public boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        if (maxHeight == 0) {
            return true;
        }
        for (int i = 1; i <= maxHeight; i ++) {
            if (!context.getBlockState(pos.above(i)).isAir()) {
                return true;
            }
        }

        return false;
    }

    public static CeilingHeightFilter maxCeilingHeight(int maxHeight) {
        return new CeilingHeightFilter(maxHeight);
    }
}
