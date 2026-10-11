package artifacts.neoforge.data.loot;

import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModLootTables;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class ArtifactsEntityLoot extends EntityLootSubProvider {

    private final HolderGetter<LootTable> lootTables;

    protected ArtifactsEntityLoot(Context output) {
        super(FeatureFlags.DEFAULT_FLAGS, output);
        lootTables = this.output.lookup(Registries.LOOT_TABLE);
    }

    @Override
    public void generate() {
        add(ModEntityTypes.MIMIC.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(NestedLootTable.lootTableReference(lootTables.getOrThrow(ModLootTables.ARTIFACT)))
                        )
        );
    }
}
