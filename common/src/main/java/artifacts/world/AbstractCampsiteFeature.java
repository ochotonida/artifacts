package artifacts.world;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;

public interface AbstractCampsiteFeature extends Feature {

    // TODO: add chest feature config (loot table, trapped chest probability, mimic probability)
    default void placeChest(WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing, CampsiteChestConfiguration config) {
        if (random.nextFloat() < config.getMimicChance()) {
            MimicEntity mimic = ModEntityTypes.MIMIC.get().create(level.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
            if (mimic != null) {
                mimic.setDormant(true);
                mimic.setFacing(facing);
                mimic.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                level.addFreshEntity(mimic);
            }
        } else {
            BlockState chest = Blocks.CHEST.defaultBlockState();

            if (random.nextFloat() < config.trappedChestChance()) {
                setBlock(level, pos.below(), Blocks.TNT.defaultBlockState());
                chest = Blocks.TRAPPED_CHEST.defaultBlockState();
                setBlock(level, pos, Blocks.TRAPPED_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
            } else if (Artifacts.CONFIG.general.campsite.useModdedChests.get()) {
                // TODO: find a cleaner way to do this
                var tag = BuiltInRegistries.BLOCK.getTagOrEmpty(ModTags.CAMPSITE_CHESTS);
                if (tag instanceof HolderSet<Block>) {
                    chest = ((HolderSet<Block>) tag)
                            .getRandomElement(random)
                            .map(Holder::value)
                            .orElse(Blocks.CHEST)
                            .defaultBlockState();
                }
            }

            if (chest.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                chest = chest.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
            }
            setBlock(level, pos, chest);

            config.chestLootTable().ifPresent(lootTable -> RandomizableContainer.setBlockEntityLootTable(level, random, pos, lootTable));
        }
    }
}
