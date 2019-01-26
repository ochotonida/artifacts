package artifacts.common.worldgen;

import artifacts.common.ModConfig;
import artifacts.common.ModLootTables;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nullable;
import java.util.Random;

public class WorldGenUnderwaterChest implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        for (double chestChance = ModConfig.underwaterChestChance; chestChance > 0; chestChance--) {
            if (random.nextDouble() <= chestChance) {
                generateChest(world, random, chunkX, chunkZ);
            }
        }
    }

    private void generateChest(World world, Random random, int chunkX, int chunkZ) {
        BlockPos pos = getUnderwaterBlock(world, new BlockPos(chunkX * 16 + 8 + random.nextInt(16), 255, chunkZ * 16 + 8 + random.nextInt(16)));

        if (pos != null) {
            world.setBlockState(pos, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.HORIZONTALS[random.nextInt(4)]), 2);
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof TileEntityChest) {
                ((TileEntityChest)tileentity).setLootTable(ModLootTables.CHEST_UNDERWATER, random.nextLong());
            }
        }
    }

    @Nullable
    private BlockPos getUnderwaterBlock(World world, BlockPos pos) {
        int depth = 0;
        while (pos.getY() >= ModConfig.underwaterChestMinHeight) {
            if (world.getBlockState(pos).getMaterial().isLiquid()) {
                depth++;

                if (world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP)) {
                    if (depth >= ModConfig.underwaterChestMinDepth) {
                        return pos;
                    }
                }
            } else if (depth > 0 || !world.isAirBlock(pos)) {
                return null;
            }

            pos = pos.down();
        }
        return null;
    }
}
