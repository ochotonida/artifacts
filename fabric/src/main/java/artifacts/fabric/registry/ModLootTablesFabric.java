package artifacts.fabric.registry;

import artifacts.Artifacts;
import artifacts.loot.ConfigValueChance;
import artifacts.loot.ReplaceWithLootTableFunction;
import artifacts.registry.ModLootTables;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class ModLootTablesFabric {

    public static void onLootTableLoad(ResourceKey<LootTable> key, LootTable.Builder builder, LootTableSource source, HolderLookup.Provider provider) {
        if (source.isBuiltin()) {
            if (ModLootTables.INJECTED_LOOT_TABLES.contains(key)) {
                var reference = NestedLootTable.lootTableReference(provider.getOrThrow(getInjectName(key)));
                builder.withPool(LootPool.lootPool().add(reference));
            }
            if (ModLootTables.ARCHAEOLOGY_LOOT_TABLES.contains(key)) {
                builder.modifyPools(pool -> pool.apply(ReplaceWithLootTableFunction
                        .replaceWithLootTable(getInjectName(key))
                        .when(ConfigValueChance.archaeologyChance()))
                );
            }
        }
    }

    private static ResourceKey<LootTable> getInjectName(ResourceKey<LootTable> name) {
        return Artifacts.key(Registries.LOOT_TABLE, "inject/" + name.identifier().getPath());
    }
}
