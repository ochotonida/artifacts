package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.loot.ArtifactRarityAdjustedChance;
import artifacts.loot.ConfigValueChance;
import artifacts.neoforge.loot.ReplaceWithTableLootModifier;
import artifacts.registry.ModLootTables;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class LootModifiers extends GlobalLootModifierProvider {

    protected final List<Builder> lootBuilders = new ArrayList<>();

    public LootModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Artifacts.MOD_ID);
    }

    private void addLoot() {
    }

    protected Builder builder(ResourceKey<LootTable> lootTable, float baseChance) {
        if (!ModLootTables.INJECTED_LOOT_TABLES.contains(lootTable)) {
            throw new IllegalArgumentException("Missing injected loot table: %s".formatted(lootTable));
        }
        Builder builder = new Builder(lootTable);
        builder.lootPoolCondition(ArtifactRarityAdjustedChance.adjustedChance(baseChance));
        builder.lootModifierCondition(LootTableIdCondition.builder(lootTable.identifier()));
        lootBuilders.add(builder);
        return builder;
    }

    protected Builder archaeologyBuilder(ResourceKey<LootTable> lootTable) {
        if (!ModLootTables.ARCHAEOLOGY_LOOT_TABLES.contains(lootTable)) {
            throw new IllegalArgumentException("Missing archaeology loot table: %s".formatted(lootTable));
        }

        // FIXME: items in archeology loot tables might need an extra canGenerateAsLoot check to prevent an empty loot item from being selected
        Builder builder = new Builder(lootTable).replace();
        builder.lootModifierCondition(LootTableIdCondition.builder(lootTable.identifier()));
        builder.lootModifierCondition(ConfigValueChance.archaeologyChance());
        lootBuilders.add(builder);
        return builder;
    }

    protected void start() {
        addLoot();

        for (Builder lootBuilder : lootBuilders) {
            add("inject/" + lootBuilder.getName(), lootBuilder.build());
        }
    }

    public static class Builder {

        private final ResourceKey<LootTable> lootTable;
        private final LootPool.Builder lootPool = LootPool.lootPool();
        private final List<LootItemCondition.Builder> conditions;
        private boolean replace = false;

        private Builder(ResourceKey<LootTable> lootTable) {
            this.lootTable = lootTable;
            this.conditions = new ArrayList<>();
        }

        private LootModifier build() {
            if (this.conditions.isEmpty()) {
                throw new IllegalStateException();
            }

            LootItemCondition.Builder condition = AllOfCondition.allOf(this.conditions.toArray(LootItemCondition.Builder[]::new));
            Optional<Holder<LootItemCondition>> conditionHolder = Optional.of(Holder.direct(condition.build()));
            ResourceKey<LootTable> table = Artifacts.key(Registries.LOOT_TABLE, "inject/" + lootTable.identifier().getPath());
            if (replace) {
                return new ReplaceWithTableLootModifier(conditionHolder, IGlobalLootModifier.DEFAULT_PRIORITY / 2, table);
            }
            return new AddTableLootModifier(conditionHolder, IGlobalLootModifier.DEFAULT_PRIORITY, table);
        }


        public String getName() {
            return lootTable.identifier().getPath();
        }

        public Builder replace() {
            this.replace = true;
            return this;
        }

        private Builder lootPoolCondition(LootItemCondition.Builder condition) {
            lootPool.when(condition);
            return this;
        }

        private Builder lootModifierCondition(LootItemCondition.Builder condition) {
            conditions.add(condition);
            return this;
        }

    }

    protected static LootPoolEntryContainer.Builder<?> item(Item item, int weight, int min, int max) {
        return LootItem.lootTableItem(item).setWeight(weight).apply(SetItemCountFunction.setCount(ContextIntProviders.between(min, max)));
    }
}
