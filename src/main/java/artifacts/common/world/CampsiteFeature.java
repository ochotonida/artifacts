package artifacts.common.world;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.entity.MimicEntity;
import artifacts.common.init.ModEntityTypes;
import artifacts.common.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CampsiteFeature extends Feature<NoneFeatureConfiguration> {

    private static final BlockStateProvider CAMPFIRES = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, true), 4)
            .add(Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, false), 20)
            .add(Blocks.SOUL_CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, true), 1)
    );

    private static final BlockStateProvider DECORATIONS = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.POTTED_DEAD_BUSH.defaultBlockState(), 2)
            .add(Blocks.POTTED_BAMBOO.defaultBlockState(), 2)
            .add(Blocks.POTTED_RED_TULIP.defaultBlockState(), 2)
            .add(Blocks.BREWING_STAND.defaultBlockState(), 1)
            .add(Blocks.CANDLE_CAKE.defaultBlockState().setValue(CandleCakeBlock.LIT, true), 1)
    );

    private static final BlockStateProvider CRAFTING_STATIONS = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.CRAFTING_TABLE.defaultBlockState(), 5)
            .add(Blocks.SMITHING_TABLE.defaultBlockState(), 5)
            .add(Blocks.FLETCHING_TABLE.defaultBlockState(), 5)
            .add(Blocks.CARTOGRAPHY_TABLE.defaultBlockState(), 5)
            .add(Blocks.ANVIL.defaultBlockState(), 2)
            .add(Blocks.CHIPPED_ANVIL.defaultBlockState(), 2)
            .add(Blocks.DAMAGED_ANVIL.defaultBlockState(), 1)
    );

    private static final BlockStateProvider FURNACES = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.FURNACE.defaultBlockState().setValue(FurnaceBlock.LIT, false), 2)
            .add(Blocks.BLAST_FURNACE.defaultBlockState().setValue(BlastFurnaceBlock.LIT, false), 1)
            .add(Blocks.SMOKER.defaultBlockState().setValue(SmokerBlock.LIT, false), 1)
    );

    private static final BlockStateProvider FURNACE_CHIMNEYS = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.COBBLESTONE_WALL.defaultBlockState(), 2)
            .add(Blocks.COBBLED_DEEPSLATE_WALL.defaultBlockState(), 2)
            .add(Blocks.STONE_BRICK_WALL.defaultBlockState(), 1)
            .add(Blocks.DEEPSLATE_BRICK_WALL.defaultBlockState(), 1)
    );

    private static final BlockStateProvider BEDS = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.RED_BED.defaultBlockState(), 1)
            .add(Blocks.YELLOW_BED.defaultBlockState(), 1)
            .add(Blocks.CYAN_BED.defaultBlockState(), 1)
            .add(Blocks.GRAY_BED.defaultBlockState(), 1)
            .add(Blocks.MAGENTA_BED.defaultBlockState(), 1)
            .add(Blocks.GREEN_BED.defaultBlockState(), 1)
    );

    private static final BlockStateProvider LIGHTS = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.LANTERN.defaultBlockState(), 3)
            .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true), 1)
            .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false), 1)
            .add(Blocks.SOUL_LANTERN.defaultBlockState(), 1)
    );

    public static final ResourceLocation CHEST_LOOT = new ResourceLocation(Artifacts.MODID, "chests/campsite_chest");
    public static final ResourceLocation BARREL_LOOT = new ResourceLocation(Artifacts.MODID, "chests/campsite_barrel");

    public CampsiteFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        if (!isSufficientlyFlat(level, origin)) {
            return false;
        }

        BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 2, 2))
                .filter(pos -> Math.abs(pos.getX() - origin.getX()) < 2 ||  Math.abs(pos.getZ() - origin.getZ()) < 2)
                .filter(pos -> !level.getBlockState(pos).isAir())
                .forEach(pos -> setBlock(level, pos, Blocks.CAVE_AIR.defaultBlockState()));

        placeFloor(level, origin, random);

        setBlock(level, origin, CAMPFIRES.getState(random, origin));

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
            BlockState bedBlock = BEDS.getState(random, pos).setValue(BedBlock.FACING, bedDirection);
            setBlock(level, pos, bedBlock.setValue(BedBlock.PART, BedPart.HEAD));
            setBlock(level, pos.relative(bedDirection.getOpposite()), bedBlock.setValue(BedBlock.PART, BedPart.FOOT));
            placeBarrel(level, pos.relative(bedDirection), random);
            if (random.nextBoolean()) {
                setBlock(level, pos.relative(bedDirection).above(), LIGHTS.getState(random, pos));
            }
        }

        direction = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
        pos = origin.relative(direction, 2);

        List<BlockPos> positions = BlockPos.betweenClosedStream(
                pos.relative(direction.getClockWise()),
                pos.relative(direction.getCounterClockWise())
        ).map(BlockPos::immutable).collect(Collectors.toCollection(ArrayList::new));

        Collections.shuffle(positions);

        placeCraftingStation(level, positions.remove(0), random, direction.getOpposite());
        placeFurnace(level, positions.remove(0), random, direction.getOpposite());
        placeChest(level, positions.remove(0), random, direction.getOpposite());

        return true;
    }

    private boolean isSufficientlyFlat(WorldGenLevel level, BlockPos origin) {
        return BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 0, 2))
                .filter(pos -> level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP))
                .filter(pos -> level.getBlockState(pos).isAir())
                .count() >= 6;
    }

    private void placeFloor(WorldGenLevel level, BlockPos origin, RandomSource random) {
        BlockPos.betweenClosedStream(origin.offset(-2, -1, -2), origin.offset(2, -1, 2))
                .filter(pos -> Math.abs(pos.getX() - origin.getX()) < 2 ||  Math.abs(pos.getZ() - origin.getZ()) < 2)
                .forEach(pos -> {
                    if (!level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
                        setBlock(level, pos, Blocks.OAK_PLANKS.defaultBlockState());
                    } else if (random.nextBoolean()) {
                        if (level.getBlockState(pos).is(Blocks.DEEPSLATE)) {
                            setBlock(level, pos, Blocks.COBBLED_DEEPSLATE.defaultBlockState());
                        } else if (level.getBlockState(pos).is(Blocks.STONE)) {
                            setBlock(level, pos, Blocks.COBBLESTONE.defaultBlockState());
                        }
                    }
                });
    }

    private void placeCraftingStation(WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
        BlockState craftingStation = CRAFTING_STATIONS.getState(random, pos);
        if (craftingStation.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            craftingStation = craftingStation.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }
        setBlock(level, pos, craftingStation);
        if (random.nextInt(3) == 0) {
            setBlock(level, pos.above(), DECORATIONS.getState(random, pos));
        }
    }

    private void placeFurnace(WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
        BlockState furnace = FURNACES.getState(random, pos);
        furnace = furnace.setValue(FurnaceBlock.FACING, facing);
        setBlock(level, pos, furnace);
        if (random.nextBoolean()) {
            setBlock(level, pos.above(), FURNACE_CHIMNEYS.getState(random, pos));
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
        if (random.nextFloat() < ModConfig.common.campsiteMimicChance.get()) {
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
            } else if (ModConfig.common.useModdedChests.get()) {
                chest = ModTags.getTag(Tags.Blocks.CHESTS_WOODEN)
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
