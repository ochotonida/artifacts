package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider {

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = new ArrayList<>();

    private final ExistingFileHelper existingFileHelper;
    private final LootModifiers lootModifiers;

    public LootTables(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, LootModifiers lootModifiers) {
        super(dataGenerator);
        this.existingFileHelper = existingFileHelper;
        this.lootModifiers = lootModifiers;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        tables.clear();
        addDrinkingHatsLootTable();
        addArtifactsLootTable();

        for (LootModifiers.Builder lootBuilder : lootModifiers.lootBuilders) {
            addLootTable("inject/" + lootBuilder.getName(), lootBuilder.createLootTable(), lootBuilder.getParameterSet());
        }

        return tables;
    }

    private void addArtifactsLootTable() {
        addLootTable("artifact", LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .name("main")
                                .setRolls(ConstantValue.exactly(1))
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
                                .setRolls(ConstantValue.exactly(1))
                                .add(createItemEntry(ModItems.PLASTIC_DRINKING_HAT.get(), 3))
                                .add(createItemEntry(ModItems.NOVELTY_DRINKING_HAT.get(), 1))
                )
        );
    }

    protected static LootPoolSingletonContainer.Builder<?> createItemEntry(Item item, int weight) {
        return LootItem.lootTableItem(item).setWeight(weight);
    }

    protected static LootPoolEntryContainer.Builder<?> createArtifactEntry(int weight) {
        return createLootTableEntry("artifact", weight);
    }

    protected static LootPoolEntryContainer.Builder<?> createDrinkingHatEntry(int weight) {
        return createLootTableEntry("items/drinking_hat", weight);
    }

    private static LootPoolEntryContainer.Builder<?> createLootTableEntry(String lootTable, int weight) {
        return LootTableReference.lootTableReference(new ResourceLocation(Artifacts.MODID, lootTable)).setWeight(weight);
    }

    private void addLootTable(String location, LootTable.Builder lootTable, LootContextParamSet lootParameterSet) {
        if (location.startsWith("inject/")) {
            String actualLocation = location.replace("inject/", "");
            Preconditions.checkArgument(existingFileHelper.exists(new ResourceLocation("loot_tables/" + actualLocation + ".json"), PackType.SERVER_DATA), "Loot table %s does not exist in any known data pack", actualLocation);
        }
        tables.add(Pair.of(() -> lootBuilder -> lootBuilder.accept(new ResourceLocation(Artifacts.MODID, location), lootTable), lootParameterSet));
    }

    private void addLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootContextParamSets.ALL_PARAMS);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
        map.forEach((location, lootTable) -> net.minecraft.world.level.storage.loot.LootTables.validate(validationTracker, location, lootTable));    }
}
