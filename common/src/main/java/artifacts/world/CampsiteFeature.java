package artifacts.world;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CampsiteFeature extends Feature<CampsiteFeatureConfiguration> {

    public static final ResourceLocation CHEST_LOOT = Artifacts.id("chests/campsite_chest");
    public static final ResourceLocation BARREL_LOOT = Artifacts.id("chests/campsite_barrel");

    public CampsiteFeature() {
        super(CampsiteFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<CampsiteFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        CampsiteFeatureConfiguration config = context.config();

        if (!isSufficientlyFlat(level, origin)) {
            return false;
        }

        BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 2, 2))
                .filter(pos -> Math.abs(pos.getX() - origin.getX()) < 2 ||  Math.abs(pos.getZ() - origin.getZ()) < 2)
                .filter(pos -> !level.getBlockState(pos).isAir())
                .forEach(pos -> setBlock(level, pos, Blocks.CAVE_AIR.defaultBlockState()));

        placeFloor(config, level, origin, random);
        placeCampfire(config, level, origin, random);

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos pos = origin.relative(direction, 2);

        if (random.nextInt(3) == 0) {
            BlockPos.betweenClosedStream(
                    pos.relative(direction.getClockWise()),
                    pos.relative(direction.getCounterClockWise())
            ).forEach(barrelPos -> {
                placeBarrel(level, barrelPos, random);
                if (random.nextInt(3) == 0) {
                    placeBarrel(level, barrelPos.above(), random);
                }
            });
        } else {
            Direction bedDirection = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
            BlockState bedBlock = config.beds().getState(random, pos).setValue(BedBlock.FACING, bedDirection);
            setBlock(level, pos, bedBlock.setValue(BedBlock.PART, BedPart.HEAD));
            setBlock(level, pos.relative(bedDirection.getOpposite()), bedBlock.setValue(BedBlock.PART, BedPart.FOOT));
            placeBarrel(level, pos.relative(bedDirection), random);
            placeLightSource(config, level, pos.relative(bedDirection).above(), random);
        }

        direction = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
        pos = origin.relative(direction, 2);

        List<BlockPos> positions = BlockPos.betweenClosedStream(
                pos.relative(direction.getClockWise()),
                pos.relative(direction.getCounterClockWise())
        ).map(BlockPos::immutable).collect(Collectors.toCollection(ArrayList::new));

        Collections.shuffle(positions);

        placeCraftingStation(config, level, positions.remove(0), random, direction.getOpposite());
        placeFurnace(config, level, positions.remove(0), random, direction.getOpposite());
        placeChest(level, positions.remove(0), random, direction.getOpposite());

        return true;
    }

    private boolean isSufficientlyFlat(WorldGenLevel level, BlockPos origin) {
        return BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 0, 2))
                .filter(pos -> level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP))
                .filter(pos -> level.getBlockState(pos).isAir())
                .count() >= 6;
    }

    private void placeFloor(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos origin, RandomSource random) {
        BlockPos.betweenClosedStream(origin.offset(-2, -1, -2), origin.offset(2, -1, 2))
                .filter(pos -> Math.abs(pos.getX() - origin.getX()) < 2 ||  Math.abs(pos.getZ() - origin.getZ()) < 2)
                .forEach(pos -> {
                    if (!level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
                        setBlock(level, pos, config.floor().getState(random, pos));
                    } else if (random.nextBoolean()) {
                        if (level.getBlockState(pos).is(Blocks.DEEPSLATE)) {
                            setBlock(level, pos, Blocks.COBBLED_DEEPSLATE.defaultBlockState());
                        } else if (level.getBlockState(pos).is(Blocks.STONE)) {
                            setBlock(level, pos, Blocks.COBBLESTONE.defaultBlockState());
                        }
                    }
                });
    }

    private void placeCampfire(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos origin, RandomSource random) {
        BlockState campfire = config.unlitCampfires().getState(random, origin);
        if (Artifacts.CONFIG.common.campsite.allowLightSources && random.nextFloat() < 0.10) {
            campfire = config.litCampfires().getState(random, origin);
        }
        setBlock(level, origin, campfire);
    }

    private void placeLightSource(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.5) {
            BlockState lightSource = config.unlitLightSources().getState(random, pos);
            if (Artifacts.CONFIG.common.campsite.allowLightSources && random.nextFloat() < 0.30) {
                lightSource = config.lightSources().getState(random, pos);
            }
            setBlock(level, pos, lightSource);
        }
    }

    private void placeCraftingStation(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
        BlockState craftingStation = config.craftingStations().getState(random, pos);
        if (craftingStation.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            craftingStation = craftingStation.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }
        setBlock(level, pos, craftingStation);
        if (random.nextInt(3) == 0) {
            setBlock(level, pos.above(), config.decorations().getState(random, pos));
        }
    }

    private void placeFurnace(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
        BlockState furnace = config.furnaces().getState(random, pos);
        furnace = furnace.setValue(FurnaceBlock.FACING, facing);
        setBlock(level, pos, furnace);
        if (random.nextBoolean()) {
            setBlock(level, pos.above(), config.furnaceChimneys().getState(random, pos));
        }
    }

    private void placeBarrel(WorldGenLevel level, BlockPos pos, RandomSource random) {
        BlockState barrel = Blocks.BARREL.defaultBlockState();
        if (random.nextBoolean()) {
            barrel = barrel.setValue(BarrelBlock.FACING, Direction.UP);
        } else {
            barrel = barrel.setValue(BarrelBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
        }
        setBlock(level, pos, barrel);
        RandomizableContainerBlockEntity.setLootTable(level, random, pos, BARREL_LOOT);
    }

    public void placeChest(WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
        if (random.nextFloat() < Artifacts.CONFIG.common.campsite.getMimicChance()) {
            MimicEntity mimic = ModEntityTypes.MIMIC.get().create(level.getLevel());
            if (mimic != null) {
                mimic.setDormant(true);
                mimic.setFacing(facing);
                mimic.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                level.addFreshEntity(mimic);
            }
        } else {
            BlockState chest;
            if (random.nextInt(8) == 0) {
                setBlock(level, pos.below(), Blocks.TNT.defaultBlockState());
                chest = Blocks.TRAPPED_CHEST.defaultBlockState();
                setBlock(level, pos, Blocks.TRAPPED_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
            } else if (Artifacts.CONFIG.common.campsite.useModdedChests) {
                chest = ModTags.getTag(ModTags.CAMPSITE_CHESTS)
                        .getRandomElement(random)
                        .map(Holder::value)
                        .orElse(Blocks.CHEST)
                        .defaultBlockState();
            } else {
                chest = Blocks.CHEST.defaultBlockState();
            }

            if (chest.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                chest = chest.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
            }
            setBlock(level, pos, chest);

            RandomizableContainerBlockEntity.setLootTable(level, random, pos, CHEST_LOOT);
        }
    }
}
