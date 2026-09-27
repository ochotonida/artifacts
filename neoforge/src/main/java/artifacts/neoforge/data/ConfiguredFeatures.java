package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.registry.ModFeatures;
import artifacts.world.CampsiteChestConfiguration;
import artifacts.world.CampsiteFeatureConfiguration;
import artifacts.world.SuspiciousChestFeatureConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.Optional;

public class ConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CAMPSITE = Artifacts.key(Registries.CONFIGURED_FEATURE, "campsite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MINIMALIST_CAMPSITE = Artifacts.key(Registries.CONFIGURED_FEATURE, "minimalist_campsite");

    public static void create(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(CAMPSITE, createCampsite());
        context.register(MINIMALIST_CAMPSITE, createMinimalistCampsite());
    }

    private static ConfiguredFeature<?, ?> createCampsite() {
        return new ConfiguredFeature<>(ModFeatures.CAMPSITE.get(), new CampsiteFeatureConfiguration(
                createChestConfig(0.125),
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, true), 9)
                        .add(Blocks.SOUL_CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, true), 1)
                ), // lit campfires
                SimpleStateProvider.simple(
                        Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, false)
                ), // unlit campfires
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.POTTED_DEAD_BUSH.defaultBlockState(), 2)
                        .add(Blocks.POTTED_BAMBOO.defaultBlockState(), 2)
                        .add(Blocks.POTTED_RED_TULIP.defaultBlockState(), 2)
                        .add(Blocks.BREWING_STAND.defaultBlockState(), 1)
                        .add(Blocks.CANDLE_CAKE.defaultBlockState().setValue(CandleCakeBlock.LIT, true), 1)
                ), // decorations
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.CRAFTING_TABLE.defaultBlockState(), 5)
                        .add(Blocks.SMITHING_TABLE.defaultBlockState(), 5)
                        .add(Blocks.FLETCHING_TABLE.defaultBlockState(), 5)
                        .add(Blocks.CARTOGRAPHY_TABLE.defaultBlockState(), 5)
                        .add(Blocks.ANVIL.defaultBlockState(), 2)
                        .add(Blocks.CHIPPED_ANVIL.defaultBlockState(), 2)
                        .add(Blocks.DAMAGED_ANVIL.defaultBlockState(), 1)
                ), // crafting stations
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.FURNACE.defaultBlockState().setValue(FurnaceBlock.LIT, false), 2)
                        .add(Blocks.BLAST_FURNACE.defaultBlockState().setValue(BlastFurnaceBlock.LIT, false), 1)
                        .add(Blocks.SMOKER.defaultBlockState().setValue(SmokerBlock.LIT, false), 1)
                ), // furnaces
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.COBBLESTONE_WALL.defaultBlockState(), 2)
                        .add(Blocks.COBBLED_DEEPSLATE_WALL.defaultBlockState(), 2)
                        .add(Blocks.STONE_BRICK_WALL.defaultBlockState(), 1)
                        .add(Blocks.DEEPSLATE_BRICK_WALL.defaultBlockState(), 1)
                ), // furnace chimneys
                new WeightedStateProvider(WeightedList.<BlockState>builder().addAll(
                        Blocks.BED.map(Block::defaultBlockState)
                                .map(blockstate -> new Weighted<>(blockstate, 1))
                                .asList()
                        )
                ), // beds
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.LANTERN.defaultBlockState(), 4)
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true), 1)
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true).setValue(CandleBlock.CANDLES, 2), 1)
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true).setValue(CandleBlock.CANDLES, 3), 1)
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true).setValue(CandleBlock.CANDLES, 4), 1)
                        .add(Blocks.SOUL_LANTERN.defaultBlockState(), 1)
                ), // light sources
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false), 1)
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false).setValue(CandleBlock.CANDLES, 2), 1)
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false).setValue(CandleBlock.CANDLES, 3), 1)
                        .add(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false).setValue(CandleBlock.CANDLES, 4), 1)
                ), // unlit light sources
                SimpleStateProvider.simple(Blocks.OAK_PLANKS) // floor
        ));
    }

    private static ConfiguredFeature<?, ?> createMinimalistCampsite() {
        return new ConfiguredFeature<>(ModFeatures.SUSPICIOUS_CHEST.get(), new SuspiciousChestFeatureConfiguration(createChestConfig(0)));
    }

    private static CampsiteChestConfiguration createChestConfig(double trappedChestChance) {
        return new CampsiteChestConfiguration(Optional.empty(), trappedChestChance, Optional.of(LootTables.CHEST_LOOT));
    }
}
