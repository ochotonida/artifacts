package artifacts.neoforge.data;

import artifacts.loot.ConfigValueChance;
import artifacts.registry.ModLootTables;
import net.minecraft.world.entity.EntityType;
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
