package artifacts.data;

import artifacts.common.init.ModConfiguredFeatures;
import artifacts.common.init.ModFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ConfiguredFeatures {

    public static void create(BootstapContext<ConfiguredFeature<?, ?>> context) {
        ConfiguredFeature<?, ?> campsite = new ConfiguredFeature<>(ModFeatures.CAMPSITE.get(), FeatureConfiguration.NONE);
        context.register(ModConfiguredFeatures.CAMPSITE, campsite);
    }
}
