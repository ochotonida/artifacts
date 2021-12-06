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
    public final ForgeConfigSpec.IntValue campsiteRarity;
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
        biomeBlacklist = builder
                .worldRestart()
                .comment(
                        "List of biome IDs in which campsites are not allowed to generate",
                        "End and nether biomes are excluded by default",
                        "To blacklist all biomes from a single mod, use \"modid:*\""
                )
                .translation(Artifacts.MODID + ".config.common.campsite.biome_blacklist")
                .define("biome_blacklist", Lists.newArrayList("minecraft:void", "undergarden:*", "the_bumblezone:*", "twilightforest:*"));
        campsiteRarity = builder
                .worldRestart()
                .comment(
                        "Rarity of campsites generating in the world",
                        "The chance a campsite generates in a specific chunk is 1/rarity",
                        "With a rarity of 1, a campsite will attempt to generate in every chunk",
                        "With a rarity of 10000, no campsites will generate",
                        "Not every attempt at generating a campsite succeeds, this also depends on the density and shape of caves"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.rarity")
                .defineInRange("rarity", 5, 1, 10000);
        useModdedChests = builder
                .comment(
                        "Whether to use wooden chests from other mods when generating campsites",
                        "(keeping this enabled may make it easier to distinguish them from mimics)"
                )
                .translation(Artifacts.MODID + ".config.common.campsite.use_modded_chests")
                .define("use_modded_chests", true);
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
