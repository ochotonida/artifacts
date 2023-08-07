package artifacts.fabric.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModFeatures {

    public static void register() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                artifacts.registry.ModFeatures.UNDERGROUND_CAMPSITE
        );
    }
}
