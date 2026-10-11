package artifacts.neoforge.data.loot;

import artifacts.loot.ArtifactRarityAdjustedChance;
import artifacts.registry.ModLootTables;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class ArtifactsChestLoot implements LootTableSubProvider {

    private final Context output;
    private final HolderGetter<LootTable> lootTables;
    private final HolderGetter<Enchantment> enchantments;

    protected ArtifactsChestLoot(Context output) {
        this.output = output;
        this.lootTables = output.lookup(Registries.LOOT_TABLE);
        this.enchantments = output.lookup(Registries.ENCHANTMENT);
    }

    @Override
    public void run() {
        output.accept(ModLootTables.CAMPSITE_BARREL, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .when(LootItemRandomChanceCondition.randomChance(0.7F))
                        .add(LootItem.lootTableItem(Items.COD).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.SALMON).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.BONE).apply(setCountBetween(2, 10)))
                        .add(LootItem.lootTableItem(Items.PAPER).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.SUGAR_CANE).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.WHEAT).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.BOOK).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.SUGAR).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.COAL).apply(setCountBetween(4, 16)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_TNT)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_COBWEBS)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_ORES)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_INGOTS)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_GEMS)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_CROPS)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_FOOD)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_COBBLESTONE)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_RAILS)))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.CAMPSITE_BARREL_MINECARTS)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_TNT, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("tnt")
                        .setRolls(ContextIntProviders.exactly(3))
                        .add(LootItem.lootTableItem(Items.TNT).setWeight(1))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(4).apply(setCountBetween(1, 5)))
                ).withPool(new LootPool.Builder()
                        .name("sand")
                        .add(LootItem.lootTableItem(Items.SAND).setWeight(2).apply(setCountBetween(8, 40)))
                        .add(LootItem.lootTableItem(Items.RED_SAND).setWeight(1).apply(setCountBetween(8, 40)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_COBWEBS, new LootTable.Builder()
                .withPool(new LootPool.Builder().add(LootItem.lootTableItem(Items.COBWEB).apply(setCountBetween(3, 8))))
                .withPool(new LootPool.Builder().add(LootItem.lootTableItem(Items.STRING).apply(setCountBetween(6, 16))))
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_ORES, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("ores")
                        .setRolls(ContextIntProviders.exactly(2))
                        .add(LootItem.lootTableItem(Items.RAW_GOLD).apply(setCountBetween(4, 20)))
                        .add(LootItem.lootTableItem(Items.RAW_IRON).apply(setCountBetween(4, 20)))
                        .add(LootItem.lootTableItem(Items.RAW_COPPER).apply(setCountBetween(4, 20)))
                ).withPool(new LootPool.Builder()
                        .name("blocks")
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(Items.RAW_GOLD_BLOCK).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.RAW_IRON_BLOCK).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.RAW_COPPER_BLOCK).apply(setCountBetween(2, 8)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_INGOTS, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("ingots")
                        .setRolls(ContextIntProviders.exactly(2))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).apply(setCountBetween(4, 16)))
                ).withPool(new LootPool.Builder()
                        .name("blocks")
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(Items.GOLD_BLOCK).apply(setCountBetween(1, 6)))
                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).apply(setCountBetween(1, 6)))
                        .add(LootItem.lootTableItem(Items.COPPER_BLOCK.weathering().unaffected()).apply(setCountBetween(4, 16)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_GEMS, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ContextIntProviders.exactly(3))
                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(3).apply(setCountBetween(1, 8)))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1).apply(setCountBetween(1, 4)))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1).apply(setCountBetween(1, 4)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_CROPS, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ContextIntProviders.exactly(2))
                        .add(LootItem.lootTableItem(Items.POTATO).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.CARROT).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.BEETROOT).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.WHEAT).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.MELON_SLICE).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.PUMPKIN).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.APPLE).apply(setCountBetween(2, 12)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_FOOD, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ContextIntProviders.exactly(3))
                        .add(LootItem.lootTableItem(Items.BREAD).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.PUMPKIN_PIE).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.CAKE))
                        .add(LootItem.lootTableItem(Items.COOKIE).apply(setCountBetween(2, 12)))
                        .add(LootItem.lootTableItem(Items.MUSHROOM_STEW).apply(setCountBetween(1, 4)))
                        .add(LootItem.lootTableItem(Items.RABBIT_STEW).apply(setCountBetween(1, 4)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_COBBLESTONE, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ContextIntProviders.exactly(3))
                        .add(LootItem.lootTableItem(Items.COBBLESTONE).apply(setCountBetween(16, 64)))
                        .add(LootItem.lootTableItem(Items.COBBLED_DEEPSLATE).apply(setCountBetween(16, 64)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_RAILS, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .setRolls(ContextIntProviders.exactly(4))
                        .add(LootItem.lootTableItem(Items.RAIL).setWeight(4).apply(setCountBetween(4, 16)))
                        .add(LootItem.lootTableItem(Items.ACTIVATOR_RAIL).setWeight(1).apply(setCountBetween(2, 5)))
                        .add(LootItem.lootTableItem(Items.DETECTOR_RAIL).setWeight(1).apply(setCountBetween(2, 5)))
                        .add(LootItem.lootTableItem(Items.POWERED_RAIL).setWeight(2).apply(setCountBetween(4, 16)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_BARREL_MINECARTS, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("minecarts")
                        .setRolls(ContextIntProviders.exactly(5))
                        .add(LootItem.lootTableItem(Items.MINECART).setWeight(4))
                        .add(LootItem.lootTableItem(Items.CHEST_MINECART).setWeight(1))
                        .add(LootItem.lootTableItem(Items.FURNACE_MINECART).setWeight(1))
                        .add(LootItem.lootTableItem(Items.HOPPER_MINECART).setWeight(1))
                )
                .withPool(new LootPool.Builder()
                        .name("rails")
                        .add(LootItem.lootTableItem(Items.RAIL).setWeight(1).apply(setCountBetween(4, 16)))
                )
        );

        output.accept(ModLootTables.CAMPSITE_CHEST, new LootTable.Builder()
                .withPool(new LootPool.Builder()
                        .name("tools")
                        .setRolls(ContextIntProviders.between(1, 3))
                        .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(2))
                        .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND_SHOVEL).setWeight(1))
                        .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(4))
                        .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(2))
                        .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(2))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(6))
                        .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(2))
                        .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(2))
                        .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(2))
                        .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(2))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(1))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(1))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(1))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_BOOTS).setWeight(1))
                ).withPool(new LootPool.Builder()
                        .name("junk")
                        .setRolls(ContextIntProviders.between(1, 4))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(5).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.SPIDER_EYE).setWeight(5).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(5).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(5).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(5).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(3).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(3).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(3).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(3).apply(setCountBetween(2, 8)))
                ).withPool(new LootPool.Builder()
                        .name("ores")
                        .setRolls(ContextIntProviders.between(1, 4))
                        .add(LootItem.lootTableItem(Items.RAW_COPPER).setWeight(3).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(3).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(3).apply(setCountBetween(2, 8)))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(6).apply(setCountBetween(4, 8)))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1).apply(setCountBetween(1, 4)))
                ).withPool(new LootPool.Builder()
                        .name("treasure")
                        .when(LootItemRandomChanceCondition.randomChance(0.3F))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(8).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(15, 30))))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(4))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
                ).withPool(new LootPool.Builder()
                        .name("artifact")
                        .when(ArtifactRarityAdjustedChance.adjustedChance(0.15F))
                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.ARTIFACT)))
                )
        );
    }

    private static LootItemFunction.Builder setCountBetween(int min, int max) {
        return SetItemCountFunction.setCount(ContextIntProviders.between(min, max));
    }
}
