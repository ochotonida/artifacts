package artifacts.neoforge.data.loot;

import artifacts.loot.ConfigValueCondition;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.ArrayList;
import java.util.List;

public class ArtifactsItemLoot implements LootTableSubProvider {

    private final Context output;
    private final HolderGetter<LootTable> lootTables;

    protected ArtifactsItemLoot(Context output) {
        this.output = output;
        lootTables = output.lookup(Registries.LOOT_TABLE);
    }

    @Override
    public void run() {
        addItemLootTables();
        addDrinkingHatsLootTable();
        addArtifactLootTable();
    }

    // TODO: remove everlasting beef from this loot table, make all weights equal
    private void addArtifactLootTable() {
        List<Item> items = new ArrayList<>();
        ModItems.ITEMS.forEach(items::add);
        items.removeAll(List.of(
                ModItems.MIMIC_SPAWN_EGG.get(),
                ModItems.NOVELTY_DRINKING_HAT.get(),
                ModItems.PLASTIC_DRINKING_HAT.get(),
                ModItems.WHOOPEE_CUSHION.get(),
                ModItems.HELIUM_FLAMINGO.get(),
                ModItems.ETERNAL_STEAK.get(),
                ModItems.EVERLASTING_BEEF.get(),
                ModItems.UMBRELLA.get()
        ));

        LootPool.Builder builder = LootPool.lootPool().setRolls(ContextIntProviders.exactly(1));
        for (Item item : items) {
            builder.add(artifactWhenEnabled(item).setWeight(8));
        }
        builder.add(drinkingHatWhenEnabled().setWeight(8))
                .add(artifactWhenEnabled(ModItems.UMBRELLA.get()).setWeight(5))
                .add(artifactWhenEnabled(ModItems.WHOOPEE_CUSHION.get()).setWeight(5))
                .add(artifactWhenEnabled(ModItems.HELIUM_FLAMINGO.get()).setWeight(4))
                .add(artifactWhenEnabled(ModItems.EVERLASTING_BEEF.get()).setWeight(2));

        output.accept(ModLootTables.ARTIFACT, LootTable.lootTable().withPool(builder));
    }

    // FIXME: this loot table is used in the `artifact` loot table, and currently allows mimics to select an empty loot item when exactly one drinking hat is enabled
    private void addDrinkingHatsLootTable() {
        output.accept(ModLootTables.DRINKING_HAT, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .add(AlternativesEntry.alternatives(
                                        LootItem.lootTableItem(ModItems.PLASTIC_DRINKING_HAT.get()).setWeight(3).when(ConfigValueCondition.canGenerateAsLoot(ModItems.PLASTIC_DRINKING_HAT.get())),
                                        EmptyLootItem.emptyItem().setWeight(3)
                                ))
                                .add(AlternativesEntry.alternatives(
                                        LootItem.lootTableItem(ModItems.NOVELTY_DRINKING_HAT.get()).setWeight(1).when(ConfigValueCondition.canGenerateAsLoot(ModItems.NOVELTY_DRINKING_HAT.get())),
                                        EmptyLootItem.emptyItem().setWeight(1)
                                ))
                )
        );
    }

    private void addItemLootTables() {
        ModItems.ITEMS.forEach(item -> {
            if (!List.of(
                    ModItems.MIMIC_SPAWN_EGG.get(),
                    ModItems.ETERNAL_STEAK.get(),
                    ModItems.PLASTIC_DRINKING_HAT.get(),
                    ModItems.NOVELTY_DRINKING_HAT.get()
            ).contains(item)) {
                output.accept(ModLootTables.getArtifactLootTable(item), LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(AlternativesEntry.alternatives(
                                                LootItem.lootTableItem(item).setWeight(1)
                                                        .when(ConfigValueCondition.canGenerateAsLoot(item)),
                                                // add an empty entry with the same weight to the loot pool when the item is disabled,
                                                // to keep the probability of the other entries in the loot pool the same
                                                EmptyLootItem.emptyItem().setWeight(1)
                                        ))
                        )
                );
            }
        });
    }

    private UniformContainerBase.Builder<?> drinkingHatWhenEnabled() {
        Holder<LootTable> lootTable = lootTables.getOrThrow(ModLootTables.DRINKING_HAT);
        return NestedLootTable.lootTableReference(lootTable)
                .when(ConfigValueCondition.canGenerateAsLoot(ModItems.PLASTIC_DRINKING_HAT.get())
                        .or(ConfigValueCondition.canGenerateAsLoot(ModItems.NOVELTY_DRINKING_HAT.get()))
                );
    }

    private UniformContainerBase.Builder<?> artifactWhenEnabled(Item item) {
        Holder<LootTable> lootTable = lootTables.getOrThrow(ModLootTables.getArtifactLootTable(item));
        // The extra canGenerateAsLoot check is to remove the otherwise empty loot entry from the `artifacts` loot pool
        //
        return NestedLootTable.lootTableReference(lootTable)
                .when(ConfigValueCondition.canGenerateAsLoot(item));
    }
}
