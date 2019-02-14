package artifacts.common.init;

import artifacts.common.entity.EntityMimic;
import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.LightLevel;

public class ModCompat {

    @JERPlugin
    public static IJERAPI jerApi;

    public static void init() {
        if (jerApi == null) {
            return;
        }

        jerApi.getMobRegistry().register(new EntityMimic(jerApi.getWorld()), LightLevel.any, 10, ModLootTables.MIMIC_UNDERGROUND);
        jerApi.getDungeonRegistry().registerChest("dungeon.artifacts.underground_chest", ModLootTables.CHEST_UNDERGROUND);
    }
}
