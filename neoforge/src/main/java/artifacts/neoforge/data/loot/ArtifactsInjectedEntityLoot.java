package artifacts.neoforge.data.loot;

import artifacts.loot.ConfigValueChance;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import net.minecraft.advancements.predicates.entity.EntityFlagsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;

import java.util.List;

public class ArtifactsInjectedEntityLoot implements LootTableSubProvider {

    private final LootTableSubProvider.Context output;
    private final HolderGetter<LootTable> lootTables;

    public ArtifactsInjectedEntityLoot(LootTableSubProvider.Context output) {
        this.output = output;
        lootTables = output.lookup(Registries.LOOT_TABLE);
    }

    @Override
    public void run() {
        LootPool.Builder everlastingBeefLootPool = LootPool.lootPool()
                .when(ConfigValueChance.everlastingBeefChance())
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .add(artifact(ModItems.EVERLASTING_BEEF.value()).apply(
                        SmeltItemFunction.smelted().when(
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().flags(
                                                EntityFlagsPredicate.Builder.flags()
                                                        .setOnFire(true)
                                        )
                                )
                        )
                ));

        List<ResourceKey<LootTable>> cowLootTables = List.of(
                EntityTypes.COW.getDefaultLootTable().orElseThrow(),
                EntityTypes.MOOSHROOM.getDefaultLootTable().orElseThrow()
        );
        for (ResourceKey<LootTable> lootTable : cowLootTables) {
            inject(lootTable, LootTable.lootTable().withPool(everlastingBeefLootPool));
        }
    }

    private void inject(ResourceKey<LootTable> target, LootTable.Builder builder) {
        ResourceKey<LootTable> key = ModLootTables.getInjectedLootTable(target);
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
