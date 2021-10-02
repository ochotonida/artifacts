package artifacts.common.world;

import artifacts.common.config.ModConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.Random;
import java.util.stream.Stream;

public class InCaveWithChance extends FeatureDecorator<ChanceDecoratorConfiguration> {

    public InCaveWithChance(Codec<ChanceDecoratorConfiguration> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecorationContext helper, Random random, ChanceDecoratorConfiguration config, BlockPos pos) {
        if (config.chance < 10000 && random.nextFloat() < 1F / config.chance) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            pos = new BlockPos(pos.getX() + x, ModConfig.common.campsiteMinY.get(), pos.getZ() + z);
            while (pos.getY() <= ModConfig.common.campsiteMaxY.get()) {
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
