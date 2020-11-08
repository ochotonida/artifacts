package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.config.Config;
import artifacts.common.world.CampsiteFeature;
import artifacts.common.world.InCaveWithChance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.IForgeRegistry;

public class Features {

    public static Placement<ChanceConfig> placement = new InCaveWithChance(ChanceConfig.CODEC);

    public static Feature<NoFeatureConfig> campsite = new CampsiteFeature();

    public static ConfiguredFeature<?, ?> UNDERGROUND_CAMPSITE;

    public static void registerFeatures(IForgeRegistry<Feature<?>> registry) {
        campsite.setRegistryName(Artifacts.MODID, "campsite");
        registry.register(campsite);
    }

    public static void registerConfiguredFeatures() {
        UNDERGROUND_CAMPSITE = campsite
                .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                .withPlacement(placement.configure(new ChanceConfig(Config.campsiteChance)));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Artifacts.MODID, "underground_campsite"), UNDERGROUND_CAMPSITE);
    }

    public static void registerPlacements(IForgeRegistry<Placement<?>> registry) {
        placement.setRegistryName(new ResourceLocation(Artifacts.MODID, "in_cave_with_chance"));
        registry.register(placement);
    }
}
