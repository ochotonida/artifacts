package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.loot.ConfigValueChance;
import artifacts.neoforge.loot.ReplaceWithTableLootModifier;
import artifacts.registry.ModLootTables;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class LootModifiers extends GlobalLootModifierProvider {

    private static final int ARCHAEOLOGY_MODIFIER_PRIORITY = LootModifier.DEFAULT_PRIORITY / 2;

    public LootModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Artifacts.MOD_ID);
    }

    protected void start() {
        for (ResourceKey<LootTable> target : ModLootTables.INJECTED_LOOT_TABLES) {
            ResourceKey<LootTable> injectedLootTable = ModLootTables.getInjectedArchaeologyLootTable(target);
            LootItemCondition condition = LootTableIdCondition.builder(target.identifier()).build();
            LootModifier lootModifier = new AddTableLootModifier(
                    Optional.of(Holder.direct(condition)),
                    LootModifier.DEFAULT_PRIORITY,
                    injectedLootTable
            );
            add(injectedLootTable.identifier().getPath(), lootModifier);
        }

        // FIXME: items in archeology loot tables might need an extra canGenerateAsLoot check to prevent an empty loot item from being selected
        // FIXME: loot might be replaced with an empty table if all applicable items are disabled in the config
        for (ResourceKey<LootTable> target : ModLootTables.ARCHAEOLOGY_LOOT_TABLES) {
            ResourceKey<LootTable> injectedLootTable = ModLootTables.getInjectedArchaeologyLootTable(target);
            LootItemCondition condition = AllOfCondition.allOf(
                    LootTableIdCondition.builder(target.identifier()),
                    ConfigValueChance.archaeologyChance()
            ).build();
            LootModifier lootModifier = new ReplaceWithTableLootModifier(
                    Optional.of(Holder.direct(condition)),
                    ARCHAEOLOGY_MODIFIER_PRIORITY,
                    injectedLootTable
            );
            add(injectedLootTable.identifier().getPath(), lootModifier);
        }
    }
}
