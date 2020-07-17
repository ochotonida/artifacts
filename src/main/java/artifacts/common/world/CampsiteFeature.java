package artifacts.common.world;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import artifacts.common.init.Entities;
import artifacts.common.init.LootTables;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CampsiteFeature extends Feature<NoFeatureConfig> {

    public static ResourceLocation CRAFTING_STATIONS = new ResourceLocation(Artifacts.MODID, "campsite/crafting_stations");
    public static ResourceLocation DECORATION_BLOCKS = new ResourceLocation(Artifacts.MODID, "campsite/decorations");

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
        if (random.nextFloat() < 0.2) {
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
        Tag<Block> craftingStations = BlockTags.getCollection().get(CRAFTING_STATIONS);
        if (craftingStations != null) {
            BlockState state = craftingStations.getRandomElement(random).getDefaultState();
            world.setBlockState(pos, state, 0);
            if (random.nextBoolean() && Block.hasEnoughSolidSide(world, pos.down(), Direction.UP) && world.isAirBlock(pos.up())) {
                generateDecoration(world, pos.up(), random);
            }
        }
    }

    public void generateDecoration(IWorld world, BlockPos pos, Random random) {
        if (random.nextBoolean()) {
            Tag<Block> craftingStations = BlockTags.getCollection().get(DECORATION_BLOCKS);
            if (craftingStations != null) {
                world.setBlockState(pos, craftingStations.getRandomElement(random).getDefaultState(), 2);
                return;
            }
        }
        world.setBlockState(pos, BlockTags.FLOWER_POTS.getRandomElement(random).getDefaultState(), 2);
    }
}
