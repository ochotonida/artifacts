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
        if (random.nextInt(100) < config.chance) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            pos = new BlockPos(pos.getX() + x, Config.campsiteMinY, pos.getZ() + z);
            while (pos.getY() <= Config.campsiteMaxY) {
                // noinspection deprecation
                if (helper.func_242894_a(pos).isAir() && helper.func_242894_a(pos.down()).getMaterial().blocksMovement()) {
                    return Stream.of(pos);
                }
                pos = pos.up();
            }
        }
        return Stream.empty();
    }
}
