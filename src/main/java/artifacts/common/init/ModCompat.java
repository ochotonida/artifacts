package artifacts.common.init;

import artifacts.common.entity.EntityMimic;
import artifacts.common.item.AttributeModifierBauble;
import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.LightLevel;
import net.minecraftforge.fml.common.Loader;

public class ModCompat {

    @JERPlugin
    public static IJERAPI jerApi;

    public static boolean isArtemisLibLoaded;

    public static void preInit() {
        if (Loader.isModLoaded("artemislib")) {
            isArtemisLibLoaded = true;
            AttributeModifierBauble.ExtendedAttributeModifier.initShrinkingModifier();
        }
    }

    public static void init() {
        if (jerApi == null) {
            return;
        }

        jerApi.getMobRegistry().register(new EntityMimic(jerApi.getWorld()), LightLevel.any, 10, ModLootTables.MIMIC_UNDERGROUND);
        jerApi.getDungeonRegistry().registerChest("dungeon.artifacts.underground_chest", ModLootTables.CHEST_UNDERGROUND);
    }
}
