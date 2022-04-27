package artifacts.common.config;

import artifacts.Artifacts;
import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonConfig {

    private Set<ResourceLocation> worldGenBiomeBlacklist;
    private Set<String> worldGenModIdBlacklist;

    private final ForgeConfigSpec.ConfigValue<List<String>> biomeBlacklist;
    public final ForgeConfigSpec.IntValue campsiteCount;
    public final ForgeConfigSpec.IntValue campsiteRarity;
    public final ForgeConfigSpec.IntValue campsiteMinY;
    public final ForgeConfigSpec.IntValue campsiteMaxY;
    public final ForgeConfigSpec.IntValue campsiteScanRange;
    public final ForgeConfigSpec.IntValue campsiteMaxCeilingHeight;
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
        campsiteRarity = builder
                .worldRestart()
                .comment(
                        "Rarity of campsites generating in the world",
                        "You don't need this unless you want to make campsites rarer than 1 attempt per chunk",
                        "Each attempt to generate a campsite will succeed with a chance of 1/rarity"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.rarity")
                .defineInRange("rarity", 1, 1, Integer.MAX_VALUE);
        campsiteMinY = builder
                .worldRestart()
                .comment(
                        "The minimum y-level campsites can spawn at"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.min_y")
                .defineInRange("min_y", -60, -2048, 2048);
        campsiteMaxY = builder
                .worldRestart()
                .comment(
                        "The maximum y-level campsites can spawn at"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.max_y")
                .defineInRange("max_y", 40, -2048, 2048);
        campsiteScanRange = builder
                .worldRestart()
                .comment(
                        "After choosing an initial position between min_y and max_y, a downwards scan will be performed to find a suitable non-air block to place the campsite on",
                        "(This means campsites can spawn slightly below min_y)",
                        "The scan range is the amount of blocks downwards to search for",
                        "If no suitable location is found, no campsite will spawn"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.scan_range")
                .defineInRange("scan_range", 8, 1, 4096);
        campsiteMaxCeilingHeight = builder
                .worldRestart()
                .comment(
                        "The maximum amount of air blocks above a campsite",
                        "To prevent too many campsites from spawning in large, open caves, campsites will not spawn if the cave ceiling in a candidate location is higher than this value",
                        "Set this to 0 to allow campsites to be placed regardless of ceiling height"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.max_ceiling_height")
                .defineInRange("max_ceiling_height", 6, 0, 4096);
        biomeBlacklist = builder
                .worldRestart()
                .comment(
                        "List of biome IDs in which campsites are not allowed to generate",
                        "End and nether biomes are excluded by default and do not have to be in this list",
                        "To blacklist all biomes from a single mod, use \"modid:*\""
                )
                .translation(Artifacts.MODID + ".config.common.campsite.biome_blacklist")
                .define("biome_blacklist", Lists.newArrayList("minecraft:void", "undergarden:*", "the_bumblezone:*", "twilightforest:*"));
        builder.pop();
    }

    public void bake() {
        worldGenBiomeBlacklist = biomeBlacklist.get()
                .stream()
                .filter(string -> !string.endsWith(":*"))
                .map(ResourceLocation::new)
                .collect(Collectors.toSet());
        worldGenModIdBlacklist = biomeBlacklist.get()
                .stream()
                .filter(string -> string.endsWith(":*"))
                .map(string -> string.substring(0, string.length() - 2))
                .collect(Collectors.toSet());
    }

    public boolean isBlacklisted(@Nullable ResourceLocation biome) {
        return biome != null && (worldGenBiomeBlacklist.contains(biome) || worldGenModIdBlacklist.contains(biome.getNamespace()));
    }
}
