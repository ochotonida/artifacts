package artifacts.neoforge.data.loot;

import artifacts.loot.ConfigValueChance;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;

public class ArtifactsEntityEquipmentLoot implements LootTableSubProvider {

    private final Context output;
    private final HolderGetter<LootTable> lootTables;

    protected ArtifactsEntityEquipmentLoot(Context output) {
        this.output = output;
        this.lootTables = output.lookup(Registries.LOOT_TABLE);
    }

    @Override
    public void run() {
        add(EntityTypes.ZOMBIE, LootPool.lootPool()
                .add(artifact(ModItems.COWBOY_HAT.value()))
                .add(artifact(ModItems.BUNNY_HOPPERS.value()))
                .add(artifact(ModItems.SCARF_OF_INVISIBILITY.value()))
        );
        add(EntityTypes.HUSK, LootPool.lootPool()
                .add(artifact(ModItems.VAMPIRIC_GLOVE.value()))
                .add(artifact(ModItems.THORN_PENDANT.value()))
        );
        add(EntityTypes.DROWNED, LootPool.lootPool()
                .add(artifact(ModItems.SNORKEL.value()))
                .add(artifact(ModItems.FLIPPERS.value()))
        );
        add(EntityTypes.SKELETON, LootPool.lootPool()
                .add(artifact(ModItems.NIGHT_VISION_GOGGLES.get()))
                .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.DRINKING_HAT)))
                .add(artifact(ModItems.FLAME_PENDANT.get()))
        );
        add(EntityTypes.STRAY, LootPool.lootPool()
                .add(artifact(ModItems.SNOWSHOES.value()))
                .add(artifact(ModItems.STEADFAST_SPIKES.value()))
        );
        add(EntityTypes.BOGGED, LootPool.lootPool()
                .add(artifact(ModItems.ANTIDOTE_VESSEL.value()))
                .add(artifact(ModItems.ROOTED_BOOTS.value()))
        );
        add(EntityTypes.PARCHED, LootPool.lootPool()
                .add(artifact(ModItems.PANIC_NECKLACE.value()))
                .add(artifact(ModItems.PICKAXE_HEATER.value()))
        );
        add(EntityTypes.WITHER_SKELETON, LootPool.lootPool()
                .add(artifact(ModItems.FIRE_GAUNTLET.value()))
                .add(artifact(ModItems.ANTIDOTE_VESSEL.value()))
        );
        add(EntityTypes.PIGLIN, LootPool.lootPool()
                .add(artifact(ModItems.GOLDEN_HOOK.value()))
                .add(artifact(ModItems.UNIVERSAL_ATTRACTOR.value()))
                .add(artifact(ModItems.OBSIDIAN_SKULL.value()))
        );
        add(EntityTypes.ZOMBIFIED_PIGLIN, LootPool.lootPool()
                .add(artifact(ModItems.GOLDEN_HOOK.value()))
                .add(artifact(ModItems.UNIVERSAL_ATTRACTOR.value()))
                .add(artifact(ModItems.OBSIDIAN_SKULL.value()))
        );
        add(EntityTypes.PIGLIN_BRUTE, LootPool.lootPool()
                .add(artifact(ModItems.ONION_RING.value()))
                .add(artifact(ModItems.STRIDER_SHOES.value()))
        );
    }

    public void add(EntityType<?> entityType, LootPool.Builder pool) {
        LootTable.Builder builder = LootTable.lootTable();
        builder.withPool(pool.when(ConfigValueChance.entityEquipmentChance()));

        output.accept(ModLootTables.getEntityEquipmentLootTable(entityType), builder);
    }

    private UniformContainerBase.Builder<?> artifact(Item item) {
        return NestedLootTable.lootTableReference(
                lootTables.getOrThrow(
                        ModLootTables.getArtifactLootTable(item)
                )
        );
    }
}
