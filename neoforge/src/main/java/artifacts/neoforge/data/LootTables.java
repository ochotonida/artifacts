package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.config.value.Value;
import artifacts.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class LootTables extends LootTableProvider {

    private final LootModifiers lootModifiers;

    public LootTables(LootModifiers lootModifiers) {
        super(Set.of(), getTables());
        this.lootModifiers = lootModifiers;
    }

    public List<SubProviderEntry> getTables() {
        tables.clear();
        new EntityEquipment(this).addLootTables();

        for (LootModifiers.Builder lootBuilder : lootModifiers.lootBuilders) {
            addLootTable("inject/" + lootBuilder.getName(), _ -> lootBuilder.createLootTable(), lootBuilder.getContextKeySet());
        }

        return tables;
    }


    protected static LootPoolEntryContainer.Builder<?> item(Item item, int weight) {
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

    protected static LootPoolEntryContainer.Builder<?> item(Item item, int weight, int min, int max) {
        return LootItem.lootTableItem(item).setWeight(weight).apply(SetItemCountFunction.setCount(ContextIntProviders.between(min, max)));
    }

    protected static LootPoolEntryContainer.Builder<?> artifact(int weight) {
        return lootTable("artifact", weight);
    }

    protected static LootPoolEntryContainer.Builder<?> drinkingHat(int weight) {
        return lootTable("items/drinking_hat", weight);
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
