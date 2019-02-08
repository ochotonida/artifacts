package artifacts.common.compat;

import artifacts.common.ModLootTables;
import artifacts.common.entity.EntityMimic;
import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.LightLevel;

public class JustEnoughResources {

    @JERPlugin
    public static IJERAPI api;

    public static void init() {
        if (api == null) {
            return;
        }

        api.getMobRegistry().register(new EntityMimic(api.getWorld()), LightLevel.any, 10, ModLootTables.MIMIC_UNDERGROUND);
        api.getDungeonRegistry().registerChest("dungeon.artifacts.underground_chest", ModLootTables.CHEST_UNDERGROUND);
    }
}
