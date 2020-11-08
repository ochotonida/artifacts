package artifacts.common.config;

import artifacts.Artifacts;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class CommonConfig {

    final ForgeConfigSpec.ConfigValue<List<String>> biomeBlacklist;
    final ForgeConfigSpec.IntValue campsiteChance;
    final ForgeConfigSpec.DoubleValue campsiteMimicChance;
    final ForgeConfigSpec.DoubleValue campsiteOreChance;
    final ForgeConfigSpec.IntValue campsiteMinY;
    final ForgeConfigSpec.IntValue campsiteMaxY;
    final ForgeConfigSpec.BooleanValue useModdedChests;

    final ForgeConfigSpec.IntValue everlastingFoodCooldown;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("campsite");

        biomeBlacklist = builder
                .worldRestart()
                .comment("List of biome IDs in which campsites are not allowed to generate. End and nether biomes are excluded by default.")
                .translation(Artifacts.MODID + ".config.biome_blacklist")
                .define("biome_blacklist", Lists.newArrayList("minecraft:void"));
        campsiteChance = builder
                .worldRestart()
                .comment("Per-chunk probability (as a percentage) a campsite is attempted to be generated. Not every attempt succeeds, this also depends on the density and shape of caves")
                .translation(Artifacts.MODID + ".config.campsite_chance")
                .defineInRange("campsite_chance", 8, 0, 100);
        campsiteMinY = builder
                .comment("The minimum y-level at which a campsite can generate")
                .translation(Artifacts.MODID + ".config.campsite_min_y")
                .defineInRange("campsite_min_y", 0, 0, 255);
        campsiteMaxY = builder
                .comment("The maximum y-level at which a campsite can generate")
                .translation(Artifacts.MODID + ".config.campsite_max_y")
                .defineInRange("campsite_max_y", 45, 0, 255);
        campsiteMimicChance = builder
                .comment("Chance for a container of a campsite to be replaced by a mimic")
                .translation(Artifacts.MODID + ".config.campsite_mimic_chance")
                .defineInRange("campsite_mimic_chance", 0.3, 0, 1);
        campsiteOreChance = builder
                .comment("Chance for an ore vein to generate underneath a campsite")
                .translation(Artifacts.MODID + ".config.campsite_ore_chance")
                .defineInRange("campsite_ore_chance", 0.25, 0, 1);
        useModdedChests = builder
                .comment("Whether to use wooden chests from other mods when generating campsites, may make it easier to distinguish them from mimics")
                .translation(Artifacts.MODID + ".config.use_modded_chests")
                .define("use_modded_chests", true);


        builder.pop();
        builder.push("items");
        everlastingFoodCooldown = builder
                .comment("Cooldown in ticks for the Everlasting Beef and Eternal Steak items")
                .translation(Artifacts.MODID + ".config.eternal_food_cooldown")
                .defineInRange("eternal_food_cooldown", 300, 0, Integer.MAX_VALUE);
        builder.pop();
    }
}
