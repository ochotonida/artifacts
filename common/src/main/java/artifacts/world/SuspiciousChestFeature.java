package artifacts.world;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public record SuspiciousChestFeature(CampsiteChestConfiguration chestConfig) implements AbstractCampsiteFeature {

    public static final MapCodec<SuspiciousChestFeature> CODEC = CampsiteChestConfiguration.CODEC.fieldOf("chest")
            .xmap(SuspiciousChestFeature::new, SuspiciousChestFeature::chestConfig);

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        placeChest(level, origin, random, direction.getOpposite(), chestConfig());

        return true;
    }
}
