package artifacts.common.init;

import artifacts.common.ModConfig;
import artifacts.common.worldgen.WorldGenOceanShrine;
import artifacts.common.worldgen.WorldGenUndergroundChest;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModWorldGen {

    public static void init() {
        GameRegistry.registerWorldGenerator(new WorldGenUndergroundChest(getDimensionList(ModConfig.undergroundChestDimensions)), 0);
        GameRegistry.registerWorldGenerator(new WorldGenOceanShrine(getDimensionList(ModConfig.underwaterShrineDimensions)), 0);
    }

    private static List<Integer> getDimensionList(String dimensionString) {
        List<Integer> result = new ArrayList<>();
        dimensionString = dimensionString.replaceAll(" +", "");
        for (String s : dimensionString.split(",")) {
            try {
                result.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
            }
        }
        return result;
    }
}
