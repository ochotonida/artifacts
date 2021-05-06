package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.config.Config;
import artifacts.common.world.CampsiteFeature;
import artifacts.common.world.InCaveWithChance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class Features {

    public static final Feature<NoFeatureConfig> CAMPSITE_FEATURE = new CampsiteFeature();

    public static void registerFeatures(IForgeRegistry<Feature<?>> registry) {
        registry.register(CAMPSITE_FEATURE.setRegistryName(new ResourceLocation(Artifacts.MODID, "campsite")));
    }

    public static void addFeatures() {
        Placement<ChanceConfig> placement = new InCaveWithChance(ChanceConfig::deserialize);

        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && !Config.isBlacklisted(biome.getRegistryName())) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, CAMPSITE_FEATURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(placement.configure(new ChanceConfig((int) (1 / Config.campsiteChance)))));
            }
        }
    }
}
