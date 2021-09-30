package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModLootTables;
import artifacts.common.loot.ConfigurableRandomChance;
import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.KilledByPlayer;
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
        addDrinkingHatsLootTable();
        addArtifactsLootTable();
        addChestLootTables();
        addLootTable("inject/entities/cow", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .when(ConfigurableRandomChance.configurableRandomChance(1 / 500F))
                        .when(KilledByPlayer.killedByPlayer())
                        .add(createItemEntry(ModItems.EVERLASTING_BEEF.get(), 1)
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
        addLootTable("artifact", LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .name("main")
                                .setRolls(ConstantRange.exactly(1))
                                .add(createItemEntry(ModItems.SNORKEL.get(), 8))
                                .add(createItemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 8))
                                .add(createItemEntry(ModItems.PANIC_NECKLACE.get(), 8))
                                .add(createItemEntry(ModItems.SHOCK_PENDANT.get(), 8))
                                .add(createItemEntry(ModItems.FLAME_PENDANT.get(), 8))
                                .add(createItemEntry(ModItems.THORN_PENDANT.get(), 8))
                                .add(createItemEntry(ModItems.FLIPPERS.get(), 8))
                                .add(createItemEntry(ModItems.OBSIDIAN_SKULL.get(), 8))
                                .add(createItemEntry(ModItems.FIRE_GAUNTLET.get(), 8))
                                .add(createItemEntry(ModItems.FERAL_CLAWS.get(), 8))
                                .add(createItemEntry(ModItems.POCKET_PISTON.get(), 8))
                                .add(createItemEntry(ModItems.POWER_GLOVE.get(), 8))
                                .add(createItemEntry(ModItems.CROSS_NECKLACE.get(), 8))
                                .add(createItemEntry(ModItems.ANTIDOTE_VESSEL.get(), 8))
                                .add(createItemEntry(ModItems.LUCKY_SCARF.get(), 8))
                                .add(createItemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 8))
                                .add(createItemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 8))
                                .add(createItemEntry(ModItems.DIGGING_CLAWS.get(), 8))
                                .add(createItemEntry(ModItems.STEADFAST_SPIKES.get(), 8))
                                .add(createItemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 8))
                                .add(createItemEntry(ModItems.KITTY_SLIPPERS.get(), 8))
                                .add(createItemEntry(ModItems.RUNNING_SHOES.get(), 8))
                                .add(createItemEntry(ModItems.BUNNY_HOPPERS.get(), 8))
                                .add(createItemEntry(ModItems.CRYSTAL_HEART.get(), 8))
                                .add(createItemEntry(ModItems.VILLAGER_HAT.get(), 8))
                                .add(createItemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 8))
                                .add(createItemEntry(ModItems.VAMPIRIC_GLOVE.get(), 8))
                                .add(createItemEntry(ModItems.GOLDEN_HOOK.get(), 8))
                                .add(createItemEntry(ModItems.CHARM_OF_SINKING.get(), 8))
                                .add(createItemEntry(ModItems.AQUA_DASHERS.get(), 8))
                                .add(createDrinkingHatEntry(8))
                                .add(createItemEntry(ModItems.UMBRELLA.get(), 5))
                                .add(createItemEntry(ModItems.WHOOPEE_CUSHION.get(), 5))
                                .add(createItemEntry(ModItems.HELIUM_FLAMINGO.get(), 4))
                                .add(createItemEntry(ModItems.EVERLASTING_BEEF.get(), 2))
                )
        );
    }

    private void addDrinkingHatsLootTable() {
        addLootTable("items/drinking_hat", LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .name("main")
                                .setRolls(ConstantRange.exactly(1))
                                .add(createItemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 3))
                                .add(createItemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1))
                )
        );
    }

    private void addChestLootTables() {
        for (String biome : Arrays.asList("desert", "plains", "savanna", "snowy", "taiga")) {
            addLootTable(
                    String.format("inject/chests/village/village_%s_house", biome),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool()
                                    .name("main")
                                    .setRolls(ConstantRange.exactly(1))
                                    .when(ConfigurableRandomChance.configurableRandomChance(0.02F))
                                    .add(createItemEntry(ModItems.VILLAGER_HAT.get(), 1))
                    )
            );
        }
        addLootTable("inject/chests/spawn_bonus_chest", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .when(ConfigurableRandomChance.configurableRandomChance(1))
                        .setRolls(ConstantRange.exactly(1))
                        .add(createItemEntry(ModItems.WHOOPEE_CUSHION.get(), 1))
                )
        );
        addLootTable("inject/chests/village/village_armorer", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.1F))
                        .add(createItemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(createItemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .add(createItemEntry(ModItems.RUNNING_SHOES.get(), 1))
                        .add(createItemEntry(ModItems.VAMPIRIC_GLOVE.get(), 1))
                )
        );
        addLootTable("inject/chests/village/village_butcher", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.02F))
                        .add(createItemEntry(ModItems.EVERLASTING_BEEF.get(), 1))
                )
        );
        addLootTable("inject/chests/village/village_tannery", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.2F))
                        .add(createItemEntry(ModItems.UMBRELLA.get(), 3))
                        .add(createItemEntry(ModItems.WHOOPEE_CUSHION.get(), 2))
                        .add(createItemEntry(ModItems.KITTY_SLIPPERS.get(), 1))
                        .add(createItemEntry(ModItems.BUNNY_HOPPERS.get(), 1))
                        .add(createItemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                )
        );
        addLootTable("inject/chests/village/village_temple", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.2F))
                        .add(createItemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .add(createItemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                        .add(createItemEntry(ModItems.CHARM_OF_SINKING.get(), 1))
                )
        );
        addLootTable("inject/chests/village/village_toolsmith", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.15F))
                        .add(createItemEntry(ModItems.DIGGING_CLAWS.get(), 1))
                        .add(createItemEntry(ModItems.POCKET_PISTON.get(), 1))
                )
        );
        addLootTable("inject/chests/village/village_weaponsmith", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.1F))
                        .add(createItemEntry(ModItems.FERAL_CLAWS.get(), 1))
                )
        );
        addLootTable("inject/chests/abandoned_mineshaft", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.30F))
                        .add(createItemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 2))
                        .add(createItemEntry(ModItems.PANIC_NECKLACE.get(), 2))
                        .add(createItemEntry(ModItems.OBSIDIAN_SKULL.get(), 2))
                        .add(createItemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 2))
                        .add(createItemEntry(ModItems.DIGGING_CLAWS.get(), 2))
                        .add(createItemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 2))
                        .add(createItemEntry(ModItems.VAMPIRIC_GLOVE.get(), 2))
                        .add(createItemEntry(ModItems.AQUA_DASHERS.get(), 2))
                        .add(createDrinkingHatEntry(2))
                )
        );
        addLootTable("inject/chests/bastion_hoglin_stable", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.20F))
                        .add(createArtifactEntry(5))
                        .add(createItemEntry(ModItems.BUNNY_HOPPERS.get(), 3))
                        .add(createItemEntry(ModItems.FLAME_PENDANT.get(), 3))
                        .add(createItemEntry(ModItems.EVERLASTING_BEEF.get(), 1))
                )
        );
        addLootTable("inject/chests/bastion_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.65F))
                        .add(createArtifactEntry(6))
                        .add(createItemEntry(ModItems.GOLDEN_HOOK.get(), 3))
                        .add(createItemEntry(ModItems.CROSS_NECKLACE.get(), 3))
                        .add(createItemEntry(ModItems.FIRE_GAUNTLET.get(), 2))
                        .add(createItemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(createItemEntry(ModItems.PANIC_NECKLACE.get(), 1))
                        .add(createItemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .add(createItemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                )
        );
        addLootTable("inject/chests/buried_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.25F))
                        .add(createItemEntry(ModItems.SNORKEL.get(), 5))
                        .add(createItemEntry(ModItems.FLIPPERS.get(), 5))
                        .add(createItemEntry(ModItems.UMBRELLA.get(), 5))
                        .add(createItemEntry(ModItems.GOLDEN_HOOK.get(), 5))
                        .add(createItemEntry(ModItems.FERAL_CLAWS.get(), 3))
                        .add(createItemEntry(ModItems.DIGGING_CLAWS.get(), 3))
                        .add(createItemEntry(ModItems.KITTY_SLIPPERS.get(), 3))
                        .add(createItemEntry(ModItems.BUNNY_HOPPERS.get(), 3))
                        .add(createItemEntry(ModItems.LUCKY_SCARF.get(), 3))
                        .add(createItemEntry(ModItems.AQUA_DASHERS.get(), 3))
                        .add(createDrinkingHatEntry(3))
                )
        );
        addLootTable("inject/chests/desert_pyramid", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.2F))
                        .add(createItemEntry(ModItems.FLAME_PENDANT.get(), 2))
                        .add(createItemEntry(ModItems.THORN_PENDANT.get(), 2))
                        .add(createItemEntry(ModItems.WHOOPEE_CUSHION.get(), 2))
                        .add(createItemEntry(ModItems.CHARM_OF_SINKING.get(), 2))
                        .add(createItemEntry(ModItems.SHOCK_PENDANT.get(), 1))
                        .add(createItemEntry(ModItems.UMBRELLA.get(), 1))
                        .add(createItemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .add(createItemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .add(createItemEntry(ModItems.VAMPIRIC_GLOVE.get(), 1))
                )
        );
        addLootTable("inject/chests/end_city_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.3F))
                        .add(createArtifactEntry(3))
                        .add(createItemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .add(createItemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                        .add(createItemEntry(ModItems.HELIUM_FLAMINGO.get(), 4))
                )
        );
        addLootTable("inject/chests/jungle_temple", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.3F))
                        .add(createItemEntry(ModItems.KITTY_SLIPPERS.get(), 2))
                        .add(createItemEntry(ModItems.BUNNY_HOPPERS.get(), 1))
                )
        );
        addLootTable("inject/chests/nether_bridge", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.15F))
                        .add(createItemEntry(ModItems.CROSS_NECKLACE.get(), 3))
                        .add(createItemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 3))
                        .add(createItemEntry(ModItems.POCKET_PISTON.get(), 3))
                        .add(createItemEntry(ModItems.RUNNING_SHOES.get(), 3))
                        .add(createDrinkingHatEntry(3))
                )
        );
        addLootTable("inject/chests/pillager_outpost", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.25F))
                        .add(createItemEntry(ModItems.PANIC_NECKLACE.get(), 1))
                        .add(createItemEntry(ModItems.POCKET_PISTON.get(), 1))
                        .add(createItemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(createItemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(createItemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .add(createItemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .add(createItemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .add(createItemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                        .add(createItemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                )
        );
        addLootTable("inject/chests/ruined_portal", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.15F))
                        .add(createItemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 1))
                        .add(createItemEntry(ModItems.THORN_PENDANT.get(), 1))
                        .add(createItemEntry(ModItems.FIRE_GAUNTLET.get(), 1))
                        .add(createItemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(createItemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .add(createItemEntry(ModItems.OBSIDIAN_SKULL.get(), 1))
                        .add(createItemEntry(ModItems.LUCKY_SCARF.get(), 1))
                )
        );
        addLootTable("inject/chests/shipwreck_treasure", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.15F))
                        .add(createItemEntry(ModItems.GOLDEN_HOOK.get(), 3))
                        .add(createItemEntry(ModItems.SNORKEL.get(), 1))
                        .add(createItemEntry(ModItems.FLIPPERS.get(), 1))
                        .add(createItemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .add(createItemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .add(createItemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .add(createItemEntry(ModItems.FERAL_CLAWS.get(), 1))
                        .add(createItemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 1))
                        .add(createItemEntry(ModItems.OBSIDIAN_SKULL.get(), 1))
                        .add(createItemEntry(ModItems.RUNNING_SHOES.get(), 1))
                        .add(createItemEntry(ModItems.CHARM_OF_SINKING.get(), 1))
                )
        );
        addLootTable("inject/chests/stronghold_corridor", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.3F))
                        .add(createArtifactEntry(3))
                        .add(createItemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(createItemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                        .add(createItemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .add(createItemEntry(ModItems.LUCKY_SCARF.get(), 1))
                        .add(createItemEntry(ModItems.AQUA_DASHERS.get(), 1))
                        .add(createItemEntry(ModItems.HELIUM_FLAMINGO.get(), 1))
                )
        );
        addLootTable("inject/chests/underwater_ruin_big", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.45F))
                        .add(createItemEntry(ModItems.SNORKEL.get(), 3))
                        .add(createItemEntry(ModItems.FLIPPERS.get(), 3))
                        .add(createItemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .add(createItemEntry(ModItems.LUCKY_SCARF.get(), 1))
                        .add(createItemEntry(ModItems.FIRE_GAUNTLET.get(), 1))
                        .add(createItemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .add(createItemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .add(createItemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                )
        );
        addLootTable("inject/chests/woodland_mansion", LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .name("main")
                        .setRolls(ConstantRange.exactly(1))
                        .when(ConfigurableRandomChance.configurableRandomChance(0.25F))
                        .add(createArtifactEntry(1))
                )
        );
    }

    private static StandaloneLootEntry.Builder<?> createItemEntry(Item item, int weight) {
        return ItemLootEntry.lootTableItem(item).setWeight(weight);
    }

    private static LootEntry.Builder<?> createArtifactEntry(int weight) {
        return createLootTableEntry("artifact", weight);
    }

    private static LootEntry.Builder<?> createDrinkingHatEntry(int weight) {
        return createLootTableEntry("items/drinking_hat", weight);
    }

    private static LootEntry.Builder<?> createLootTableEntry(String lootTable, int weight) {
        return TableLootEntry.lootTableReference(new ResourceLocation(Artifacts.MODID, lootTable)).setWeight(weight);
    }

    private void addLootTable(String location, LootTable.Builder lootTable, LootParameterSet lootParameterSet) {
        if (location.startsWith("inject/")) {
            String actualLocation = location.replace("inject/", "");
            Preconditions.checkArgument(existingFileHelper.exists(new ResourceLocation("loot_tables/" + actualLocation + ".json"), ResourcePackType.SERVER_DATA), "Loot table %s does not exist in any known data pack", actualLocation);
            Preconditions.checkArgument(ModLootTables.LootTableEvents.LOOT_TABLE_LOCATIONS.contains(actualLocation), "Loot table %s does not exist in list of injected loot tables", actualLocation);
        }
        tables.add(Pair.of(() -> lootBuilder -> lootBuilder.accept(new ResourceLocation(Artifacts.MODID, location), lootTable), lootParameterSet));
    }

    private void addLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootParameterSets.ALL_PARAMS);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validate(validationtracker, loc, table));
    }
}
