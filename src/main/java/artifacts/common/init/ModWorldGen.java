package artifacts.common.init;

import artifacts.common.worldgen.WorldGenOceanShrine;
import artifacts.common.worldgen.WorldGenUndergroundChest;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModWorldGen {

    public static void init() {
        GameRegistry.registerWorldGenerator(new WorldGenUndergroundChest(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenOceanShrine(), 0);
    }
}
