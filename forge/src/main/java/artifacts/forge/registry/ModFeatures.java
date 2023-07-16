package artifacts.forge.registry;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.world.CampsiteFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ArtifactsForge.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAMPSITE = FEATURES.register("campsite", CampsiteFeature::new);
}
