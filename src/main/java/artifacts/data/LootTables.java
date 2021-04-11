package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModLootTables;
import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider {

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();

    private final ExistingFileHelper existingFileHelper;

    public LootTables(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        tables.clear();
        addArtifactsLootTable();
        addChestLootTables();
        addLootTable("inject/entities/cow", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .when(RandomChance.randomChance(1 / 500F))
                        .add(itemEntry(ModItems.EVERLASTING_BEEF.get(), 1)
                                .apply(
                                        Smelt.smelted().when(
                                                EntityHasProperty.hasProperties(
                                                        LootContext.EntityTarget.THIS,
                                                        EntityPredicate.Builder.entity().flags(
                                                                EntityFlagsPredicate.Builder.flags()
                                                                        .setOnFire(true)
                                                                        .build()
                                                        )
                                                )
                                        )
                                )
                        )
                ),
                LootParameterSets.ENTITY
        );

        return tables;
    }

    private void addArtifactsLootTable() {
        addChestLootTable("artifact", LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .name("main")
                                .setRolls(ConstantRange.exactly(1))
                                .add(itemEntry(ModItems.SNORKEL.get(), 8))
                                .add(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 8))
                                .add(itemEntry(ModItems.PANIC_NECKLACE.get(), 8))
                                .add(itemEntry(ModItems.SHOCK_PENDANT.get(), 8))
                                .add(itemEntry(ModItems.FLAME_PENDANT.get(), 8))
                                .add(itemEntry(ModItems.THORN_PENDANT.get(), 8))
                                .add(itemEntry(ModItems.FLIPPERS.get(), 8))
                                .add(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 8))
                                .add(itemEntry(ModItems.FIRE_GAUNTLET.get(), 8))
                                .add(itemEntry(ModItems.FERAL_CLAWS.get(), 8))
                                .add(itemEntry(ModItems.POCKET_PISTON.get(), 8))
                                .add(itemEntry(ModItems.POWER_GLOVE.get(), 8))
                                .add(itemEntry(ModItems.CROSS_NECKLACE.get(), 8))
                                .add(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 8))
                                .add(itemEntry(ModItems.LUCKY_SCARF.get(), 8))
                                .add(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 8))
                                .add(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 8))
                                .add(itemEntry(ModItems.DIGGING_CLAWS.get(), 8))
                                .add(itemEntry(ModItems.STEADFAST_SPIKES.get(), 8))
                                .add(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 8))
                                .add(itemEntry(ModItems.KITTY_SLIPPERS.get(), 8))
                                .add(itemEntry(ModItems.RUNNING_SHOES.get(), 8))
                                .add(itemEntry(ModItems.BUNNY_HOPPERS.get(), 8))
                                .add(itemEntry(ModItems.CRYSTAL_HEART.get(), 8))
                                .add(itemEntry(ModItems.VILLAGER_HAT.get(), 8))
                                .add(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 8))
                                .add(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 8))
                                .add(itemEntry(ModItems.GOLDEN_HOOK.get(), 8))
                                .add(itemEntry(ModItems.HELIUM_FLAMINGO.get(), 5))
                                .add(itemEntry(ModItems.UMBRELLA.get(), 5))
                                .add(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 5))
                                .add(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 5))
                                .add(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 3))
                                .add(itemEntry(ModItems.EVERLASTING_BEEF.get(), 2))
                )
        );
    }

    private void addChestLootTables() {
        for (String biome : Arrays.asList("desert", "plains", "savanna", "snowy", "taiga")) {
            addChestLootTable(
                    String.format("inject/chests/village/village_%s_house", biome),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool()
                                    .name("main")
                                    .setRolls(ConstantRange.exactly(1))
                                    .when(RandomChance.randomChance(0.02F))
                                    .add(itemEntry(ModItems.VILLAGER_HAT.get(), 1))
                    )
            );
        }
        addChestLootTable("inject/chests/spawn_bonus_chest", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .add(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_armorer", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.1F))
                        .add(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .add(itemEntry(ModItems.RUNNING_SHOES.get(), 1))
                        .add(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_butcher", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.01F))
                        .add(itemEntry(ModItems.EVERLASTING_BEEF.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_tannery", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.2F))
                        .add(itemEntry(ModItems.UMBRELLA.get(), 3))
                        .add(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 2))
                        .add(itemEntry(ModItems.KITTY_SLIPPERS.get(), 1))
                        .add(itemEntry(ModItems.BUNNY_HOPPERS.get(), 1))
                        .add(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_temple", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.16F))
                        .add(itemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .add(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_toolsmith", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.1F))
                        .add(itemEntry(ModItems.DIGGING_CLAWS.get(), 1))
                        .add(itemEntry(ModItems.POCKET_PISTON.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_weaponsmith", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.06F))
                        .add(itemEntry(ModItems.FERAL_CLAWS.get(), 1))
                )
        );
        addChestLootTable("inject/chests/abandoned_mineshaft", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.35F))
                        .add(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 2))
                        .add(itemEntry(ModItems.PANIC_NECKLACE.get(), 2))
                        .add(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 2))
                        .add(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 2))
                        .add(itemEntry(ModItems.DIGGING_CLAWS.get(), 2))
                        .add(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 2))
                        .add(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 2))
                        .add(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 2))
                        .add(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1)))
        );
        addChestLootTable("inject/chests/bastion_hoglin_stable", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.25F))
                        .add(artifactEntry(5))
                        .add(itemEntry(ModItems.BUNNY_HOPPERS.get(), 3))
                        .add(itemEntry(ModItems.FLAME_PENDANT.get(), 3))
                        .add(itemEntry(ModItems.EVERLASTING_BEEF.get(), 1))
                )
        );
        addChestLootTable("inject/chests/bastion_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.75F))
                        .add(artifactEntry(6))
                        .add(itemEntry(ModItems.GOLDEN_HOOK.get(), 3))
                        .add(itemEntry(ModItems.CROSS_NECKLACE.get(), 3))
                        .add(itemEntry(ModItems.FIRE_GAUNTLET.get(), 2))
                        .add(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(itemEntry(ModItems.PANIC_NECKLACE.get(), 1))
                        .add(itemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .add(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                        .add(itemEntry(ModItems.HELIUM_FLAMINGO.get(), 1))
                )
        );
        addChestLootTable("inject/chests/buried_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.35F))
                        .add(itemEntry(ModItems.SNORKEL.get(), 5))
                        .add(itemEntry(ModItems.FLIPPERS.get(), 5))
                        .add(itemEntry(ModItems.UMBRELLA.get(), 5))
                        .add(itemEntry(ModItems.GOLDEN_HOOK.get(), 5))
                        .add(itemEntry(ModItems.FERAL_CLAWS.get(), 3))
                        .add(itemEntry(ModItems.DIGGING_CLAWS.get(), 3))
                        .add(itemEntry(ModItems.KITTY_SLIPPERS.get(), 3))
                        .add(itemEntry(ModItems.BUNNY_HOPPERS.get(), 3))
                        .add(itemEntry(ModItems.LUCKY_SCARF.get(), 3))
                        .add(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 2))
                        .add(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1))
                )
        );
        addChestLootTable("inject/chests/desert_pyramid", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.4F))
                        .add(itemEntry(ModItems.FLAME_PENDANT.get(), 2))
                        .add(itemEntry(ModItems.THORN_PENDANT.get(), 2))
                        .add(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 2))
                        .add(itemEntry(ModItems.SHOCK_PENDANT.get(), 1))
                        .add(itemEntry(ModItems.UMBRELLA.get(), 1))
                        .add(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .add(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .add(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 1))
                )
        );
        addChestLootTable("inject/chests/end_city_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.4F))
                        .add(artifactEntry(6))
                        .add(itemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .add(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                        .add(itemEntry(ModItems.HELIUM_FLAMINGO.get(), 3))
                )
        );
        addChestLootTable("inject/chests/jungle_temple", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.35F))
                        .add(itemEntry(ModItems.KITTY_SLIPPERS.get(), 2))
                        .add(itemEntry(ModItems.BUNNY_HOPPERS.get(), 1))
                )
        );
        addChestLootTable("inject/chests/nether_bridge", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.20F))
                        .add(itemEntry(ModItems.CROSS_NECKLACE.get(), 3))
                        .add(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 3))
                        .add(itemEntry(ModItems.POCKET_PISTON.get(), 3))
                        .add(itemEntry(ModItems.RUNNING_SHOES.get(), 3))
                        .add(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 2))
                        .add(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1))
                )
        );
        addChestLootTable("inject/chests/pillager_outpost", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.4F))
                        .add(itemEntry(ModItems.PANIC_NECKLACE.get(), 1))
                        .add(itemEntry(ModItems.POCKET_PISTON.get(), 1))
                        .add(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(itemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .add(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .add(itemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .add(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                        .add(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                )
        );
        addChestLootTable("inject/chests/ruined_portal", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.25F))
                        .add(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 1))
                        .add(itemEntry(ModItems.THORN_PENDANT.get(), 1))
                        .add(itemEntry(ModItems.FIRE_GAUNTLET.get(), 1))
                        .add(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .add(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 1))
                        .add(itemEntry(ModItems.LUCKY_SCARF.get(), 1))
                )
        );
        addChestLootTable("inject/chests/shipwreck_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.25F))
                        .add(itemEntry(ModItems.GOLDEN_HOOK.get(), 3))
                        .add(itemEntry(ModItems.SNORKEL.get(), 1))
                        .add(itemEntry(ModItems.FLIPPERS.get(), 1))
                        .add(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .add(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .add(itemEntry(ModItems.FERAL_CLAWS.get(), 1))
                        .add(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 1))
                        .add(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 1))
                        .add(itemEntry(ModItems.RUNNING_SHOES.get(), 1))
                )
        );
        addChestLootTable("inject/chests/stronghold_corridor", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.35F))
                        .add(artifactEntry(5))
                        .add(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                        .add(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .add(itemEntry(ModItems.LUCKY_SCARF.get(), 1))
                        .add(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .add(itemEntry(ModItems.HELIUM_FLAMINGO.get(), 1))
                )
        );
        addChestLootTable("inject/chests/underwater_ruin_big", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.65F))
                        .add(itemEntry(ModItems.SNORKEL.get(), 3))
                        .add(itemEntry(ModItems.FLIPPERS.get(), 3))
                        .add(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .add(itemEntry(ModItems.LUCKY_SCARF.get(), 1))
                        .add(itemEntry(ModItems.FIRE_GAUNTLET.get(), 1))
                        .add(itemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .add(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                )
        );
        addChestLootTable("inject/chests/woodland_mansion", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(RandomChance.randomChance(0.25F))
                        .add(artifactEntry(1))
                )
        );
    }

    private static StandaloneLootEntry.Builder<?> itemEntry(Item item, int weight) {
        return ItemLootEntry.lootTableItem(item).setWeight(weight);
    }

    private static LootEntry.Builder<?> artifactEntry(int weight) {
        return TableLootEntry.lootTableReference(new ResourceLocation(Artifacts.MODID, "artifact")).setWeight(weight);
    }

    private void addLootTable(String location, LootTable.Builder lootTable, LootParameterSet lootParameterSet) {
        if (location.startsWith("inject/")) {
            String actualLocation = location.replace("inject/", "");
            Preconditions.checkArgument(existingFileHelper.exists(new ResourceLocation("loot_tables/" + actualLocation + ".json"), ResourcePackType.SERVER_DATA), "Loot table %s does not exist in any known data pack", actualLocation);
            Preconditions.checkArgument(ModLootTables.LootTableEvents.LOOT_TABLE_LOCATIONS.contains(actualLocation), "Loot table %s does not exist in list of injected loot tables", actualLocation);
        }
        tables.add(Pair.of(() -> lootBuilder -> lootBuilder.accept(new ResourceLocation(Artifacts.MODID, location), lootTable), lootParameterSet));
    }

    private void addChestLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootParameterSets.ALL_PARAMS);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validate(validationtracker, loc, table));
    }
}
