package artifacts.registry;

import artifacts.Artifacts;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class ModLootTables {

    private static final String ARTIFACT_LOOT_TABLE_PREFIX = "items/";
    private static final String CHEST_LOOT_PREFIX = "chests/";
    private static final String CAMPSITE_BARREL_PREFIX = CHEST_LOOT_PREFIX + "campsite_barrel/";
    private static final String ENTITY_EQUIPMENT_PREFIX = "entity_equipment/";

    public static final ResourceKey<LootTable> ARTIFACT = Artifacts.key(Registries.LOOT_TABLE, "artifact");
    public static final ResourceKey<LootTable> DRINKING_HAT = Artifacts.key(Registries.LOOT_TABLE, ARTIFACT_LOOT_TABLE_PREFIX + "drinking_hat");

    public static final ResourceKey<LootTable> CAMPSITE_CHEST = Artifacts.key(Registries.LOOT_TABLE, CHEST_LOOT_PREFIX + "campsite_chest");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL = Artifacts.key(Registries.LOOT_TABLE, CHEST_LOOT_PREFIX + "campsite_barrel");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_TNT = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "tnt");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_COBWEBS = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "cobwebs");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_ORES = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "ores");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_INGOTS = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "ingots");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_GEMS = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "gems");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_CROPS = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "crops");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_FOOD = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "food");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_COBBLESTONE = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "cobblestone");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_RAILS = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "rails");
    public static final ResourceKey<LootTable> CAMPSITE_BARREL_MINECARTS = Artifacts.key(Registries.LOOT_TABLE, CAMPSITE_BARREL_PREFIX + "minecarts");

    public static final List<ResourceKey<LootTable>> INJECTED_LOOT_TABLES = List.of(
            EntityTypes.COW.getDefaultLootTable().orElseThrow(),
            EntityTypes.MOOSHROOM.getDefaultLootTable().orElseThrow(),
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
            BuiltInLootTables.WOODLAND_MANSION,
            BuiltInLootTables.IGLOO_CHEST,
            BuiltInLootTables.ANCIENT_CITY_ICE_BOX,
            BuiltInLootTables.ANCIENT_CITY,
            BuiltInLootTables.SIMPLE_DUNGEON
    );

    public static final List<ResourceKey<LootTable>> ARCHAEOLOGY_LOOT_TABLES = List.of(
            BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
            BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
            BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY,
            BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,
            BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE
    );

    public static ResourceKey<LootTable> getEntityEquipmentLootTable(EntityType<?> entityType) {
        Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
        String path = id.toShortString().replace(':', '/');
        return ResourceKey.create(Registries.LOOT_TABLE, Artifacts.id(ENTITY_EQUIPMENT_PREFIX + path));
    }

    public static ResourceKey<LootTable> getArtifactLootTable(Item item) {
        if (!ModItems.isArtifact(item) || item == ModItems.NOVELTY_DRINKING_HAT.get() || item == ModItems.PLASTIC_DRINKING_HAT.get()) {
            throw new IllegalArgumentException();
        }
        return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow()
                .dependent(Registries.LOOT_TABLE, ARTIFACT_LOOT_TABLE_PREFIX::concat);
    }
}
