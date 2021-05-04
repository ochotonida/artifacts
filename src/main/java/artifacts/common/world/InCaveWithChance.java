package artifacts.common.world;

import artifacts.common.config.Config;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.stream.Stream;

public class InCaveWithChance extends Placement<ChanceConfig> {

    public InCaveWithChance(Codec<ChanceConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random random, ChanceConfig config, BlockPos pos) {
        if (config.chance < 10000 && random.nextFloat() < 1F / config.chance) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            pos = new BlockPos(pos.getX() + x, Config.COMMON.campsiteMinY, pos.getZ() + z);
            while (pos.getY() <= Config.COMMON.campsiteMaxY) {
                // noinspection deprecation
                if (helper.getBlockState(pos).isAir() && helper.getBlockState(pos.below()).getMaterial().blocksMotion()) {
                    return Stream.of(pos);
                }
                pos = pos.above();
            }
        }
        return Stream.empty();
    }
}
