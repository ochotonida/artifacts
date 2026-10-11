package artifacts.world;

import artifacts.Artifacts;
import artifacts.registry.ModLootTables;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record CampsiteFeature(
        CampsiteChestConfiguration chestConfig,
        Holder<BlockStateProvider> litCampfires,
        Holder<BlockStateProvider> unlitCampfires,
        Holder<BlockStateProvider> decorations,
        Holder<BlockStateProvider> craftingStations,
        Holder<BlockStateProvider> furnaces,
        Holder<BlockStateProvider> furnaceChimneys,
        Holder<BlockStateProvider> beds,
        Holder<BlockStateProvider> lightSources,
        Holder<BlockStateProvider> unlitLightSources,
        Holder<BlockStateProvider> floor
) implements AbstractCampsiteFeature {

    public static final MapCodec<CampsiteFeature> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    CampsiteChestConfiguration.CODEC.fieldOf("chest").forGetter(CampsiteFeature::chestConfig),
                    BlockStateProvider.CODEC.fieldOf("lit_campfires").forGetter(CampsiteFeature::litCampfires),
                    BlockStateProvider.CODEC.fieldOf("unlit_campfires").forGetter(CampsiteFeature::unlitCampfires),
                    BlockStateProvider.CODEC.fieldOf("decorations").forGetter(CampsiteFeature::decorations),
                    BlockStateProvider.CODEC.fieldOf("crafting_stations").forGetter(CampsiteFeature::craftingStations),
                    BlockStateProvider.CODEC.fieldOf("furnaces").forGetter(CampsiteFeature::furnaces),
                    BlockStateProvider.CODEC.fieldOf("furnace_chimneys").forGetter(CampsiteFeature::furnaceChimneys),
                    BlockStateProvider.CODEC.fieldOf("beds").forGetter(CampsiteFeature::beds),
                    BlockStateProvider.CODEC.fieldOf("light_sources").forGetter(CampsiteFeature::lightSources),
                    BlockStateProvider.CODEC.fieldOf("unlit_light_sources").forGetter(CampsiteFeature::unlitLightSources),
                    BlockStateProvider.CODEC.fieldOf("floor").forGetter(CampsiteFeature::floor)
            ).apply(instance, CampsiteFeature::new)
    );

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 2, 2))
                .filter(pos -> Math.abs(pos.getX() - origin.getX()) < 2 ||  Math.abs(pos.getZ() - origin.getZ()) < 2)
                .filter(pos -> !level.getBlockState(pos).isAir())
                .forEach(pos -> setBlock(level, pos, Blocks.CAVE_AIR.defaultBlockState()));

        placeFloor(level, origin, random);
        placeCampfire(level, origin, random);

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
            BlockState bedBlock = beds().value().getState(level, random, pos).setValue(BedBlock.FACING, bedDirection);
            setBlock(level, pos, bedBlock.setValue(BedBlock.PART, BedPart.HEAD));
            setBlock(level, pos.relative(bedDirection.getOpposite()), bedBlock.setValue(BedBlock.PART, BedPart.FOOT));
            placeBarrel(level, pos.relative(bedDirection), random);
            placeLightSource(level, pos.relative(bedDirection).above(), random);
        }

        direction = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
        pos = origin.relative(direction, 2);

        List<BlockPos> positions = BlockPos.betweenClosedStream(
                pos.relative(direction.getClockWise()),
                pos.relative(direction.getCounterClockWise())
        ).map(BlockPos::immutable).collect(Collectors.toCollection(ArrayList::new));

        Collections.shuffle(positions);

        placeCraftingStation(level, positions.removeFirst(), random, direction.getOpposite());
        placeFurnace(level, positions.removeFirst(), random, direction.getOpposite());
        placeChest(level, positions.removeFirst(), random, direction.getOpposite(), chestConfig());

        return true;
    }

    private void placeFloor(WorldGenLevel level, BlockPos origin, RandomSource random) {
        BlockPos.betweenClosedStream(origin.offset(-2, -1, -2), origin.offset(2, -1, 2))
                .filter(pos -> Math.abs(pos.getX() - origin.getX()) < 2 ||  Math.abs(pos.getZ() - origin.getZ()) < 2)
                .forEach(pos -> {
                    if (!level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
                        setBlock(level, pos, floor().value().getState(level, random, pos));
                    } else if (random.nextBoolean()) {
                        if (level.getBlockState(pos).is(Blocks.DEEPSLATE)) {
                            setBlock(level, pos, Blocks.COBBLED_DEEPSLATE.defaultBlockState());
                        } else if (level.getBlockState(pos).is(Blocks.STONE)) {
                            setBlock(level, pos, Blocks.COBBLESTONE.defaultBlockState());
                        }
                    }
                });
    }

    private void placeCampfire(WorldGenLevel level, BlockPos origin, RandomSource random) {
        BlockState campfire = unlitCampfires().value().getState(level, random, origin);
        if (Artifacts.CONFIG.general.campsite.allowLightSources.get() && random.nextFloat() < 0.10) {
            campfire = litCampfires().value().getState(level, random, origin);
        }
        setBlock(level, origin, campfire);
    }

    private void placeLightSource(WorldGenLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.5) {
            BlockState lightSource = unlitLightSources().value().getState(level, random, pos);
            if (Artifacts.CONFIG.general.campsite.allowLightSources.get() && random.nextFloat() < 0.30) {
                lightSource = lightSources().value().getState(level, random, pos);
            }
            setBlock(level, pos, lightSource);
        }
    }

    private void placeCraftingStation(WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
        BlockState craftingStation = craftingStations().value().getState(level, random, pos);
        if (craftingStation.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            craftingStation = craftingStation.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }
        setBlock(level, pos, craftingStation);
        if (random.nextInt(3) == 0) {
            setBlock(level, pos.above(), decorations().value().getState(level, random, pos));
        }
    }

    private void placeFurnace(WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
        BlockState furnace = furnaces().value().getState(level, random, pos);
        furnace = furnace.setValue(FurnaceBlock.FACING, facing);
        setBlock(level, pos, furnace);
        if (random.nextBoolean()) {
            setBlock(level, pos.above(), furnaceChimneys().value().getState(level, random, pos));
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
        RandomizableContainer.setBlockEntityLootTable(level, random, pos, ModLootTables.CAMPSITE_BARREL);
    }
}
