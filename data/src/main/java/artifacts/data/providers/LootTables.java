package artifacts.data.providers;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import artifacts.forge.registry.ModItems;
import artifacts.loot.ConfigurableRandomChance;
import artifacts.world.CampsiteFeature;
import com.google.common.base.Preconditions;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LootTables extends LootTableProvider {

    private final List<SubProviderEntry> tables = new ArrayList<>();

    private final ExistingFileHelper existingFileHelper;
    private final LootModifiers lootModifiers;

    public LootTables(PackOutput packOutput, ExistingFileHelper existingFileHelper, LootModifiers lootModifiers) {
        super(packOutput, Set.of(), List.of());
        this.existingFileHelper = existingFileHelper;
        this.lootModifiers = lootModifiers;
    }

    @Override
    public List<SubProviderEntry> getTables() {
        tables.clear();
        addDrinkingHatsLootTable();
        addArtifactsLootTable();
        addChestLootTables();

        for (LootModifiers.Builder lootBuilder : lootModifiers.lootBuilders) {
            addLootTable("inject/" + lootBuilder.getName(), lootBuilder.createLootTable(), lootBuilder.getParameterSet());
        }

        addLootTable(MimicEntity.LOOT_TABLE.getPath(), new LootTable.Builder().withPool(new LootPool.Builder().add(artifact(1))));


        return tables;
    }

    private void addArtifactsLootTable() {
        addLootTable("artifact", LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .name("main")
                                .setRolls(ConstantValue.exactly(1))
                                .add(item(ModItems.SNORKEL.get(), 8))
                                .add(item(ModItems.NIGHT_VISION_GOGGLES.get(), 8))
                                .add(item(ModItems.PANIC_NECKLACE.get(), 8))
                                .add(item(ModItems.SHOCK_PENDANT.get(), 8))
                                .add(item(ModItems.FLAME_PENDANT.get(), 8))
                                .add(item(ModItems.THORN_PENDANT.get(), 8))
                                .add(item(ModItems.FLIPPERS.get(), 8))
                                .add(item(ModItems.OBSIDIAN_SKULL.get(), 8))
                                .add(item(ModItems.FIRE_GAUNTLET.get(), 8))
                                .add(item(ModItems.FERAL_CLAWS.get(), 8))
                                .add(item(ModItems.POCKET_PISTON.get(), 8))
                                .add(item(ModItems.POWER_GLOVE.get(), 8))
                                .add(item(ModItems.CROSS_NECKLACE.get(), 8))
                                .add(item(ModItems.ANTIDOTE_VESSEL.get(), 8))
                                .add(item(ModItems.LUCKY_SCARF.get(), 8))
                                .add(item(ModItems.SUPERSTITIOUS_HAT.get(), 8))
                                .add(item(ModItems.SCARF_OF_INVISIBILITY.get(), 8))
                                .add(item(ModItems.DIGGING_CLAWS.get(), 8))
                                .add(item(ModItems.STEADFAST_SPIKES.get(), 8))
                                .add(item(ModItems.UNIVERSAL_ATTRACTOR.get(), 8))
                                .add(item(ModItems.KITTY_SLIPPERS.get(), 8))
                                .add(item(ModItems.RUNNING_SHOES.get(), 8))
                                .add(item(ModItems.BUNNY_HOPPERS.get(), 8))
                                .add(item(ModItems.CRYSTAL_HEART.get(), 8))
                                .add(item(ModItems.VILLAGER_HAT.get(), 8))
                                .add(item(ModItems.CLOUD_IN_A_BOTTLE.get(), 8))
                                .add(item(ModItems.VAMPIRIC_GLOVE.get(), 8))
                                .add(item(ModItems.GOLDEN_HOOK.get(), 8))
                                .add(item(ModItems.CHARM_OF_SINKING.get(), 8))
                                .add(item(ModItems.AQUA_DASHERS.get(), 8))
                                .add(drinkingHat(8))
                                .add(item(ModItems.UMBRELLA.get(), 5))
                                .add(item(ModItems.WHOOPEE_CUSHION.get(), 5))
                                .add(item(ModItems.HELIUM_FLAMINGO.get(), 4))
                                .add(item(ModItems.EVERLASTING_BEEF.get(), 2))
                )
        );
    }

    private void addDrinkingHatsLootTable() {
        addLootTable("items/drinking_hat", LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .name("main")
                                .setRolls(ConstantValue.exactly(1))
                                .add(item(ModItems.PLASTIC_DRINKING_HAT.get(), 3))
                                .add(item(ModItems.NOVELTY_DRINKING_HAT.get(), 1))
                )
        );
    }

    private void addChestLootTables() {
        String barrel = CampsiteFeature.BARREL_LOOT.getPath();
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
                        .add(item(Items.COPPER_BLOCK, 1, 4, 16))
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

        addLootTable(CampsiteFeature.CHEST_LOOT.getPath(), new LootTable.Builder()
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
                        .add(item(Items.BOOK, 8).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15, 30)).allowTreasure()))
                        .add(item(Items.GOLDEN_APPLE, 4))
                        .add(item(Items.ENCHANTED_GOLDEN_APPLE, 1))
                ).withPool(new LootPool.Builder()
                        .name("artifact")
                        .when(ConfigurableRandomChance.configurableRandomChance(0.15F))
                        .add(artifact(1))
                )
        );
    }

    protected static LootPoolSingletonContainer.Builder<?> item(Item item, int weight) {
        return LootItem.lootTableItem(item).setWeight(weight);
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

    private static LootPoolEntryContainer.Builder<?> lootTable(String lootTable, int weight) {
        return LootTableReference.lootTableReference(Artifacts.id(lootTable)).setWeight(weight);
    }

    private void addLootTable(String location, LootTable.Builder lootTable, LootContextParamSet lootParameterSet) {
        if (location.startsWith("inject/")) {
            String actualLocation = location.replace("inject/", "");
            Preconditions.checkArgument(existingFileHelper.exists(new ResourceLocation("loot_tables/" + actualLocation + ".json"), PackType.SERVER_DATA), "Loot table %s does not exist in any known data pack", actualLocation);
        }
        tables.add(new SubProviderEntry(() -> lootBuilder -> lootBuilder.accept(Artifacts.id(location), lootTable), lootParameterSet));
    }

    private void addLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootContextParamSets.ALL_PARAMS);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
        map.forEach((id, lootTable) -> lootTable.validate(context.setParams(lootTable.getParamSet()).enterElement("{" + id + "}", new LootDataId<>(LootDataType.TABLE, id))));
    }
}
