package artifacts.common.world;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class InCaveWithChance extends Placement<ChanceConfig> {

    public InCaveWithChance(Function<Dynamic<?>, ? extends ChanceConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, ChanceConfig config, BlockPos pos) {
        if (random.nextFloat() < 1F / config.chance) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            pos = new BlockPos(pos.getX() + x, 0, pos.getZ() + z);
            while (pos.getY() <= Math.min(45, world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos).getY() - 1)) {
                if (world.getBlockState(pos).isAir(world, pos) && world.getBlockState(pos.down()).getMaterial().blocksMovement()) {
                    return Stream.of(pos);
                }
                pos = pos.up();
            }
        }
        return Stream.empty();
    }
}
