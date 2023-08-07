package artifacts.fabric.registry;

import artifacts.Artifacts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import java.util.List;

public class ModLootTables {

    // TODO move this to common
    private static final List<ResourceLocation> LOOTTABLES = List.of(
            EntityType.COW.getDefaultLootTable(),
            EntityType.MOOSHROOM.getDefaultLootTable(),
            BuiltInLootTables.VILLAGE_DESERT_HOUSE,
            BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
            BuiltInLootTables.VILLAGE_SAVANNA_HOUSE,
            BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
            BuiltInLootTables.VILLAGE_TAIGA_HOUSE,
            BuiltInLootTables.SPAWN_BONUS_CHEST,
            BuiltInLootTables.VILLAGE_ARMORER,
            BuiltInLootTables.VILLAGE_BUTCHER,
            BuiltInLootTables.VILLAGE_TANNERY,
            BuiltInLootTables.VILLAGE_TEMPLE,
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            BuiltInLootTables.VILLAGE_WEAPONSMITH,
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.BASTION_HOGLIN_STABLE,
            BuiltInLootTables.BASTION_TREASURE,
            BuiltInLootTables.BURIED_TREASURE,
            BuiltInLootTables.DESERT_PYRAMID,
            BuiltInLootTables.END_CITY_TREASURE,
            BuiltInLootTables.JUNGLE_TEMPLE,
            BuiltInLootTables.NETHER_BRIDGE,
            BuiltInLootTables.PILLAGER_OUTPOST,
            BuiltInLootTables.RUINED_PORTAL,
            BuiltInLootTables.SHIPWRECK_TREASURE,
            BuiltInLootTables.STRONGHOLD_CORRIDOR,
            BuiltInLootTables.UNDERWATER_RUIN_BIG,
            BuiltInLootTables.WOODLAND_MANSION
    );

    public static void onLootTableLoad(ResourceLocation id, LootTable.Builder supplier) {
        if (LOOTTABLES.contains(id)) {
            supplier.withPool(LootPool.lootPool().add(getInjectEntry(id.getPath())));
        }
    }

    private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name) {
        ResourceLocation table = Artifacts.id("inject/" + name);
        return LootTableReference.lootTableReference(table).setWeight(1);
    }
}
