package artifacts.common;

import artifacts.Artifacts;
import net.minecraftforge.common.config.Config;

@SuppressWarnings("unused")
@Config(modid = Artifacts.MODID, name = Artifacts.MODNAME)
public class ModConfig {

    @Config.RangeDouble(min = 0)
    @Config.Comment({"per-chunk chance an underground chest is attempted to generate, from y = 1 to y = 45", "setting this to 0 prevents underground chests from generating", "for values higher than 1, the amount of per-chunk attempts lies between floor(value) and ceil(value)"})
    public static double undergroundChestChance = 0.35;

    @Config.Comment({"list of dimensions underground chests can spawn in (separated by commas)"})
    public static String undergroundChestDimensions = "0";

    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment({"chance for a mimic to spawn instead of an underground chest", "setting this to 0 prevents underground mimics from generating"})
    public static double undergroundChestMimicRatio = 0.4;

    @Config.RangeDouble(min = 0)
    @Config.Comment({"per-chunk chance an underwater shrine is attempted to generate, from y = 255 to y = 1, with at least 20 water blocks between the ocean surface and floor", "setting this to 0 prevents underwater shrines from generating", "for values higher than 1, the amount of per-chunk attempts lies between floor(value) and ceil(value)"})
    public static double underwaterShrineChance = 0.001;

    @Config.Comment({"list of dimensions underwater shrines can spawn in (separated by commas)"})
    public static String underwaterShrineDimensions = "0";

    @Config.Comment({"whether animals can drop everlasting food"})
    public static boolean enableEverlastingFood = true;

    @Config.RangeInt(min = 0)
    @Config.Comment({"Minimum amount of falling stars that spawn when struck while wearing a star cloak"})
    public static int starCloakStarsMin = 3;

    @Config.RangeInt(min = 0)
    @Config.Comment({"Maximum amount of falling stars that spawn when struck while wearing a star cloak"})
    public static int starCloakStarsMax = 8;

    @Config.RangeInt(min = 0)
    @Config.Comment({"Hallow star damage when striking an entity"})
    public static int starCloakDamage = 4;

    @Config.RangeInt(min = 1)
    @Config.Comment({"Attack damage boost for power glove, mechanical glove & fire gauntlet"})
    public static int attackDamageBoost = 3;
}
