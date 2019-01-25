package artifacts.common.worldgen;

import artifacts.common.ModConfig;
import artifacts.common.entity.EntityMimic;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class WorldGenUndergroundChest implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (random.nextDouble() > ModConfig.undergroundChestChance) {
            return;
        }

        BlockPos pos = getFirstTopSolidBlock(world, new BlockPos(chunkX * 16 + 8 + random.nextInt(16), 1, chunkZ * 16 + 8 + random.nextInt(16)));

        if (pos != null) {
            if (random.nextDouble() < ModConfig.undergroundChestMimicRatio) {
                EntityMimic mimic = new EntityMimic(world);
                mimic.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, random.nextInt(4) * 90, 0);
                mimic.setDormant();
                mimic.enablePersistence();
                world.spawnEntity(mimic);
            } else {
                world.setBlockState(pos, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.HORIZONTALS[random.nextInt(4)]));
            }
        }

    }

    @Nullable
    private BlockPos getFirstTopSolidBlock(World world, BlockPos pos) {
        while (pos.getY() <= ModConfig.underGroundChestMaxHeight) {
            IBlockState downState = world.getBlockState(pos.down());
            if (downState.isSideSolid(world, pos, EnumFacing.UP) && world.isAirBlock(pos)) {
                return pos;
            }
            pos = pos.up();
        }
        return null;
    }
}
