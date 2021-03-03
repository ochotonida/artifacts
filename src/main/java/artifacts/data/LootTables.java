package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
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
        addLootTable("inject/entities/cow", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .acceptCondition(RandomChance.builder(0.0025F))
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
                                .addEntry(itemEntry(ModItems.SNORKEL.get(), 8))
                                .addEntry(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 8))
                                .addEntry(itemEntry(ModItems.PANIC_NECKLACE.get(), 8))
                                .addEntry(itemEntry(ModItems.SHOCK_PENDANT.get(), 8))
                                .addEntry(itemEntry(ModItems.FLAME_PENDANT.get(), 8))
                                .addEntry(itemEntry(ModItems.THORN_PENDANT.get(), 8))
                                .addEntry(itemEntry(ModItems.FLIPPERS.get(), 8))
                                .addEntry(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 8))
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
                                .addEntry(itemEntry(ModItems.CRYSTAL_HEART.get(), 8))
                                .addEntry(itemEntry(ModItems.VILLAGER_HAT.get(), 8))
                                .addEntry(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 8))
                                .addEntry(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 8))
                                .addEntry(itemEntry(ModItems.GOLDEN_HOOK.get(), 8))
                                .addEntry(itemEntry(ModItems.UMBRELLA.get(), 5))
                                .addEntry(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 5))
                                .addEntry(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 5))
                                .addEntry(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 3))
                                .addEntry(itemEntry(ModItems.EVERLASTING_BEEF.get(), 2))
                )
        );
    }

    private void addChestLootTables() {
        for (String biome : Arrays.asList("desert", "plains", "savanna", "snowy", "taiga")) {
            addChestLootTable(
                    String.format("inject/chests/village/village_%s_house", biome),
                    LootTable.builder().addLootPool(
                            LootPool.builder()
                                    .name("main")
                                    .rolls(ConstantRange.of(1))
                                    .acceptCondition(RandomChance.builder(0.02F))
                                    .addEntry(itemEntry(ModItems.VILLAGER_HAT.get(), 1))
                    )
            );
        }
        addChestLootTable("inject/chests/spawn_bonus_chest", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .addEntry(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_armorer", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.1F))
                        .addEntry(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .addEntry(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .addEntry(itemEntry(ModItems.RUNNING_SHOES.get(), 1))
                        .addEntry(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_butcher", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.01F))
                        .addEntry(itemEntry(ModItems.EVERLASTING_BEEF.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_tannery", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.2F))
                        .addEntry(itemEntry(ModItems.UMBRELLA.get(), 3))
                        .addEntry(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 2))
                        .addEntry(itemEntry(ModItems.KITTY_SLIPPERS.get(), 1))
                        .addEntry(itemEntry(ModItems.BUNNY_HOPPERS.get(), 1))
                        .addEntry(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_temple", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.16F))
                        .addEntry(itemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .addEntry(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_toolsmith", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.1F))
                        .addEntry(itemEntry(ModItems.DIGGING_CLAWS.get(), 1))
                        .addEntry(itemEntry(ModItems.POCKET_PISTON.get(), 1))
                )
        );
        addChestLootTable("inject/chests/village/village_weaponsmith", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.06F))
                        .addEntry(itemEntry(ModItems.FERAL_CLAWS.get(), 1))
                )
        );
        addChestLootTable("inject/chests/abandoned_mineshaft", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 2))
                        .addEntry(itemEntry(ModItems.PANIC_NECKLACE.get(), 2))
                        .addEntry(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 2))
                        .addEntry(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 2))
                        .addEntry(itemEntry(ModItems.DIGGING_CLAWS.get(), 2))
                        .addEntry(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 2))
                        .addEntry(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 2))
                        .addEntry(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 2))
                        .addEntry(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1)))
        );
        addChestLootTable("inject/chests/bastion_hoglin_stable", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(artifactEntry(5))
                        .addEntry(itemEntry(ModItems.BUNNY_HOPPERS.get(), 3))
                        .addEntry(itemEntry(ModItems.FLAME_PENDANT.get(), 3))
                        .addEntry(itemEntry(ModItems.EVERLASTING_BEEF.get(), 1))
                )
        );
        addChestLootTable("inject/chests/bastion_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.75F))
                        .addEntry(artifactEntry(6))
                        .addEntry(itemEntry(ModItems.GOLDEN_HOOK.get(), 3))
                        .addEntry(itemEntry(ModItems.CROSS_NECKLACE.get(), 3))
                        .addEntry(itemEntry(ModItems.FIRE_GAUNTLET.get(), 2))
                        .addEntry(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .addEntry(itemEntry(ModItems.PANIC_NECKLACE.get(), 1))
                        .addEntry(itemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .addEntry(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                )
        );
        addChestLootTable("inject/chests/buried_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(itemEntry(ModItems.SNORKEL.get(), 5))
                        .addEntry(itemEntry(ModItems.FLIPPERS.get(), 5))
                        .addEntry(itemEntry(ModItems.UMBRELLA.get(), 5))
                        .addEntry(itemEntry(ModItems.GOLDEN_HOOK.get(), 5))
                        .addEntry(itemEntry(ModItems.FERAL_CLAWS.get(), 3))
                        .addEntry(itemEntry(ModItems.DIGGING_CLAWS.get(), 3))
                        .addEntry(itemEntry(ModItems.KITTY_SLIPPERS.get(), 3))
                        .addEntry(itemEntry(ModItems.BUNNY_HOPPERS.get(), 3))
                        .addEntry(itemEntry(ModItems.LUCKY_SCARF.get(), 3))
                        .addEntry(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 2))
                        .addEntry(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1))
                )
        );
        addChestLootTable("inject/chests/desert_pyramid", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.4F))
                        .addEntry(itemEntry(ModItems.FLAME_PENDANT.get(), 2))
                        .addEntry(itemEntry(ModItems.THORN_PENDANT.get(), 2))
                        .addEntry(itemEntry(ModItems.WHOOPEE_CUSHION.get(), 2))
                        .addEntry(itemEntry(ModItems.SHOCK_PENDANT.get(), 1))
                        .addEntry(itemEntry(ModItems.UMBRELLA.get(), 1))
                        .addEntry(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .addEntry(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .addEntry(itemEntry(ModItems.VAMPIRIC_GLOVE.get(), 1))
                )
        );
        addChestLootTable("inject/chests/end_city_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.4F))
                        .addEntry(artifactEntry(6))
                        .addEntry(itemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .addEntry(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                )
        );
        addChestLootTable("inject/chests/jungle_temple", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(itemEntry(ModItems.KITTY_SLIPPERS.get(), 2))
                        .addEntry(itemEntry(ModItems.BUNNY_HOPPERS.get(), 1))
                )
        );
        addChestLootTable("inject/chests/nether_bridge", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.20F))
                        .addEntry(itemEntry(ModItems.CROSS_NECKLACE.get(), 3))
                        .addEntry(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 3))
                        .addEntry(itemEntry(ModItems.POCKET_PISTON.get(), 3))
                        .addEntry(itemEntry(ModItems.RUNNING_SHOES.get(), 3))
                        .addEntry(itemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 2))
                        .addEntry(itemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1))
                )
        );
        addChestLootTable("inject/chests/pillager_outpost", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.4F))
                        .addEntry(itemEntry(ModItems.PANIC_NECKLACE.get(), 1))
                        .addEntry(itemEntry(ModItems.POCKET_PISTON.get(), 1))
                        .addEntry(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .addEntry(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .addEntry(itemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .addEntry(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .addEntry(itemEntry(ModItems.CRYSTAL_HEART.get(), 1))
                        .addEntry(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                        .addEntry(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                )
        );
        addChestLootTable("inject/chests/ruined_portal", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 1))
                        .addEntry(itemEntry(ModItems.THORN_PENDANT.get(), 1))
                        .addEntry(itemEntry(ModItems.FIRE_GAUNTLET.get(), 1))
                        .addEntry(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .addEntry(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .addEntry(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 1))
                        .addEntry(itemEntry(ModItems.LUCKY_SCARF.get(), 1))
                )
        );
        addChestLootTable("inject/chests/shipwreck_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(itemEntry(ModItems.GOLDEN_HOOK.get(), 3))
                        .addEntry(itemEntry(ModItems.SNORKEL.get(), 1))
                        .addEntry(itemEntry(ModItems.FLIPPERS.get(), 1))
                        .addEntry(itemEntry(ModItems.SCARF_OF_INVISIBILITY.get(), 1))
                        .addEntry(itemEntry(ModItems.STEADFAST_SPIKES.get(), 1))
                        .addEntry(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                        .addEntry(itemEntry(ModItems.FERAL_CLAWS.get(), 1))
                        .addEntry(itemEntry(ModItems.NIGHT_VISION_GOGGLES.get(), 1))
                        .addEntry(itemEntry(ModItems.OBSIDIAN_SKULL.get(), 1))
                        .addEntry(itemEntry(ModItems.RUNNING_SHOES.get(), 1))
                )
        );
        addChestLootTable("inject/chests/stronghold_corridor", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(artifactEntry(5))
                        .addEntry(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .addEntry(itemEntry(ModItems.ANTIDOTE_VESSEL.get(), 1))
                        .addEntry(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .addEntry(itemEntry(ModItems.LUCKY_SCARF.get(), 1))
                        .addEntry(itemEntry(ModItems.UNIVERSAL_ATTRACTOR.get(), 1))
                )
        );
        addChestLootTable("inject/chests/underwater_ruin_big", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.65F))
                        .addEntry(itemEntry(ModItems.SNORKEL.get(), 3))
                        .addEntry(itemEntry(ModItems.FLIPPERS.get(), 3))
                        .addEntry(itemEntry(ModItems.SUPERSTITIOUS_HAT.get(), 1))
                        .addEntry(itemEntry(ModItems.LUCKY_SCARF.get(), 1))
                        .addEntry(itemEntry(ModItems.FIRE_GAUNTLET.get(), 1))
                        .addEntry(itemEntry(ModItems.CROSS_NECKLACE.get(), 1))
                        .addEntry(itemEntry(ModItems.POWER_GLOVE.get(), 1))
                        .addEntry(itemEntry(ModItems.CLOUD_IN_A_BOTTLE.get(), 1))
                )
        );
        addChestLootTable("inject/chests/woodland_mansion", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(artifactEntry(1))
                )
        );
    }

    private static StandaloneLootEntry.Builder<?> itemEntry(Item item, int weight) {
        return ItemLootEntry.builder(item).weight(weight);
    }

    private static LootEntry.Builder<?> artifactEntry(int weight) {
        return TableLootEntry.builder(new ResourceLocation(Artifacts.MODID, "artifact")).weight(weight);
    }

    private void addLootTable(String location, LootTable.Builder lootTable, LootParameterSet lootParameterSet) {
        if (location.startsWith("inject")) {
            ResourceLocation lootTableLocation = new ResourceLocation(location.replaceFirst("inject", "loot_tables") + ".json");
            Preconditions.checkArgument(existingFileHelper.exists(lootTableLocation, ResourcePackType.SERVER_DATA), "Loot table %s does not exist in any known data pack", lootTableLocation);
        }
        tables.add(Pair.of(() -> lootBuilder -> lootBuilder.accept(new ResourceLocation(Artifacts.MODID, location), lootTable), lootParameterSet));
    }

    private void addChestLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootParameterSets.GENERIC);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validateLootTable(validationtracker, loc, table));
    }
}
