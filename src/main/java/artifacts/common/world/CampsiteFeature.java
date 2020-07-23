package artifacts.common.world;

import artifacts.common.config.Config;
import artifacts.common.entity.MimicEntity;
import artifacts.common.init.Entities;
import artifacts.common.init.LootTables;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CampsiteFeature extends Feature<NoFeatureConfig> {

    public static final BlockStateProvider CRAFTING_STATION_PROVIDER = new WeightedBlockStateProvider()
            .addWeightedBlockstate(Blocks.CRAFTING_TABLE.getDefaultState(), 5)
            .addWeightedBlockstate(Blocks.FURNACE.getDefaultState().with(FurnaceBlock.LIT, false), 5)
            .addWeightedBlockstate(Blocks.BLAST_FURNACE.getDefaultState().with(BlastFurnaceBlock.LIT, false), 5)
            .addWeightedBlockstate(Blocks.SMOKER.getDefaultState().with(SmokerBlock.LIT, false), 5)
            .addWeightedBlockstate(Blocks.SMITHING_TABLE.getDefaultState(), 5)
            .addWeightedBlockstate(Blocks.FLETCHING_TABLE.getDefaultState(), 5)
            .addWeightedBlockstate(Blocks.CARTOGRAPHY_TABLE.getDefaultState(), 5)
            .addWeightedBlockstate(Blocks.ANVIL.getDefaultState(), 2)
            .addWeightedBlockstate(Blocks.CHIPPED_ANVIL.getDefaultState(), 2)
            .addWeightedBlockstate(Blocks.DAMAGED_ANVIL.getDefaultState(), 1);

    public static final BlockStateProvider DECORATION_PROVIDER = new WeightedBlockStateProvider()
            .addWeightedBlockstate(Blocks.LANTERN.getDefaultState(), 5)
            .addWeightedBlockstate(Blocks.TORCH.getDefaultState(), 3)
            .addWeightedBlockstate(Blocks.REDSTONE_TORCH.getDefaultState(), 3)
            .addWeightedBlockstate(Blocks.CAKE.getDefaultState(), 1)
            .addWeightedBlockstate(Blocks.BREWING_STAND.getDefaultState(), 4);

    public static final BlockStateProvider ORE_PROVIDER = new WeightedBlockStateProvider()
            .addWeightedBlockstate(Blocks.IRON_ORE.getDefaultState(), 6)
            .addWeightedBlockstate(Blocks.REDSTONE_ORE.getDefaultState(), 6)
            .addWeightedBlockstate(Blocks.LAPIS_ORE.getDefaultState(), 6)
            .addWeightedBlockstate(Blocks.GOLD_ORE.getDefaultState(), 4)
            .addWeightedBlockstate(Blocks.DIAMOND_ORE.getDefaultState(), 2)
            .addWeightedBlockstate(Blocks.EMERALD_ORE.getDefaultState(), 1);

    public CampsiteFeature() {
        super(NoFeatureConfig::deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config) {
        List<BlockPos> positions = new ArrayList<>();
        BlockPos.getAllInBox(pos.add(-3, 0, -3), pos.add(3, 0, 3)).forEach((blockPos -> positions.add(blockPos.toImmutable())));
        positions.remove(pos);
        positions.removeIf(currentPos -> !world.isAirBlock(currentPos));
        positions.removeIf(currentPos -> !world.getBlockState(currentPos.down()).getMaterial().blocksMovement());
        if (positions.size() < 12) {
            return false;
        }
        Collections.shuffle(positions);

        if (random.nextFloat() < Config.campsiteOreChance) {
            generateOreVein(world, pos.down(), random);
        }

        generateLightSource(world, pos, random);

        generateContainer(world, positions.remove(0), random);
        if (random.nextBoolean()) {
            generateContainer(world, positions.remove(0), random);
        }

        generateCraftingStation(world, positions.remove(0), random);
        if (random.nextBoolean()) {
            generateCraftingStation(world, positions.remove(0), random);
        }

        return true;
    }

    public void generateLightSource(IWorld world, BlockPos pos, Random random) {
        if (random.nextInt(4) != 0) {
            BlockPos currentPos = pos;
            while (currentPos.getY() - pos.getY() < 8 && world.isAirBlock(currentPos.up())) {
                currentPos = currentPos.up();
            }
            if (currentPos.getY() - pos.getY() > 2 && !world.isAirBlock(currentPos.up())) {
                if (random.nextInt(12) == 0) {
                    world.setBlockState(currentPos, Blocks.END_ROD.getDefaultState().with(EndRodBlock.FACING, Direction.DOWN), 2);
                } else {
                    world.setBlockState(currentPos, Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true), 2);
                }
                return;
            }
        }
        world.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, random.nextInt(3) != 0), 2);
    }

    public void generateContainer(IWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < Config.campsiteMimicChance) {
            MimicEntity mimic = Entities.MIMIC.create(world.getWorld());
            if (mimic != null) {
                mimic.setDormant();
                mimic.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                world.addEntity(mimic);
            }
        } else {
            if (random.nextBoolean()) {
                if (random.nextInt(4) == 0) {
                    world.setBlockState(pos, Blocks.TRAPPED_CHEST.getDefaultState().with(ChestBlock.FACING, Direction.Plane.HORIZONTAL.random(random)), 2);
                    world.setBlockState(pos.down(), Blocks.TNT.getDefaultState(), 0);
                }
                world.setBlockState(pos, Tags.Blocks.CHESTS_WOODEN.getRandomElement(random).getDefaultState().with(ChestBlock.FACING, Direction.Plane.HORIZONTAL.random(random)), 2);
            } else {
                world.setBlockState(pos, Blocks.BARREL.getDefaultState().with(BarrelBlock.PROPERTY_FACING, Direction.random(random)), 2);
            }
            TileEntity container = world.getTileEntity(pos);
            if (container instanceof LockableLootTileEntity) {
                ((LockableLootTileEntity) container).setLootTable(LootTables.CAMPSITE_CHEST, random.nextLong());
            }
        }
    }

    public void generateCraftingStation(IWorld world, BlockPos pos, Random random) {
        BlockState state = CRAFTING_STATION_PROVIDER.getBlockState(random, pos);
        world.setBlockState(pos, state, 0);
        if (random.nextBoolean() && world.isAirBlock(pos.up())) {
            generateDecoration(world, pos.up(), random);
        }

    }

    public void generateDecoration(IWorld world, BlockPos pos, Random random) {
        if (random.nextBoolean()) {
            world.setBlockState(pos, DECORATION_PROVIDER.getBlockState(random, pos), 2);
            return;
        }
        world.setBlockState(pos, BlockTags.FLOWER_POTS.getRandomElement(random).getDefaultState(), 2);
    }

    public void generateOreVein(IWorld world, BlockPos pos, Random random) {
        BlockState ore = ORE_PROVIDER.getBlockState(random, pos);
        List<BlockPos> positions = new ArrayList<>();
        positions.add(pos);
        for (int i = 4 + random.nextInt(12); i > 0; i--) {
            pos = positions.remove(0);
            world.setBlockState(pos, ore, 2);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (world.getBlockState(pos.offset(direction)).getMaterial().blocksMovement() && !world.getBlockState(pos.offset(direction).up()).getMaterial().blocksMovement()) {
                    positions.add(pos.offset(direction));
                }
            }
            if (positions.size() == 0) {
                return;
            }
        }
    }
}
