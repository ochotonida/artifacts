package artifacts.registry;

import artifacts.Artifacts;
import artifacts.world.CampsiteFeature;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Artifacts.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> CAMPSITE = FEATURES.register("campsite", CampsiteFeature::new);
}
