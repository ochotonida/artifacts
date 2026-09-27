package artifacts.neoforge.data;

import artifacts.loot.ConfigValueChance;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class EntityEquipment {

    private final LootTables lootTables;

    public EntityEquipment(LootTables lootTables) {
        this.lootTables = lootTables;
    }

    public void addLootTables() {
        addItems(EntityTypes.ZOMBIE,
                ModItems.COWBOY_HAT.value(),
                ModItems.BUNNY_HOPPERS.value(),
                ModItems.SCARF_OF_INVISIBILITY.value()
        );
        addItems(EntityTypes.HUSK,
                ModItems.VAMPIRIC_GLOVE.value(),
                ModItems.THORN_PENDANT.value()
        );
        addItems(EntityTypes.DROWNED,
                ModItems.SNORKEL.value(),
                ModItems.FLIPPERS.value()
        );
        addEquipment(EntityTypes.SKELETON, LootPool.lootPool()
                .add(LootTables.item(ModItems.NIGHT_VISION_GOGGLES.value(), 1))
                .add(LootTables.drinkingHat(1))
                .add(LootTables.item(ModItems.FLAME_PENDANT.value(), 1))
        );
        addItems(EntityTypes.STRAY,
                ModItems.SNOWSHOES.value(),
                ModItems.STEADFAST_SPIKES.value()
        );
        addItems(EntityTypes.BOGGED,
                ModItems.ANTIDOTE_VESSEL.value(),
                ModItems.ROOTED_BOOTS.value()
        );
        addItems(EntityTypes.PARCHED,
                ModItems.PANIC_NECKLACE.value(),
                ModItems.PICKAXE_HEATER.value()
        );
        addItems(EntityTypes.WITHER_SKELETON,
                ModItems.FIRE_GAUNTLET.value(),
                ModItems.ANTIDOTE_VESSEL.value()
        );
        addItems(EntityTypes.PIGLIN,
                ModItems.GOLDEN_HOOK.value(),
                ModItems.UNIVERSAL_ATTRACTOR.value(),
                ModItems.OBSIDIAN_SKULL.value()
        );
        addItems(EntityTypes.ZOMBIFIED_PIGLIN,
                ModItems.GOLDEN_HOOK.value(),
                ModItems.UNIVERSAL_ATTRACTOR.value(),
                ModItems.OBSIDIAN_SKULL.value()
        );
        addItems(EntityTypes.PIGLIN_BRUTE,
                ModItems.ONION_RING.value(),
                ModItems.STRIDER_SHOES.value()
        );
    }

    public void addItems(EntityType<?> entityType, Item... items) {
        LootPool.Builder pool = LootPool.lootPool();
        for (Item item : items) {
            pool.add(LootTables.item(item, 1));
        }
        addEquipment(entityType, pool);
    }

    public void addEquipment(EntityType<?> entityType, LootPool.Builder pool) {
        LootTable.Builder builder = LootTable.lootTable();
        builder.withPool(pool.when(ConfigValueChance.entityEquipmentChance()));
        lootTables.addLootTable(ModLootTables.getEntityEquipmentLootTable(entityType).identifier().getPath(), _ -> builder, LootContextParamSets.ALL_PARAMS);
    }
}
