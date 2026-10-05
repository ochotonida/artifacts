package artifacts.registry;

import artifacts.Artifacts;
import artifacts.world.CampsiteFeature;
import artifacts.world.SuspiciousChestFeature;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@SuppressWarnings("unused")
public class ModFeatures {

    public static final Register<MapCodec<? extends Feature>> FEATURE_TYPES = Register.create(Registries.FEATURE_TYPE);

    public static final RegistryHolder<MapCodec<? extends Feature>, MapCodec<? extends Feature>> CAMPSITE = FEATURE_TYPES.register("campsite", () -> CampsiteFeature.CODEC);
    public static final RegistryHolder<MapCodec<? extends Feature>, MapCodec<? extends Feature>> SUSPICIOUS_CHEST = FEATURE_TYPES.register("suspicious_chest", () -> SuspiciousChestFeature.CODEC);

    public static final ResourceKey<PlacedFeature> UNDERGROUND_CAMPSITE = Artifacts.key(Registries.PLACED_FEATURE, "underground_campsite");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_MINIMALIST_CAMPSITE = Artifacts.key(Registries.PLACED_FEATURE, "underground_minimalist_campsite");
}
