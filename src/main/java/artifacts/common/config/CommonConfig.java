package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public final ForgeConfigSpec.IntValue campsiteCount;
    public final ForgeConfigSpec.DoubleValue campsiteMimicChance;
    public final ForgeConfigSpec.BooleanValue useModdedChests;
    public final ForgeConfigSpec.DoubleValue artifactRarity;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        artifactRarity = builder
                .comment(
                        "Affects how common artifacts are (does not affect mimics)",
                        "When this is 1, the default artifact spawn rates will be used",
                        "Values higher that 1 will decrease spawn rates while values lower than 1 will increase spawn rates",
                        "Doubling this value will (roughly) halve the chance a container contains an artifact",
                        "Setting this to 10000 will completely prevent artifacts from spawning",
                        "When set to 0, every container that can contain artifacts will contain an artifact"
                )
                .translation(Artifacts.MODID + ".config.common.artifact_rarity")
                .defineInRange("artifact_rarity", 1, 0, 10000D);

        builder.push("campsite");
        campsiteMimicChance = builder
                .comment("Probability that a campsite has a mimic instead of a chest")
                .translation(Artifacts.MODID + ".config.common.campsite.mimic_chance")
                .defineInRange("mimic_chance", 0.3, 0, 1);
        useModdedChests = builder
                .comment(
                        "Whether to use wooden chests from other mods when generating campsites",
                        "(keeping this enabled may make it easier to distinguish them from mimics)"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.use_modded_chests")
                .define("use_modded_chests", true);
        campsiteCount = builder
                .worldRestart()
                .comment(
                        "Affects the amount of campsites generating in the world",
                        "This is the amount of times a campsite attempts to generate in each chunk",
                        "The actual amount of campsites per chunk is lower than this value, since not every attempt at generating a campsite is successful",
                        "Set this to 0 to prevent campsites from generating entirely"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.count")
                .defineInRange("count", 4, 0, Integer.MAX_VALUE);
        builder.pop();
    }
}
