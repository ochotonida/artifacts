package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURE_REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, Artifacts.MODID);
    public static final DeferredRegister<Placement<?>> PLACEMENT_REGISTRY = DeferredRegister.create(ForgeRegistries.DECORATORS, Artifacts.MODID);

    public static final RegistryObject<InCaveWithChance> IN_CAVE_WITH_CHANCE = PLACEMENT_REGISTRY.register("in_cave_with_chance", () -> new InCaveWithChance(ChanceConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> CAMPSITE = FEATURE_REGISTRY.register("campsite", CampsiteFeature::new);

    public static ConfiguredFeature<?, ?> UNDERGROUND_CAMPSITE;

    public static void registerConfiguredFeatures() {
        UNDERGROUND_CAMPSITE = CAMPSITE.get()
                .configured(IFeatureConfig.NONE)
                .decorated(IN_CAVE_WITH_CHANCE.get().configured(new ChanceConfig(ModConfig.common.campsiteRarity.get())));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Artifacts.MODID, "underground_campsite"), UNDERGROUND_CAMPSITE);
    }
}
