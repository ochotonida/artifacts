package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.world.CampsiteFeature;
import artifacts.common.world.CeilingHeightFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Artifacts.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Artifacts.MODID);

    public static final RegistryObject<PlacementModifierType<CeilingHeightFilter>> CEILING_HEIGHT_FILTER = PLACEMENT_MODIFIERS.register("ceiling_height_filter", () -> () -> CeilingHeightFilter.CODEC);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAMPSITE = FEATURES.register("campsite", CampsiteFeature::new);

    public static PlacedFeature UNDERGROUND_CAMPSITE;
}
