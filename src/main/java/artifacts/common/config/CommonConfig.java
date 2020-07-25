package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    final ForgeConfigSpec.DoubleValue campsiteChance;
    final ForgeConfigSpec.DoubleValue campsiteMimicChance;
    final ForgeConfigSpec.DoubleValue campsiteOreChance;
    final ForgeConfigSpec.IntValue campsiteMinY;
    final ForgeConfigSpec.IntValue campsiteMaxY;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("campsite");

        campsiteChance = builder
                .comment("Per-chunk chance a campsite is attempted to be generated. Not every attempt succeeds, this also depends on the density and shape of caves")
                .translation(Artifacts.MODID + ".config.campsite_chance")
                .defineInRange("campsite_chance", 0.08, 0, 1);
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

        builder.pop();
    }
}
