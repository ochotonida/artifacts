package artifacts.registry;

import artifacts.Artifacts;
import artifacts.world.CampsiteFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Artifacts.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAMPSITE = FEATURES.register("campsite", CampsiteFeature::new);
}
