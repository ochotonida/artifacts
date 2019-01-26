package artifacts.common;

import artifacts.Artifacts;
import net.minecraftforge.common.config.Config;

@SuppressWarnings("unused")
@Config(modid = Artifacts.MODID, name = Artifacts.MODNAME)
public class ModConfig {

    @Config.RangeDouble(min = 0)
    @Config.Comment({"per-chunk chance an underground chest is attempted to generate, from y = minHeight to y = maxHeight", "setting this to 0 prevents underground chests from generating", "for values higher than 1, the amount of per-chunk attempts lies between floor(value) and ceil(value)"})
    public static double undergroundChestChance = 0.2;

    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment({"chance for a mimic to spawn instead of an underground chest", "setting this to 0 prevents underground mimics from generating"})
    public static double undergroundChestMimicRatio = 0.2;

    @Config.RangeInt(min = 1, max = 255)
    @Config.Comment({"maximum height underground chest can generate at", "underground chests generate in the lowest valid position at a randomly chosen x and y in the chunk"})
    public static int undergroundChestMinHeight = 1;

    @Config.RangeInt(min = 1, max = 255)
    @Config.Comment({"maximum height underground chest can generate at"})
    public static int undergroundChestMaxHeight = 45;

    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment({"chance for a mob to drop everlasting food"})
    public static double everlastingFoodChance = 0.0004;

    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment({"chance for an everlasting fish to generate instead of the regular item when a loot table entry with the generate_everlasting_fish function is rolled", "the loot function generate_everlasting_fish randomly replaces the rolled item with an everlasting fish"})
    public static double everlastingFishChance = 0.1;

    @Config.RangeInt(min = 0)
    @Config.Comment({"weight for the default entry with the generate_everlasting_fish function in the regular fish loottable", "setting this to 0 will prevent an entry from being created"})
    public static int everlastingFishWeight = 1;

}
