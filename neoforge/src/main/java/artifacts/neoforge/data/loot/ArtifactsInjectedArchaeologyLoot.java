package artifacts.neoforge.data.loot;

import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;

public class ArtifactsInjectedArchaeologyLoot implements LootTableSubProvider {

    private final LootTableSubProvider.Context output;
    private final HolderGetter<LootTable> lootTables;

    public ArtifactsInjectedArchaeologyLoot(LootTableSubProvider.Context output) {
        this.output = output;
        lootTables = output.lookup(Registries.LOOT_TABLE);
    }

    @Override
    public void run() {
        inject(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
                artifact(ModItems.COWBOY_HAT.value()),
                artifact(ModItems.OBSIDIAN_SKULL.value()),
                artifact(ModItems.SNORKEL.value()),
                artifact(ModItems.POWER_GLOVE.value()),
                artifact(ModItems.KITTY_SLIPPERS.value()),
                artifact(ModItems.NIGHT_VISION_GOGGLES.value()),
                artifact(ModItems.SHOCK_PENDANT.value())
        );
        inject(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
                artifact(ModItems.CHARM_OF_SINKING.value()),
                artifact(ModItems.UNIVERSAL_ATTRACTOR.value()),
                artifact(ModItems.SUPERSTITIOUS_HAT.value()),
                artifact(ModItems.UMBRELLA.value()),
                artifact(ModItems.WARP_DRIVE.value()),
                NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.DRINKING_HAT))
        );
        inject(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY,
                artifact(ModItems.LUCKY_SCARF.value()),
                artifact(ModItems.FIRE_GAUNTLET.value()),
                artifact(ModItems.ANGLERS_HAT.value()),
                artifact(ModItems.DIGGING_CLAWS.value()),
                artifact(ModItems.ANTIDOTE_VESSEL.value()),
                artifact(ModItems.WITHERED_BRACELET.value())
        );
        inject(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,
                artifact(ModItems.AQUA_DASHERS.value()),
                artifact(ModItems.ONION_RING.value()),
                artifact(ModItems.RUNNING_SHOES.value()),
                artifact(ModItems.BUNNY_HOPPERS.value()),
                artifact(ModItems.VAMPIRIC_GLOVE.value()),
                artifact(ModItems.STRIDER_SHOES.value())
        );
        inject(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE,
                artifact(ModItems.ROOTED_BOOTS.value()),
                artifact(ModItems.PICKAXE_HEATER.value()),
                artifact(ModItems.AQUA_DASHERS.value()),
                artifact(ModItems.SNOWSHOES.value()),
                artifact(ModItems.STEADFAST_SPIKES.value()),
                artifact(ModItems.VILLAGER_HAT.value()),
                artifact(ModItems.CLOUD_IN_A_BOTTLE.value()),
                artifact(ModItems.FERAL_CLAWS.value()),
                artifact(ModItems.POCKET_PISTON.value()),
                artifact(ModItems.WHOOPEE_CUSHION.value()),
                artifact(ModItems.FLAME_PENDANT.value()),
                artifact(ModItems.THORN_PENDANT.value()),
                artifact(ModItems.CHARM_OF_SHRINKING.value())
        );
    }

    private void inject(ResourceKey<LootTable> target, LootPoolEntryContainer.Builder<?>... builders) {
        LootPool.Builder lootPool = LootPool.lootPool();
        for (LootPoolEntryContainer.Builder<?> builder : builders) {
            lootPool.add(builder);
        }
        inject(target, lootPool);
    }

    private void inject(ResourceKey<LootTable> target, LootPool.Builder builder) {
        ResourceKey<LootTable> key = ModLootTables.getInjectedArchaeologyLootTable(target);
        output.accept(key, LootTable.lootTable().withPool(builder));
    }

    private UniformContainerBase.Builder<?> artifact(Item item) {
        return NestedLootTable.lootTableReference(
                lootTables.getOrThrow(
                        ModLootTables.getArtifactLootTable(item)
                )
        );
    }
}
