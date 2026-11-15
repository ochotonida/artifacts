package artifacts.neoforge.data.loot;

import artifacts.Artifacts;
import artifacts.loot.ArtifactRarityAdjustedChance;
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

import java.util.Arrays;

public class ArtifactsInjectedStructureLoot implements LootTableSubProvider {

    private final Context output;
    private final HolderGetter<LootTable> lootTables;

    public ArtifactsInjectedStructureLoot(Context output) {
        this.output = output;
        lootTables = output.lookup(Registries.LOOT_TABLE);
    }

    @Override
    public void run() {
        // Village houses
        for (ResourceKey<LootTable> lootTable : Arrays.asList(
                BuiltInLootTables.VILLAGE_DESERT_HOUSE,
                BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
                BuiltInLootTables.VILLAGE_SAVANNA_HOUSE
        )) {
            inject(lootTable, 0.05F,
                    artifact(ModItems.VILLAGER_HAT.value())
            );
        }
        // Snowy village houses
        for (ResourceKey<LootTable> lootTable : Arrays.asList(
                BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
                BuiltInLootTables.VILLAGE_TAIGA_HOUSE
        )) {
            inject(lootTable, 0.08F,
                    artifact(ModItems.VILLAGER_HAT.value()),
                    artifact(ModItems.SNOWSHOES.value())
            );
        }

        inject(BuiltInLootTables.SPAWN_BONUS_CHEST, 1, artifact(ModItems.WHOOPEE_CUSHION.get())
        );
        inject(BuiltInLootTables.VILLAGE_ARMORER, 0.1F,
                artifact(ModItems.STEADFAST_SPIKES.value()),
                artifact(ModItems.SUPERSTITIOUS_HAT.value()),
                artifact(ModItems.RUNNING_SHOES.value()),
                artifact(ModItems.VAMPIRIC_GLOVE.value())
        );
        inject(BuiltInLootTables.VILLAGE_BUTCHER, 0.05F,
                artifact(ModItems.EVERLASTING_BEEF.value())
        );
        inject(BuiltInLootTables.VILLAGE_TANNERY, 0.2F,
                artifact(ModItems.UMBRELLA.value()).setWeight(3),
                artifact(ModItems.WHOOPEE_CUSHION.value()).setWeight(2),
                artifact(ModItems.KITTY_SLIPPERS.value()),
                artifact(ModItems.BUNNY_HOPPERS.value()),
                artifact(ModItems.SCARF_OF_INVISIBILITY.value()),
                artifact(ModItems.ANGLERS_HAT.value())
        );
        inject(BuiltInLootTables.VILLAGE_TEMPLE, 0.2F,
                artifact(ModItems.CROSS_NECKLACE.value()),
                artifact(ModItems.ANTIDOTE_VESSEL.value()),
                artifact(ModItems.CHARM_OF_SINKING.value()),
                artifact(ModItems.WARP_DRIVE.value())
        );
        inject(BuiltInLootTables.VILLAGE_TOOLSMITH, 0.15F,
                artifact(ModItems.DIGGING_CLAWS.value()),
                artifact(ModItems.POCKET_PISTON.value())
        );
        inject(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1F,
                artifact(ModItems.FERAL_CLAWS.value()).setWeight(2),
                artifact(ModItems.CHARM_OF_SHRINKING.value())
        );
        inject(BuiltInLootTables.ABANDONED_MINESHAFT, 0.3F,
                artifact(ModItems.ONION_RING.value()).setWeight(2),
                artifact(ModItems.NIGHT_VISION_GOGGLES.value()),
                artifact(ModItems.PANIC_NECKLACE.value()),
                artifact(ModItems.OBSIDIAN_SKULL.value()),
                artifact(ModItems.SUPERSTITIOUS_HAT.value()),
                artifact(ModItems.DIGGING_CLAWS.value()),
                artifact(ModItems.CLOUD_IN_A_BOTTLE.value()),
                artifact(ModItems.VAMPIRIC_GLOVE.value()),
                artifact(ModItems.AQUA_DASHERS.value()),
                artifact(ModItems.PICKAXE_HEATER.value()),
                NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.DRINKING_HAT))
        );
        inject(BuiltInLootTables.BASTION_HOGLIN_STABLE, 0.25F,
                NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.ARTIFACT)).setWeight(5),
                artifact(ModItems.BUNNY_HOPPERS.value()).setWeight(3),
                artifact(ModItems.FLAME_PENDANT.value()).setWeight(3),
                artifact(ModItems.COWBOY_HAT.value()).setWeight(3),
                artifact(ModItems.PICKAXE_HEATER.value()).setWeight(3),
                artifact(ModItems.WITHERED_BRACELET.value()).setWeight(3),
                artifact(ModItems.EVERLASTING_BEEF.value())
        );
        inject(BuiltInLootTables.BASTION_TREASURE, 0.65F,
                NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.ARTIFACT)).setWeight(6),
                artifact(ModItems.GOLDEN_HOOK.value()).setWeight(3),
                artifact(ModItems.CROSS_NECKLACE.value()).setWeight(3),
                artifact(ModItems.FIRE_GAUNTLET.value()).setWeight(2),
                artifact(ModItems.STEADFAST_SPIKES.value()),
                artifact(ModItems.PANIC_NECKLACE.value()),
                artifact(ModItems.CRYSTAL_HEART.value()),
                artifact(ModItems.ANTIDOTE_VESSEL.value())
        );
        inject(BuiltInLootTables.BURIED_TREASURE, 0.25F,
                artifact(ModItems.SNORKEL.value()).setWeight(5),
                artifact(ModItems.FLIPPERS.value()).setWeight(5),
                artifact(ModItems.UMBRELLA.value()).setWeight(5),
                artifact(ModItems.GOLDEN_HOOK.value()).setWeight(5),
                artifact(ModItems.FERAL_CLAWS.value()).setWeight(3),
                artifact(ModItems.DIGGING_CLAWS.value()).setWeight(3),
                artifact(ModItems.KITTY_SLIPPERS.value()).setWeight(3),
                artifact(ModItems.BUNNY_HOPPERS.value()).setWeight(3),
                artifact(ModItems.LUCKY_SCARF.value()).setWeight(3),
                artifact(ModItems.AQUA_DASHERS.value()).setWeight(3),
                artifact(ModItems.ANGLERS_HAT.value()).setWeight(3),
                NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.DRINKING_HAT)).setWeight(3)
        );
        inject(BuiltInLootTables.DESERT_PYRAMID, 0.2F,
                artifact(ModItems.FLAME_PENDANT.value()).setWeight(2),
                artifact(ModItems.THORN_PENDANT.value()).setWeight(2),
                artifact(ModItems.WHOOPEE_CUSHION.value()).setWeight(2),
                artifact(ModItems.CHARM_OF_SINKING.value()).setWeight(2),
                artifact(ModItems.SHOCK_PENDANT.value()),
                artifact(ModItems.UMBRELLA.value()),
                artifact(ModItems.SCARF_OF_INVISIBILITY.value()),
                artifact(ModItems.UNIVERSAL_ATTRACTOR.value()),
                artifact(ModItems.VAMPIRIC_GLOVE.value()),
                artifact(ModItems.ONION_RING.value())
        );
        inject(BuiltInLootTables.END_CITY_TREASURE, 0.3F,
                artifact(ModItems.CHORUS_TOTEM.value()).setWeight(6),
                artifact(ModItems.HELIUM_FLAMINGO.value()).setWeight(4),
                artifact(ModItems.WARP_DRIVE.value()).setWeight(2),
                artifact(ModItems.CRYSTAL_HEART.value()),
                artifact(ModItems.CLOUD_IN_A_BOTTLE.value())
        );
        inject(BuiltInLootTables.JUNGLE_TEMPLE, 0.35F,
                artifact(ModItems.KITTY_SLIPPERS.value()).setWeight(2),
                artifact(ModItems.ROOTED_BOOTS.value()).setWeight(2),
                artifact(ModItems.BUNNY_HOPPERS.value()),
                artifact(ModItems.ANGLERS_HAT.value())
        );
        inject(BuiltInLootTables.NETHER_BRIDGE, 0.15F,
                artifact(ModItems.CROSS_NECKLACE.value()),
                artifact(ModItems.NIGHT_VISION_GOGGLES.value()),
                artifact(ModItems.POCKET_PISTON.value()),
                artifact(ModItems.RUNNING_SHOES.value()),
                artifact(ModItems.COWBOY_HAT.value()),
                artifact(ModItems.CHARM_OF_SHRINKING.value()),
                artifact(ModItems.STRIDER_SHOES.value()),
                artifact(ModItems.WITHERED_BRACELET.value()),
                NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.DRINKING_HAT))
        );
        inject(BuiltInLootTables.PILLAGER_OUTPOST, 0.25F,
                artifact(ModItems.PANIC_NECKLACE.value()),
                artifact(ModItems.POCKET_PISTON.value()),
                artifact(ModItems.STEADFAST_SPIKES.value()),
                artifact(ModItems.POWER_GLOVE.value()),
                artifact(ModItems.CROSS_NECKLACE.value()),
                artifact(ModItems.SCARF_OF_INVISIBILITY.value()),
                artifact(ModItems.CRYSTAL_HEART.value()),
                artifact(ModItems.CLOUD_IN_A_BOTTLE.value()),
                artifact(ModItems.SUPERSTITIOUS_HAT.value()),
                artifact(ModItems.ROOTED_BOOTS.value())
        );
        inject(BuiltInLootTables.RUINED_PORTAL, 0.15F,
                artifact(ModItems.NIGHT_VISION_GOGGLES.value()),
                artifact(ModItems.THORN_PENDANT.value()),
                artifact(ModItems.FIRE_GAUNTLET.value()),
                artifact(ModItems.POWER_GLOVE.value()),
                artifact(ModItems.UNIVERSAL_ATTRACTOR.value()),
                artifact(ModItems.OBSIDIAN_SKULL.value()),
                artifact(ModItems.LUCKY_SCARF.value()),
                artifact(ModItems.COWBOY_HAT.value()),
                artifact(ModItems.CHARM_OF_SHRINKING.value()),
                artifact(ModItems.STRIDER_SHOES.value())
        );
        inject(BuiltInLootTables.SHIPWRECK_TREASURE, 0.15F,
                artifact(ModItems.GOLDEN_HOOK.value()).setWeight(3),
                artifact(ModItems.SNORKEL.value()),
                artifact(ModItems.FLIPPERS.value()),
                artifact(ModItems.SCARF_OF_INVISIBILITY.value()),
                artifact(ModItems.STEADFAST_SPIKES.value()),
                artifact(ModItems.UNIVERSAL_ATTRACTOR.value()),
                artifact(ModItems.FERAL_CLAWS.value()),
                artifact(ModItems.NIGHT_VISION_GOGGLES.value()),
                artifact(ModItems.OBSIDIAN_SKULL.value()),
                artifact(ModItems.RUNNING_SHOES.value()),
                artifact(ModItems.CHARM_OF_SINKING.value())
        );
        inject(BuiltInLootTables.SIMPLE_DUNGEON, 0.15F,
                artifact(ModItems.CHARM_OF_SHRINKING.value()),
                artifact(ModItems.WARP_DRIVE.value()),
                artifact(ModItems.WITHERED_BRACELET.value()),
                artifact(ModItems.STRIDER_SHOES.value())
        );
        inject(BuiltInLootTables.STRONGHOLD_CORRIDOR, 0.3F,
                artifact(ModItems.POWER_GLOVE.value()),
                artifact(ModItems.ANTIDOTE_VESSEL.value()),
                artifact(ModItems.SUPERSTITIOUS_HAT.value()),
                artifact(ModItems.LUCKY_SCARF.value()),
                artifact(ModItems.AQUA_DASHERS.value()),
                artifact(ModItems.HELIUM_FLAMINGO.value()),
                artifact(ModItems.ROOTED_BOOTS.value()),
                artifact(ModItems.PICKAXE_HEATER.value())
        );
        inject(BuiltInLootTables.UNDERWATER_RUIN_BIG, 0.45F,
                artifact(ModItems.SNORKEL.value()).setWeight(3),
                artifact(ModItems.FLIPPERS.value()).setWeight(3),
                artifact(ModItems.SUPERSTITIOUS_HAT.value()),
                artifact(ModItems.LUCKY_SCARF.value()),
                artifact(ModItems.FIRE_GAUNTLET.value()),
                artifact(ModItems.CROSS_NECKLACE.value()),
                artifact(ModItems.POWER_GLOVE.value()),
                artifact(ModItems.CLOUD_IN_A_BOTTLE.value()),
                artifact(ModItems.ANGLERS_HAT.value())
        );
        inject(BuiltInLootTables.WOODLAND_MANSION, 0.4F,
                artifact(ModItems.CHORUS_TOTEM.value()).setWeight(2),
                NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.ARTIFACT))
        );
        inject(BuiltInLootTables.IGLOO_CHEST, 0.3F,
                artifact(ModItems.SNOWSHOES.value()).setWeight(2),
                artifact(ModItems.VILLAGER_HAT.value()),
                artifact(ModItems.LUCKY_SCARF.value())
        );
        inject(BuiltInLootTables.ANCIENT_CITY_ICE_BOX, 0.2F,
                artifact(ModItems.SNOWSHOES.value())
        );
        inject(BuiltInLootTables.ANCIENT_CITY, 0.15F,
                artifact(ModItems.ROOTED_BOOTS.value()),
                artifact(ModItems.PICKAXE_HEATER.value()),
                artifact(ModItems.ONION_RING.value()),
                artifact(ModItems.AQUA_DASHERS.value()),
                artifact(ModItems.CHARM_OF_SINKING.value()),
                artifact(ModItems.SHOCK_PENDANT.value()),
                artifact(ModItems.HELIUM_FLAMINGO.value()),
                artifact(ModItems.WARP_DRIVE.value())
        );
    }

    private void inject(ResourceKey<LootTable> target, float baseChance, LootPoolEntryContainer.Builder<?>... builders) {
        LootPool.Builder lootPool = LootPool.lootPool();
        for (LootPoolEntryContainer.Builder<?> builder : builders) {
            lootPool.add(builder);
        }
        inject(target, baseChance, lootPool);
    }

    private void inject(ResourceKey<LootTable> target, float baseChance, LootPool.Builder builder) {
        builder.when(ArtifactRarityAdjustedChance.adjustedChance(baseChance));
        inject(target, LootTable.lootTable().withPool(builder));
    }

    private void inject(ResourceKey<LootTable> target, LootTable.Builder builder) {
        ResourceKey<LootTable> key = Artifacts.key(Registries.LOOT_TABLE, "inject/" + target.identifier().getPath());
        output.accept(key, builder);
    }

    private UniformContainerBase.Builder<?> artifact(Item item) {
        return NestedLootTable.lootTableReference(
                lootTables.getOrThrow(
                        ModLootTables.getArtifactLootTable(item)
                )
        );
    }
}
