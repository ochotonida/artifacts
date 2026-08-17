package artifacts.neoforge.data;

import artifacts.loot.ConfigValueChance;
import artifacts.loot.IsAprilFools;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;

public class EntityEquipment {

    private final LootTables lootTables;

    public EntityEquipment(LootTables lootTables) {
        this.lootTables = lootTables;
    }

    public void addLootTables() {
        addItems(EntityType.ZOMBIE,
                ModItems.COWBOY_HAT.value(),
                ModItems.BUNNY_HOPPERS.value(),
                ModItems.SCARF_OF_INVISIBILITY.value()
        );
        addItems(EntityType.HUSK,
                ModItems.VAMPIRIC_GLOVE.value(),
                ModItems.THORN_PENDANT.value()
        );
        addItems(EntityType.DROWNED,
                ModItems.SNORKEL.value(),
                ModItems.FLIPPERS.value()
        );
        addEquipment(EntityType.SKELETON, LootPool.lootPool()
                .add(LootTables.item(ModItems.NIGHT_VISION_GOGGLES.value(), 1))
                .add(LootTables.drinkingHat(1))
                .add(LootTables.item(ModItems.FLAME_PENDANT.value(), 1))
        );
        addItems(EntityType.STRAY,
                ModItems.SNOWSHOES.value(),
                ModItems.STEADFAST_SPIKES.value()
        );
        addItems(EntityType.BOGGED,
                ModItems.ANTIDOTE_VESSEL.value(),
                ModItems.ROOTED_BOOTS.value()
        );
        addItems(EntityType.WITHER_SKELETON,
                ModItems.FIRE_GAUNTLET.value(),
                ModItems.ANTIDOTE_VESSEL.value()
        );
        addItems(EntityType.PIGLIN,
                ModItems.GOLDEN_HOOK.value(),
                ModItems.UNIVERSAL_ATTRACTOR.value(),
                ModItems.OBSIDIAN_SKULL.value()
        );
        addItems(EntityType.ZOMBIFIED_PIGLIN,
                ModItems.GOLDEN_HOOK.value(),
                ModItems.UNIVERSAL_ATTRACTOR.value(),
                ModItems.OBSIDIAN_SKULL.value()
        );
        addItems(EntityType.PIGLIN_BRUTE,
                ModItems.ONION_RING.value(),
                ModItems.STRIDER_SHOES.value()
        );

        lootTables.addLootTable(ModLootTables.getEntityEquipmentLootTable(EntityType.GHAST).location().getPath(), this::ghastLoot, LootContextParamSets.ALL_PARAMS);

    }

    private LootTable.Builder ghastLoot(HolderLookup.Provider registries) {
        LootPool.Builder pool = LootPool.lootPool();

        for (Item item : List.of(
                ModItems.ANGLERS_HAT.value(),
                ModItems.COWBOY_HAT.value(),
                ModItems.VILLAGER_HAT.value(),
                ModItems.NIGHT_VISION_GOGGLES.value(),
                ModItems.SNORKEL.value()
        )) {
            pool.add(LootTables.item(item, 1));
        }
        pool.apply(
                new SetEnchantmentsFunction.Builder().withEnchantment(registries.holderOrThrow(Enchantments.VANISHING_CURSE), ConstantValue.exactly(1))
        ).apply(
                SetComponentsFunction.setComponent(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false)
        );
        return LootTable.lootTable().withPool(pool.when(IsAprilFools.builder()));
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
        lootTables.addLootTable(ModLootTables.getEntityEquipmentLootTable(entityType).location().getPath(), provider -> builder, LootContextParamSets.ALL_PARAMS);
    }
}
