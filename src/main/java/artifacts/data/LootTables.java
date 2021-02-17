package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider {

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();

    public LootTables(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        tables.clear();
        addArtifactsLootTable();
        addChestLootTables();
        addLootTable("inject/entities/cow", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .addEntry(itemEntry(ModItems.EVERLASTING_BEEF.get(), 1)
                                .acceptFunction(
                                        Smelt.func_215953_b().acceptCondition(
                                                EntityHasProperty.builder(
                                                        LootContext.EntityTarget.THIS,
                                                        EntityPredicate.Builder.create().flags(
                                                                EntityFlagsPredicate.Builder.create()
                                                                        .onFire(true)
                                                                        .build()
                                                        )
                                                )
                                        )
                                )
                        )
                        .addEntry(emptyEntry(999))
                ),
                LootParameterSets.ENTITY
        );

        return tables;
    }

    private void addArtifactsLootTable() {
        addChestLootTable("artifact", LootTable.builder()
                .addLootPool(
                        LootPool.builder()
                                .name("main")
                                .rolls(ConstantRange.of(1))
                                .addEntry(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 8))
                                .addEntry(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 2))
                                .addEntry(itemEntry(ModItems.SNORKEL.get(), 8))
                                .addEntry(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 8))
                                .addEntry(itemEntry(ModItems.PANIC_NECKLACE.get(), 8))
                                .addEntry(itemEntry(ModItems.SHOCK_PENDANT.get(), 8))
                                .addEntry(itemEntry(ModItems.FLAME_PENDANT.get(), 8))
                                .addEntry(itemEntry(ModItems.THORN_PENDANT.get(), 8))
                                .addEntry(itemEntry(ModItems.FLIPPERS.get(), 8))
                                .addEntry(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 8))
                                .addEntry(itemEntry(ModItems.UMBRELLA.get(), 8))
                                .addEntry(itemEntry(ModItems.EVERLASTING_BEEF.get(), 1))
                                .addEntry(itemEntry(ModItems.FIRE_GAUNTLET.get(), 8))
                                .addEntry(itemEntry(ModItems.FERAL_CLAWS.get(), 8))
                                .addEntry(itemEntry(ModItems.POCKET_PISTON.get(), 8))
                                .addEntry(itemEntry(ModItems.POWER_GLOVE.get(), 8))
                                .addEntry(itemEntry(ModItems.CROSS_NECKLACE.get(), 8))
                                .addEntry(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 8))
                                .addEntry(itemEntry(ModItems.LUCKY_SCARF.get(), 8))
                                .addEntry(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 8))
                                .addEntry(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 8))
                                .addEntry(itemEntry(ModItems.DIGGING_CLAWS.get(), 8))
                                .addEntry(itemEntry(ModItems.STEADFAST_SPIKES.get(), 8))
                                .addEntry(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 8))
                                .addEntry(itemEntry(ModItems.KITTY_SLIPPERS.get(), 8))
                                .addEntry(itemEntry(ModItems.RUNNING_SHOES.get(), 8))
                                .addEntry(itemEntry(ModItems.BUNNY_HOPPERS.get(), 8))
                                .addEntry(itemEntry(ModItems.CRYSTAL_HEART.get(), 6))
                                .addEntry(itemEntry(ModItems.VILLAGER_HAT.get(), 8))
                                .addEntry(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 8))
                                .addEntry(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 10))
                ));
    }

    private void addChestLootTables() {
        addChestLootTable("inject/chests/village/village_armorer", ChestLootTableBuilder.builder()
                .add(ModItems.STEADFAST_SPIKES.get(), 5)
                .add(ModItems.SUPERSTITIOUS_HAT.get(), 2)
                .add(ModItems.RUNNING_SHOES.get(), 3)
                .build()
        );
        addChestLootTable("inject/chests/village/village_butcher", ChestLootTableBuilder.builder()
                .add(ModItems.EVERLASTING_BEEF.get(), 1)
                .build()
        );
        addChestLootTable("inject/chests/village/village_desert_house", ChestLootTableBuilder.builder()
                .add(ModItems.VILLAGER_HAT.get(), 2)
                .build()
        );
        addChestLootTable("inject/chests/village/village_plains_house", ChestLootTableBuilder.builder()
                .add(ModItems.VILLAGER_HAT.get(), 2)
                .build()
        );
        addChestLootTable("inject/chests/village/village_savanna_house", ChestLootTableBuilder.builder()
                .add(ModItems.VILLAGER_HAT.get(), 2)
                .build()
        );
        addChestLootTable("inject/chests/village/village_tannery", ChestLootTableBuilder.builder()
                .add(ModItems.UMBRELLA.get(), 9)
                .add(ModItems.KITTY_SLIPPERS.get(), 2)
                .add(ModItems.BUNNY_HOPPERS.get(), 3)
                .add(ModItems.SCARF_OF_INVISIBILITY.get(), 2)
                .add(ModItems.WHOOPEE_CUSHION.get(), 4)
                .build()
        );
        addChestLootTable("inject/chests/village/village_temple", ChestLootTableBuilder.builder()
                .add(ModItems.CROSS_NECKLACE.get(), 8)
                .add(ModItems.ANTIDOTE_VESSEL.get(), 8)
                .build()
        );
        addChestLootTable("inject/chests/village/village_toolsmith", ChestLootTableBuilder.builder()
                .add(ModItems.DIGGING_CLAWS.get(), 4)
                .add(ModItems.POCKET_PISTON.get(), 6)
                .build()
        );
        addChestLootTable("inject/chests/village/village_weaponsmith", ChestLootTableBuilder.builder()
                .add(ModItems.FERAL_CLAWS.get(), 6)
                .build()
        );
        addChestLootTable("inject/chests/abandoned_mineshaft", ChestLootTableBuilder.builder()
                .add(ModItems.NIGHT_VISION_GOGGLES.get(), 2)
                .add(ModItems.PANIC_NECKLACE.get(), 2)
                .add(ModItems.OBSIDIAN_SKULL.get(), 2)
                .add(ModItems.PLASTIC_DRINKING_HAT.get(), 3)
                .add(ModItems.NOVELTY_DRINKING_HAT.get(), 1)
                .add(ModItems.SUPERSTITIOUS_HAT.get(), 2)
                .add(ModItems.DIGGING_CLAWS.get(), 2)
                .add(ModItems.CLOUD_IN_A_BOTTLE.get(), 2)
                .build()
        );
        addChestLootTable("inject/chests/bastion_hoglin_stable", ChestLootTableBuilder.builder()
                .add(ModItems.EVERLASTING_BEEF.get(), 3)
                .add(ModItems.BUNNY_HOPPERS.get(), 7)
                .add(ModItems.FLAME_PENDANT.get(), 8)
                .addArtifact(7)
                .build()
        );
        addChestLootTable("inject/chests/bastion_treasure", ChestLootTableBuilder.builder()
                .add(ModItems.FIRE_GAUNTLET.get(), 8)
                .add(ModItems.CROSS_NECKLACE.get(), 7)
                .add(ModItems.STEADFAST_SPIKES.get(), 5)
                .add(ModItems.PANIC_NECKLACE.get(), 8)
                .add(ModItems.CRYSTAL_HEART.get(), 10)
                .add(ModItems.ANTIDOTE_VESSEL.get(), 7)
                .addArtifact(5)
                .build()
        );
        addChestLootTable("inject/chests/buried_treasure", ChestLootTableBuilder.builder()
                .add(ModItems.SNORKEL.get(), 4)
                .add(ModItems.FLIPPERS.get(), 3)
                .add(ModItems.FERAL_CLAWS.get(), 2)
                .add(ModItems.DIGGING_CLAWS.get(), 2)
                .add(ModItems.PLASTIC_DRINKING_HAT.get(), 3)
                .add(ModItems.NOVELTY_DRINKING_HAT.get(), 1)
                .add(ModItems.UMBRELLA.get(), 3)
                .add(ModItems.KITTY_SLIPPERS.get(), 2)
                .add(ModItems.BUNNY_HOPPERS.get(), 3)
                .add(ModItems.LUCKY_SCARF.get(), 2)
                .build()
        );
        addChestLootTable("inject/chests/desert_pyramid", ChestLootTableBuilder.builder()
                .add(ModItems.FLAME_PENDANT.get(), 8)
                .add(ModItems.THORN_PENDANT.get(), 8)
                .add(ModItems.SHOCK_PENDANT.get(), 5)
                .add(ModItems.UMBRELLA.get(), 5)
                .add(ModItems.SCARF_OF_INVISIBILITY.get(), 2)
                .add(ModItems.UNIVERSAL_ATTRACTOR.get(), 5)
                .add(ModItems.WHOOPEE_CUSHION.get(), 7)
                .build()
        );
        addChestLootTable("inject/chests/end_city_treasure", ChestLootTableBuilder.builder()
                .addArtifact(40)
                .add(ModItems.CRYSTAL_HEART.get(), 10)
                .build()
        );
        addChestLootTable("inject/chests/jungle_temple", ChestLootTableBuilder.builder()
                .add(ModItems.KITTY_SLIPPERS.get(), 15)
                .add(ModItems.BUNNY_HOPPERS.get(), 5)
                .build()
        );
        addChestLootTable("inject/chests/nether_bridge", ChestLootTableBuilder.builder()
                .add(ModItems.PLASTIC_DRINKING_HAT.get(), 3)
                .add(ModItems.NOVELTY_DRINKING_HAT.get(), 1)
                .add(ModItems.CROSS_NECKLACE.get(), 3)
                .add(ModItems.NIGHT_VISION_GOGGLES.get(), 2)
                .add(ModItems.POCKET_PISTON.get(), 3)
                .add(ModItems.RUNNING_SHOES.get(), 3)
                .build()
        );
        addChestLootTable("inject/chests/pillager_outpost", ChestLootTableBuilder.builder()
                .add(ModItems.PANIC_NECKLACE.get(), 5)
                .add(ModItems.POCKET_PISTON.get(), 5)
                .add(ModItems.STEADFAST_SPIKES.get(), 5)
                .add(ModItems.POWER_GLOVE.get(), 5)
                .add(ModItems.CROSS_NECKLACE.get(), 5)
                .add(ModItems.SCARF_OF_INVISIBILITY.get(), 4)
                .add(ModItems.CRYSTAL_HEART.get(), 4)
                .add(ModItems.CLOUD_IN_A_BOTTLE.get(), 8)
                .build()
        );
        addChestLootTable("inject/chests/ruined_portal", ChestLootTableBuilder.builder()
                .add(ModItems.NIGHT_VISION_GOGGLES.get(), 2)
                .add(ModItems.THORN_PENDANT.get(), 4)
                .add(ModItems.FIRE_GAUNTLET.get(), 3)
                .add(ModItems.POWER_GLOVE.get(), 3)
                .add(ModItems.UNIVERSAL_ATTRACTOR.get(), 3)
                .add(ModItems.OBSIDIAN_SKULL.get(), 3)
                .add(ModItems.LUCKY_SCARF.get(), 2)
                .build()
        );
        addChestLootTable("inject/chests/shipwreck_treasure", ChestLootTableBuilder.builder()
                .add(ModItems.SNORKEL.get(), 4)
                .add(ModItems.FLIPPERS.get(), 4)
                .add(ModItems.SCARF_OF_INVISIBILITY.get(), 2)
                .add(ModItems.STEADFAST_SPIKES.get(), 3)
                .add(ModItems.UNIVERSAL_ATTRACTOR.get(), 2)
                .add(ModItems.FERAL_CLAWS.get(), 3)
                .add(ModItems.NIGHT_VISION_GOGGLES.get(), 2)
                .add(ModItems.OBSIDIAN_SKULL.get(), 3)
                .add(ModItems.RUNNING_SHOES.get(), 2)
                .build()
        );
        addChestLootTable("inject/chests/spawn_bonus_chest", LootTable.builder()
                .addLootPool(LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .addEntry(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 1))
                )
        );
        addChestLootTable("inject/chests/stronghold_corridor", ChestLootTableBuilder.builder()
                .add(ModItems.POWER_GLOVE.get(), 4)
                .add(ModItems.ANTIDOTE_VESSEL.get(), 4)
                .add(ModItems.SUPERSTITIOUS_HAT.get(), 7)
                .add(ModItems.LUCKY_SCARF.get(), 6)
                .add(ModItems.UNIVERSAL_ATTRACTOR.get(), 4)
                .addArtifact(10)
                .build()
        );
        addChestLootTable("inject/chests/underwater_ruin_big", ChestLootTableBuilder.builder()
                .add(ModItems.SNORKEL.get(), 7)
                .add(ModItems.FLIPPERS.get(), 7)
                .add(ModItems.SUPERSTITIOUS_HAT.get(), 3)
                .add(ModItems.LUCKY_SCARF.get(), 3)
                .add(ModItems.FIRE_GAUNTLET.get(), 5)
                .add(ModItems.CROSS_NECKLACE.get(), 5)
                .add(ModItems.POWER_GLOVE.get(), 5)
                .add(ModItems.CLOUD_IN_A_BOTTLE.get(), 5)
                .build()
        );
        addChestLootTable("inject/chests/woodland_mansion", ChestLootTableBuilder.builder()
                .addArtifact(25)
                .build()
        );
    }

    private static StandaloneLootEntry.Builder<?> itemEntry(Item item, int weight) {
        return ItemLootEntry.builder(item).weight(weight);
    }

    private static LootEntry.Builder<?> emptyEntry(int weight) {
        return EmptyLootEntry.func_216167_a().weight(weight);
    }

    private static LootEntry.Builder<?> artifactEntry(int weight) {
        return TableLootEntry.builder(new ResourceLocation(Artifacts.MODID, "artifact")).weight(weight);
    }

    private void addLootTable(String location, LootTable.Builder lootTable, LootParameterSet lootParameterSet) {
        tables.add(Pair.of(() -> lootBuilder -> lootBuilder.accept(new ResourceLocation(Artifacts.MODID, location), lootTable), lootParameterSet));
    }

    private void addChestLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootParameterSets.GENERIC);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validateLootTable(validationtracker, loc, table));
    }

    private static class ChestLootTableBuilder {

        private final LootPool.Builder builder = LootPool.builder().name("main").rolls(ConstantRange.of(1));
        private int totalWeight;

        private static ChestLootTableBuilder builder() {
            return new ChestLootTableBuilder();
        }

        private ChestLootTableBuilder add(Item item, int weight) {
            totalWeight += weight;
            builder.addEntry(itemEntry(item, weight));
            return this;
        }

        private ChestLootTableBuilder addArtifact(int weight) {
            totalWeight += weight;
            builder.addEntry(artifactEntry(weight));
            return this;
        }

        private LootTable.Builder build() {
            builder.addEntry(emptyEntry(100 - totalWeight));
            return LootTable.builder().addLootPool(builder);
        }
    }
}
