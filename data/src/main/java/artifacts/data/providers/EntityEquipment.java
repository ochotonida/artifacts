package artifacts.data.providers;

import artifacts.loot.ConfigValueChance;
import artifacts.loot.IsAprilFools;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityEquipment {

    private final LootTables lootTables;
    private final Set<EntityType<?>> entityTypes = new HashSet<>();

    public EntityEquipment(LootTables lootTables) {
        this.lootTables = lootTables;
    }

    public void addLootTables() {
        entityTypes.clear();

        addItems(EntityType.ZOMBIE,
                ModItems.COWBOY_HAT.get(),
                ModItems.BUNNY_HOPPERS.get(),
                ModItems.SCARF_OF_INVISIBILITY.get()
        );
        addItems(EntityType.HUSK,
                ModItems.VAMPIRIC_GLOVE.get(),
                ModItems.THORN_PENDANT.get()
        );
        addItems(EntityType.DROWNED,
                ModItems.SNORKEL.get(),
                ModItems.FLIPPERS.get()
        );
        addEquipment(EntityType.SKELETON, LootPool.lootPool()
                .add(item(ModItems.NIGHT_VISION_GOGGLES.get()))
                .add(LootTables.drinkingHat(1))
                .add(item(ModItems.FLAME_PENDANT.get()))
        );
        addItems(EntityType.STRAY,
                ModItems.SNOWSHOES.get(),
                ModItems.STEADFAST_SPIKES.get()
        );
        addItems(EntityType.WITHER_SKELETON,
                ModItems.FIRE_GAUNTLET.get(),
                ModItems.ANTIDOTE_VESSEL.get()
        );
        addItems(EntityType.PIGLIN,
                ModItems.GOLDEN_HOOK.get(),
                ModItems.UNIVERSAL_ATTRACTOR.get(),
                ModItems.OBSIDIAN_SKULL.get()
        );
        addItems(EntityType.ZOMBIFIED_PIGLIN,
                ModItems.GOLDEN_HOOK.get(),
                ModItems.UNIVERSAL_ATTRACTOR.get(),
                ModItems.OBSIDIAN_SKULL.get()
        );
        addItems(EntityType.PIGLIN_BRUTE,
                ModItems.ONION_RING.get()
        );

        LootPool.Builder pool = LootPool.lootPool();
        for (Item item : List.of(
                ModItems.PLASTIC_DRINKING_HAT.get(),
                ModItems.ANGLERS_HAT.get(),
                ModItems.COWBOY_HAT.get(),
                ModItems.VILLAGER_HAT.get(),
                ModItems.NIGHT_VISION_GOGGLES.get(),
                ModItems.SNORKEL.get()
        )) {
            pool.add(item(item));
        }
        pool.apply(
                new SetEnchantmentsFunction.Builder().withEnchantment(Enchantments.VANISHING_CURSE, ConstantValue.exactly(1))
        );
        LootTable.Builder builder = LootTable.lootTable().withPool(pool.when(IsAprilFools.builder()));
        lootTables.addLootTable(ModLootTables.entityEquipmentLootTable(EntityType.GHAST).getPath(), builder, LootContextParamSets.ALL_PARAMS);
        entityTypes.add(EntityType.GHAST);

        if (!entityTypes.equals(ModLootTables.ENTITY_EQUIPMENT.keySet())) {
            throw new IllegalStateException(Sets.symmetricDifference(entityTypes, ModLootTables.ENTITY_EQUIPMENT.keySet()).toString());
        }
    }

    public void addItems(EntityType<?> entityType, Item... items) {
        if (!ModLootTables.ENTITY_EQUIPMENT.containsKey(entityType)) {
            throw new IllegalArgumentException("Missing entity equipment entity: %s".formatted(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
        }
        LootPool.Builder pool = LootPool.lootPool();
        for (Item item : items) {
            pool.add(item(item));
        }
        addEquipment(entityType, pool);
    }

    protected static LootPoolSingletonContainer.Builder<?> item(Item item) {
        return LootItem.lootTableItem(item).setWeight(1);
    }

    public void addEquipment(EntityType<?> entityType, LootPool.Builder pool) {
        entityTypes.add(entityType);
        LootTable.Builder builder = LootTable.lootTable();
        builder.withPool(pool.when(ConfigValueChance.entityEquipmentChance()));
        lootTables.addLootTable(ModLootTables.entityEquipmentLootTable(entityType).getPath(), builder, LootContextParamSets.ALL_PARAMS);
    }
}
