package artifacts.forge.world.placement;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.registry.ModPlacementModifierTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class CampsiteHeightRangePlacement extends PlacementModifier {

    private static final CampsiteHeightRangePlacement INSTANCE = new CampsiteHeightRangePlacement();
    public static final Codec<CampsiteHeightRangePlacement> CODEC = Codec.unit(() -> INSTANCE);

    private CampsiteHeightRangePlacement() {

    }

    public static CampsiteHeightRangePlacement campsiteHeightRange() {
        return INSTANCE;
    }

    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource randomSource, BlockPos pos) {
        int minY = ArtifactsForge.CONFIG.common.campsite.minY;
        int maxY = ArtifactsForge.CONFIG.common.campsite.maxY;
        if (minY > maxY) {
            return Stream.of();
        }
        return Stream.of(pos.atY(Mth.randomBetweenInclusive(randomSource, minY, maxY)));
    }

    public PlacementModifierType<?> type() {
        return ModPlacementModifierTypes.CAMPSITE_HEIGHT_RANGE.get();
    }
}
