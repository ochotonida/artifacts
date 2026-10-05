package artifacts.world.placement;

import artifacts.Artifacts;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.function.Consumer;

public record CampsiteHeightRangePlacement() implements PlacementModifier {

    private static final CampsiteHeightRangePlacement INSTANCE = new CampsiteHeightRangePlacement();
    public static final MapCodec<CampsiteHeightRangePlacement> CODEC = MapCodec.unit(() -> INSTANCE);

    public static CampsiteHeightRangePlacement campsiteHeightRange() {
        return INSTANCE;
    }

    @Override
    public MapCodec<? extends PlacementModifier> codec() {
        return CODEC;
    }

    @Override
    public void modify(PlacementContext placementContext, RandomSource randomSource, BlockPos pos, Consumer<BlockPos> consumer) {
        int minY = Artifacts.CONFIG.general.campsite.minY.get();
        int maxY = Artifacts.CONFIG.general.campsite.maxY.get();
        if (minY <= maxY) {
            consumer.accept(pos.atY(Mth.randomBetweenInclusive(randomSource, minY, maxY)));
        }
    }
}
