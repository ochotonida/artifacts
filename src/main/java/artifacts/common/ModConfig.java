package artifacts.common;

import artifacts.Artifacts;
import net.minecraftforge.common.config.Config;

@SuppressWarnings("unused")
@Config(modid = Artifacts.MODID, name = Artifacts.MODNAME)
public class ModConfig {

    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment({"per-chunk chance an underground chest is attempted to generate, from y = 1 to y = maxHeight", "setting this to 0 prevents underground chests from generating"})
    public static double undergroundChestChance = 0.02;

    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment({"chance for a mimic to spawn instead of an underground chest", "setting this to 0 prevents underground mimics from generating"})
    public static double undergroundChestMimicRatio = 0.3;

    @Config.RangeInt(min = 1, max = 255)
    @Config.Comment({"maximum height underground chest can generate at", "underground chests generate in the lowest valid position at a randomly chosen x and y in the chunk"})
    public static int underGroundChestMaxHeight = 50;

}
