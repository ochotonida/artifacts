package artifacts.common.init;

import artifacts.Artifacts;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

public class ModLootTables {

    public static final ResourceLocation MIMIC = new ResourceLocation(Artifacts.MODID, "entities/mimic");
    public static final ResourceLocation CAMPSITE_CHEST = new ResourceLocation(Artifacts.MODID, "chests/campsite_chest");

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    public static class LootTableEvents {

        public static final List<String> LOOT_TABLE_LOCATIONS = Arrays.asList(
                "chests/village/village_armorer",
                "chests/village/village_butcher",
                "chests/village/village_tannery",
                "chests/village/village_temple",
                "chests/village/village_toolsmith",
                "chests/village/village_weaponsmith",
                "chests/village/village_desert_house",
                "chests/village/village_plains_house",
                "chests/village/village_savanna_house",
                "chests/village/village_snowy_house",
                "chests/village/village_taiga_house",
                "chests/abandoned_mineshaft",
                "chests/bastion_hoglin_stable",
                "chests/bastion_treasure",
                "chests/buried_treasure",
                "chests/desert_pyramid",
                "chests/end_city_treasure",
                "chests/jungle_temple",
                "chests/nether_bridge",
                "chests/pillager_outpost",
                "chests/ruined_portal",
                "chests/shipwreck_treasure",
                "chests/spawn_bonus_chest",
                "chests/stronghold_corridor",
                "chests/underwater_ruin_big",
                "chests/woodland_mansion",
                "entities/cow"
        );

        @SubscribeEvent
        @SuppressWarnings("unused")
        public static void onLootTableLoad(LootTableLoadEvent event) {
            String prefix = "minecraft:";
            String name = event.getName().toString();

            if (name.startsWith(prefix)) {
                String location = name.substring(name.indexOf(prefix) + prefix.length());
                if (LOOT_TABLE_LOCATIONS.contains(location)) {
                    Artifacts.LOGGER.debug("Adding loot to " + name);
                    event.getTable().addPool(getInjectPool(location));
                }
            }
        }

        public static LootPool getInjectPool(String entryName) {
            return LootPool.builder()
                    .addEntry(getInjectEntry(entryName))
                    .name("artifacts_inject")
                    .build();
        }

        private static LootEntry.Builder<?> getInjectEntry(String name) {
            ResourceLocation table = new ResourceLocation(Artifacts.MODID, "inject/" + name);
            return TableLootEntry.builder(table).weight(1);
        }
    }
}
