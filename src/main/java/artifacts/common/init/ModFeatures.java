package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.world.CampsiteFeature;
import artifacts.common.world.InCaveWithChance;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURE_REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, Artifacts.MODID);
    public static final DeferredRegister<FeatureDecorator<?>> PLACEMENT_REGISTRY = DeferredRegister.create(ForgeRegistries.DECORATORS, Artifacts.MODID);

    public static final RegistryObject<InCaveWithChance> IN_CAVE_WITH_CHANCE = PLACEMENT_REGISTRY.register("in_cave_with_chance", () -> new InCaveWithChance(ChanceDecoratorConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAMPSITE = FEATURE_REGISTRY.register("campsite", CampsiteFeature::new);

    public static ConfiguredFeature<?, ?> UNDERGROUND_CAMPSITE;

    public static void registerConfiguredFeatures() {
        UNDERGROUND_CAMPSITE = CAMPSITE.get()
                .configured(FeatureConfiguration.NONE)
                .decorated(IN_CAVE_WITH_CHANCE.get().configured(new ChanceDecoratorConfiguration(ModConfig.common.campsiteRarity.get())));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Artifacts.MODID, "underground_campsite"), UNDERGROUND_CAMPSITE);
    }
}
