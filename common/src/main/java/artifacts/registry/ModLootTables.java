package artifacts.registry;

import artifacts.Artifacts;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModLootTables {

    public static final List<ResourceLocation> INJECTED_LOOT_TABLES = List.of(
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
            BuiltInLootTables.WOODLAND_MANSION,
            BuiltInLootTables.IGLOO_CHEST,
            BuiltInLootTables.ANCIENT_CITY_ICE_BOX,
            BuiltInLootTables.ANCIENT_CITY
    );

    public static final List<ResourceLocation> ARCHAEOLOGY_LOOT_TABLES = List.of(
            BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
            BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
            BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY,
            BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,
            BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE
    );

    public static final Map<EntityType<?>, ResourceLocation> ENTITY_EQUIPMENT;

    static {
        ENTITY_EQUIPMENT = new HashMap<>();
        List.of(
                EntityType.ZOMBIE,
                EntityType.HUSK,
                EntityType.DROWNED,
                EntityType.SKELETON,
                EntityType.STRAY,
                EntityType.WITHER_SKELETON,
                EntityType.PIGLIN,
                EntityType.PIGLIN_BRUTE,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.GHAST
        ).forEach(type -> ENTITY_EQUIPMENT.put(type, entityEquipmentLootTable(type)));
    }

    public static ResourceLocation entityEquipmentLootTable(EntityType<?> entityType) {
        return Artifacts.id("entity_equipment/%s", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath());
    }
}
