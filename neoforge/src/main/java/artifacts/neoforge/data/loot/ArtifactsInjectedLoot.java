package artifacts.neoforge.data.loot;

import artifacts.Artifacts;
import artifacts.neoforge.data.LootModifiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ArtifactsInjectedLoot implements LootTableSubProvider {

    private final Context output;

    public ArtifactsInjectedLoot(Context output) {
        this.output = output;
    }

    @Override
    public void run() {
        for (LootModifiers.Builder lootBuilder : lootModifiers.getLootBuilders()) {
            ResourceKey<LootTable> key = Artifacts.key(Registries.LOOT_TABLE, "inject/" + lootBuilder.getName());
            output.accept(key, lootBuilder.createLootTable());
        }
    }
}
