package artifacts.common.config;

import artifacts.Artifacts;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class CommonConfig {

    final ForgeConfigSpec.ConfigValue<List<String>> biomeBlacklist;
    final ForgeConfigSpec.IntValue campsiteChance;
    final ForgeConfigSpec.IntValue campsiteMimicChance;
    final ForgeConfigSpec.IntValue campsiteOreChance;
    final ForgeConfigSpec.IntValue campsiteMinY;
    final ForgeConfigSpec.IntValue campsiteMaxY;
    final ForgeConfigSpec.BooleanValue useModdedChests;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("To disable items or to change the frequency at which artifacts appear, override the loot tables from this mod with a data pack\nCosmetic slots are disabled by default, they can be enabled using the Curios config, see https://github.com/TheIllusiveC4/Curios/wiki/How-to-Use:-Users#creating-a-new-slot-type");

        builder.push("campsite");
        biomeBlacklist = builder
                .worldRestart()
                .comment("List of biome IDs in which campsites are not allowed to generate. End and nether biomes are excluded by default.\nTo blacklist all biomes from a single mod, use \"modid:*\"")
                .translation(Artifacts.MODID + ".config.common.biome_blacklist")
                .define("biome_blacklist", Lists.newArrayList("minecraft:void", "undergarden:*", "the_bumblezone:*"));
        campsiteChance = builder
                .worldRestart()
                .comment("Per-chunk probability (as a percentage) a campsite is attempted to be generated. Not every attempt succeeds, this also depends on the density and shape of caves")
                .translation(Artifacts.MODID + ".config.common.campsite_chance")
                .defineInRange("campsite_chance", 8, 0, 100);
        campsiteMinY = builder
                .comment("The minimum y-level at which a campsite can generate")
                .translation(Artifacts.MODID + ".config.common.campsite_min_y")
                .defineInRange("campsite_min_y", 1, 1, 255);
        campsiteMaxY = builder
                .comment("The maximum y-level at which a campsite can generate")
                .translation(Artifacts.MODID + ".config.common.campsite_max_y")
                .defineInRange("campsite_max_y", 45, 0, 255);
        campsiteMimicChance = builder
                .comment("Probability for a container of a campsite to be replaced by a mimic")
                .translation(Artifacts.MODID + ".config.common.campsite_mimic_chance")
                .defineInRange("campsite_mimic_chance", 30, 0, 100);
        campsiteOreChance = builder
                .comment("Probability for an ore vein to generate underneath a campsite")
                .translation(Artifacts.MODID + ".config.common.campsite_ore_chance")
                .defineInRange("campsite_ore_chance", 25, 0, 100);
        useModdedChests = builder
                .comment("Whether to use wooden chests from other mods when generating campsites, this may make it easier to distinguish them from mimics")
                .translation(Artifacts.MODID + ".config.common.use_modded_chests")
                .define("use_modded_chests", true);
        builder.pop();
    }
}
