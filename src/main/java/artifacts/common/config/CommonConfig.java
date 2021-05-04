package artifacts.common.config;

import artifacts.Artifacts;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonConfig {

    private Set<ResourceLocation> worldGenBiomeBlacklist;
    private Set<String> worldGenModIdBlacklist;
    public int campsiteRarity;
    public int campsiteMinY;
    public int campsiteMaxY;
    public double campsiteMimicChance;
    public double campsiteOreChance;
    public boolean useModdedChests;

    private final ForgeConfigSpec.ConfigValue<List<String>> biomeBlacklistValue;
    private final ForgeConfigSpec.IntValue campsiteRarityValue;
    private final ForgeConfigSpec.IntValue campsiteMimicChanceValue;
    private final ForgeConfigSpec.IntValue campsiteOreChanceValue;
    private final ForgeConfigSpec.IntValue campsiteMinYValue;
    private final ForgeConfigSpec.IntValue campsiteMaxYValue;
    private final ForgeConfigSpec.BooleanValue useModdedChestsValue;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("campsite");
        biomeBlacklistValue = builder
                .worldRestart()
                .comment(
                        "List of biome IDs in which campsites are not allowed to generate",
                        "End and nether biomes are excluded by default",
                        "To blacklist all biomes from a single mod, use \"modid:*\""
                )
                .translation(Artifacts.MODID + ".config.common.biome_blacklist")
                .define("biome_blacklist", Lists.newArrayList("minecraft:void", "undergarden:*", "the_bumblezone:*"));
        campsiteRarityValue = builder
                .worldRestart()
                .comment(
                        "Rarity of campsites generating in the world",
                        "The chance a campsite generates in a specific chunk is 1/rarity",
                        "A rarity of 1 will generate a campsite in every chunk, while 10000 will generate no campsites",
                        "Not every attempt at generating a campsite succeeds, this also depends on the density and shape of caves"
                )
                .translation(Artifacts.MODID + ".config.common.campsite_rarity")
                .defineInRange("campsite_rarity", 12, 1, 10000);
        campsiteMinYValue = builder
                .comment("The minimum y-level at which a campsite can generate")
                .translation(Artifacts.MODID + ".config.common.campsite_min_y")
                .defineInRange("campsite_min_y", 1, 1, 255);
        campsiteMaxYValue = builder
                .comment("The maximum y-level at which a campsite can generate")
                .translation(Artifacts.MODID + ".config.common.campsite_max_y")
                .defineInRange("campsite_max_y", 45, 1, 255);
        campsiteMimicChanceValue = builder
                .comment("Probability for a container of a campsite to be replaced by a mimic")
                .translation(Artifacts.MODID + ".config.common.campsite_mimic_chance")
                .defineInRange("campsite_mimic_chance", 30, 0, 100);
        campsiteOreChanceValue = builder
                .comment("Probability for an ore vein to generate underneath a campsite")
                .translation(Artifacts.MODID + ".config.common.campsite_ore_chance")
                .defineInRange("campsite_ore_chance", 25, 0, 100);
        useModdedChestsValue = builder
                .comment(
                        "Whether to use wooden chests from other mods when generating campsites",
                        "(keeping this enabled may make it easier to distinguish them from mimics)"
                )
                .translation(Artifacts.MODID + ".config.common.use_modded_chests")
                .define("use_modded_chests", true);
        builder.pop();
    }

    public void bake() {
        worldGenBiomeBlacklist = biomeBlacklistValue.get()
                .stream()
                .filter(string -> !string.endsWith(":*"))
                .map(ResourceLocation::new)
                .collect(Collectors.toSet());
        worldGenModIdBlacklist = biomeBlacklistValue.get()
                .stream()
                .filter(string -> string.endsWith(":*"))
                .map(string -> string.substring(0, string.length() - 2))
                .collect(Collectors.toSet());
        campsiteRarity = campsiteRarityValue.get();
        campsiteMimicChance = campsiteMimicChanceValue.get() / 100D;
        campsiteOreChance = campsiteOreChanceValue.get() / 100D;
        campsiteMinY = campsiteMinYValue.get();
        campsiteMaxY = campsiteMaxYValue.get();
        useModdedChests = useModdedChestsValue.get();
    }

    public boolean isBlacklisted(@Nullable ResourceLocation biome) {
        return biome != null && (worldGenBiomeBlacklist.contains(biome) || worldGenModIdBlacklist.contains(biome.getNamespace()));
    }
}
