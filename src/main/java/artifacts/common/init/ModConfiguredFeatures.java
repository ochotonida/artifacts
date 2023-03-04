package artifacts.common.init;

import artifacts.Artifacts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CAMPSITE = Artifacts.key(Registries.CONFIGURED_FEATURE, "campsite");
}
