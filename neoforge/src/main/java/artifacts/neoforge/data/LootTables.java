package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.config.value.Value;
import artifacts.loot.ArtifactRarityAdjustedChance;
import artifacts.loot.ConfigValueCondition;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModItems;
import artifacts.registry.RegistryHolder;
import artifacts.world.CampsiteFeature;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class LootTables extends LootTableProvider {

    public static final ResourceKey<LootTable> CHEST_LOOT = Artifacts.key(Registries.LOOT_TABLE, "chests/campsite_chest");

    private final List<SubProviderEntry> tables = new ArrayList<>();

    private final LootModifiers lootModifiers;

    public LootTables(PackOutput packOutput, LootModifiers lootModifiers, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Set.of(), List.of(), lookupProvider);
        this.lootModifiers = lootModifiers;
    }

    @Override
    public List<SubProviderEntry> getTables() {
        tables.clear();
        addDrinkingHatsLootTable();
        addItemLootTables();
        addArtifactsLootTable();
        addChestLootTables();
        new EntityEquipment(this).addLootTables();

        for (LootModifiers.Builder lootBuilder : lootModifiers.lootBuilders) {
            addLootTable("inject/" + lootBuilder.getName(), _ -> lootBuilder.createLootTable(), lootBuilder.getContextKeySet());
        }

        addLootTable(
                ModEntityTypes.MIMIC.get().getDefaultLootTable().orElseThrow().identifier().getPath(),
                new LootTable.Builder().withPool(new LootPool.Builder().add(artifact(1)))
        );

        return tables;
    }

    private void addArtifactsLootTable() {
        List<Item> items = new ArrayList<>();
        BuiltInRegistries.ITEM.stream()
                .filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Artifacts.MOD_ID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.value())
                .sorted(Comparator.comparing(item -> BuiltInRegistries.ITEM.getKey(item).getPath()))
                .forEach(items::add);

        items.removeAll(List.of(
                ModItems.MIMIC_SPAWN_EGG.value(),
                ModItems.NOVELTY_DRINKING_HAT.value(),
                ModItems.PLASTIC_DRINKING_HAT.value(),
                ModItems.WHOOPEE_CUSHION.value(),
                ModItems.HELIUM_FLAMINGO.value(),
                ModItems.ETERNAL_STEAK.value(),
                ModItems.EVERLASTING_BEEF.value(),
                ModItems.UMBRELLA.value()
        ));

        LootPool.Builder builder = LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1));
        items.forEach(item -> builder.add(itemWhenEnabled(item, 8)));
        builder.add(drinkingHatWhenEnabled(8))
                .add(itemWhenEnabled(ModItems.UMBRELLA.value(), 5))
                .add(itemWhenEnabled(ModItems.WHOOPEE_CUSHION.value(), 5))
                .add(itemWhenEnabled(ModItems.HELIUM_FLAMINGO.value(), 4))
                .add(itemWhenEnabled(ModItems.EVERLASTING_BEEF.value(), 2));

        addLootTable("artifact", LootTable.lootTable().withPool(builder));
    }

    private void addDrinkingHatsLootTable() {
        addLootTable("items/drinking_hat", LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .name("main")
                                .setRolls(ConstantValue.exactly(1))
                                .add(AlternativesEntry.alternatives(
                                        LootItem.lootTableItem(ModItems.PLASTIC_DRINKING_HAT.value()).setWeight(3).when(ConfigValueCondition.canGenerateAsLoot(ModItems.PLASTIC_DRINKING_HAT.value())),
                                        EmptyLootItem.emptyItem().setWeight(3)
                                ))
                                .add(AlternativesEntry.alternatives(
                                        LootItem.lootTableItem(ModItems.NOVELTY_DRINKING_HAT.value()).setWeight(1).when(ConfigValueCondition.canGenerateAsLoot(ModItems.NOVELTY_DRINKING_HAT.value())),
                                        EmptyLootItem.emptyItem().setWeight(1)
                                ))
                )
        );
    }

    private void addItemLootTables() {
        for (RegistryHolder<Item, ?> item : ModItems.ITEMS.getEntries()) {
            if (!List.of(
                    ModItems.MIMIC_SPAWN_EGG.value(),
                    ModItems.ETERNAL_STEAK.value(),
                    ModItems.PLASTIC_DRINKING_HAT.value(),
                    ModItems.NOVELTY_DRINKING_HAT.value()
            ).contains(item.value())) {
                addLootTable("items/%s".formatted(item.getKey().identifier().getPath()), LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .name("main")
                                        .setRolls(ConstantValue.exactly(1))
                                        .add(AlternativesEntry.alternatives(
                                                LootItem.lootTableItem(item.value()).setWeight(1).when(ConfigValueCondition.canGenerateAsLoot(item.value())),
                                                EmptyLootItem.emptyItem().setWeight(1)
                                        ))
                        )
                );
            }
        }
    }

    private void addChestLootTables() {
        String barrel = CampsiteFeature.BARREL_LOOT.identifier().getPath();
        addLootTable(barrel, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .when(LootItemRandomChanceCondition.randomChance(0.7F))
                        .add(item(Items.COD, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.SALMON, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.ROTTEN_FLESH, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.BONE, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 10))))
                        .add(item(Items.PAPER, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.SUGAR_CANE, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.WHEAT, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.BOOK, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.SUGAR, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(item(Items.COAL, 1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 16))))
                        .add(lootTable(barrel + "/tnt", 1))
                        .add(lootTable(barrel + "/cobwebs", 1))
                        .add(lootTable(barrel + "/ores", 1))
                        .add(lootTable(barrel + "/ingots", 1))
                        .add(lootTable(barrel + "/gems", 1))
                        .add(lootTable(barrel + "/crops", 1))
                        .add(lootTable(barrel + "/food", 1))
                        .add(lootTable(barrel + "/cobblestone", 1))
                        .add(lootTable(barrel + "/rails", 1))
                        .add(lootTable(barrel + "/minecarts", 1))
                )
        );

        addLootTable(barrel + "/tnt", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("tnt")
                        .setRolls(ConstantValue.exactly(3))
                        .add(item(Items.TNT, 1))
                        .add(item(Items.GUNPOWDER, 4, 1, 5))
                ).withPool(new LootPool.Builder()
                        .name("sand")
                        .add(item(Items.SAND, 2, 8, 40))
                        .add(item(Items.RED_SAND, 1, 8, 40))
                )
        );

        addLootTable(barrel + "/cobwebs", new LootTable.Builder()
                .withPool(new LootPool.Builder().add(item(Items.COBWEB, 1, 3, 8)))
                .withPool(new LootPool.Builder().add(item(Items.STRING, 1, 6, 16)))
        );

        addLootTable(barrel + "/ores", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("ores")
                        .setRolls(ConstantValue.exactly(2))
                        .add(item(Items.RAW_GOLD, 1, 4, 20))
                        .add(item(Items.RAW_IRON, 1, 4, 20))
                        .add(item(Items.RAW_COPPER, 1, 4, 20))
                ).withPool(new LootPool.Builder()
                        .name("blocks")
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(Items.RAW_GOLD_BLOCK, 1, 2, 8))
                        .add(item(Items.RAW_IRON_BLOCK, 1, 2, 8))
                        .add(item(Items.RAW_COPPER_BLOCK, 1, 2, 8))
                )
        );

        addLootTable(barrel + "/ingots", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("ingots")
                        .setRolls(ConstantValue.exactly(2))
                        .add(item(Items.GOLD_INGOT, 1, 4, 16))
                        .add(item(Items.IRON_INGOT, 1, 4, 16))
                        .add(item(Items.COPPER_INGOT, 1, 4, 16))
                ).withPool(new LootPool.Builder()
                        .name("blocks")
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(Items.GOLD_BLOCK, 1, 1, 6))
                        .add(item(Items.IRON_BLOCK, 1, 1, 6))
                        .add(item(Items.COPPER_BLOCK.weathering().unaffected(), 1, 4, 16))
                )
        );

        addLootTable(barrel + "/gems", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(3))
                        .add(item(Items.AMETHYST_SHARD, 3, 1, 8))
                        .add(item(Items.DIAMOND, 1, 1, 4))
                        .add(item(Items.EMERALD, 1, 1, 4))
                )
        );

        addLootTable(barrel + "/crops", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(2))
                        .add(item(Items.POTATO, 1, 2, 12))
                        .add(item(Items.CARROT, 1, 2, 12))
                        .add(item(Items.BEETROOT, 1, 2, 12))
                        .add(item(Items.WHEAT, 1, 2, 12))
                        .add(item(Items.MELON_SLICE, 1, 2, 12))
                        .add(item(Items.PUMPKIN, 1, 2, 12))
                        .add(item(Items.APPLE, 1, 2, 12))
                )
        );

        addLootTable(barrel + "/food", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(3))
                        .add(item(Items.BREAD, 1, 2, 12))
                        .add(item(Items.PUMPKIN_PIE, 1, 2, 12))
                        .add(item(Items.CAKE, 1))
                        .add(item(Items.COOKIE, 1, 2, 12))
                        .add(item(Items.MUSHROOM_STEW, 1, 1, 4))
                        .add(item(Items.RABBIT_STEW, 1, 1, 4))
                )
        );

        addLootTable(barrel + "/cobblestone", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(3))
                        .add(item(Items.COBBLESTONE, 1, 16, 64))
                        .add(item(Items.COBBLED_DEEPSLATE, 1, 16, 64))
                )
        );

        addLootTable(barrel + "/rails", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(4))
                        .add(item(Items.RAIL, 4, 4, 16))
                        .add(item(Items.ACTIVATOR_RAIL, 1, 2, 5))
                        .add(item(Items.DETECTOR_RAIL, 1, 2, 5))
                        .add(item(Items.POWERED_RAIL, 2, 4, 16))
                )
        );

        addLootTable(barrel + "/minecarts", new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("minecarts")
                        .setRolls(ConstantValue.exactly(5))
                        .add(item(Items.MINECART, 4))
                        .add(item(Items.CHEST_MINECART, 1))
                        .add(item(Items.FURNACE_MINECART, 1))
                        .add(item(Items.HOPPER_MINECART, 1))
                )
                .withPool(new LootPool.Builder()
                        .name("rails")
                        .add(item(Items.RAIL, 1, 4, 16))
                )
        );

        addLootTable(CHEST_LOOT.identifier().getPath(), provider -> new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("tools")
                        .setRolls(UniformGenerator.between(1, 3))
                        .add(item(Items.DIAMOND_PICKAXE, 2))
                        .add(item(Items.DIAMOND_AXE, 1))
                        .add(item(Items.DIAMOND_SHOVEL, 1))
                        .add(item(Items.GOLDEN_PICKAXE, 4))
                        .add(item(Items.GOLDEN_AXE, 2))
                        .add(item(Items.GOLDEN_SHOVEL, 2))
                        .add(item(Items.IRON_PICKAXE, 6))
                        .add(item(Items.IRON_AXE, 3))
                        .add(item(Items.IRON_SHOVEL, 3))
                        .add(item(Items.IRON_HELMET, 2))
                        .add(item(Items.IRON_CHESTPLATE, 2))
                        .add(item(Items.IRON_LEGGINGS, 2))
                        .add(item(Items.IRON_BOOTS, 2))
                        .add(item(Items.CHAINMAIL_HELMET, 1))
                        .add(item(Items.CHAINMAIL_CHESTPLATE, 1))
                        .add(item(Items.CHAINMAIL_LEGGINGS, 1))
                        .add(item(Items.CHAINMAIL_BOOTS, 1))
                ).withPool(new LootPool.Builder()
                        .name("junk")
                        .setRolls(UniformGenerator.between(1, 4))
                        .add(item(Items.GUNPOWDER, 5, 2, 8))
                        .add(item(Items.ROTTEN_FLESH, 5, 2, 8))
                        .add(item(Items.SPIDER_EYE, 5, 2, 8))
                        .add(item(Items.STRING, 5, 2, 8))
                        .add(item(Items.PAPER, 5, 2, 8))
                        .add(item(Items.BONE, 5, 2, 8))
                        .add(item(Items.STICK, 3, 2, 8))
                        .add(item(Items.GLASS_BOTTLE, 3, 2, 8))
                        .add(item(Items.LEATHER, 3, 2, 8))
                        .add(item(Items.FLINT, 3, 2, 8))
                        .add(item(Items.FEATHER, 3, 2, 8))
                ).withPool(new LootPool.Builder()
                        .name("ores")
                        .setRolls(UniformGenerator.between(1, 4))
                        .add(item(Items.RAW_COPPER, 3, 2, 8))
                        .add(item(Items.RAW_IRON, 3, 2, 8))
                        .add(item(Items.RAW_GOLD, 3, 2, 8))
                        .add(item(Items.COAL, 6, 4, 8))
                        .add(item(Items.DIAMOND, 1, 1, 4))
                ).withPool(new LootPool.Builder()
                        .name("treasure")
                        .when(LootItemRandomChanceCondition.randomChance(0.3F))
                        .add(item(Items.BOOK, 8).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(15, 30))))
                        .add(item(Items.GOLDEN_APPLE, 4))
                        .add(item(Items.ENCHANTED_GOLDEN_APPLE, 1))
                ).withPool(new LootPool.Builder()
                        .name("artifact")
                        .when(ArtifactRarityAdjustedChance.adjustedChance(0.15F))
                        .add(artifact(1))
                )
        );
    }

    protected static LootPoolEntryContainer.Builder<?> itemWhenEnabled(Item item, int weight) {
        return item(item, weight).when(ConfigValueCondition.canGenerateAsLoot(item));
    }

    protected static LootPoolSingletonContainer.Builder<?> item(Item item, int weight) {
        Value<Boolean> generatesAsLoot = Artifacts.CONFIG.items.generatesAsLoot(item);
        if (generatesAsLoot == null) {
            return LootItem.lootTableItem(item).setWeight(weight);
        } else if (item == ModItems.PLASTIC_DRINKING_HAT.value() || item == ModItems.NOVELTY_DRINKING_HAT.value()) {
            throw new IllegalArgumentException();
        } else {
            String itemName = BuiltInRegistries.ITEM.getKey(item).getPath();
            return NestedLootTable.lootTableReference(Artifacts.key(Registries.LOOT_TABLE, "items/%s".formatted(itemName))).setWeight(weight);
        }
    }

    protected static LootPoolSingletonContainer.Builder<?> item(Item item, int weight, int min, int max) {
        return LootItem.lootTableItem(item).setWeight(weight).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }

    protected static LootPoolEntryContainer.Builder<?> artifact(int weight) {
        return lootTable("artifact", weight);
    }

    protected static LootPoolEntryContainer.Builder<?> drinkingHat(int weight) {
        return lootTable("items/drinking_hat", weight);
    }

    protected static LootPoolEntryContainer.Builder<?> drinkingHatWhenEnabled(int weight) {
        return lootTable("items/drinking_hat", weight)
                .when(ConfigValueCondition.canGenerateAsLoot(ModItems.PLASTIC_DRINKING_HAT.value())
                        .or(ConfigValueCondition.canGenerateAsLoot(ModItems.NOVELTY_DRINKING_HAT.value())));
    }

    private static LootPoolEntryContainer.Builder<?> lootTable(String lootTable, int weight) {
        return NestedLootTable.lootTableReference(Artifacts.key(Registries.LOOT_TABLE, lootTable)).setWeight(weight);
    }

    public void addLootTable(String location, Function<HolderLookup.Provider, LootTable.Builder> lootTable, ContextKeySet contextKeySet) {
        tables.add(new SubProviderEntry(provider -> biConsumer -> biConsumer.accept(Artifacts.key(Registries.LOOT_TABLE, location), lootTable.apply(provider)), contextKeySet));
    }

    private void addLootTable(String location, Function<HolderLookup.Provider, LootTable.Builder> lootTable) {
        addLootTable(location, lootTable, LootContextParamSets.ALL_PARAMS);
    }

    private void addLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, _ -> lootTable, LootContextParamSets.ALL_PARAMS);
    }
}
