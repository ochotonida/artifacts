package artifacts.registry;

import artifacts.world.placement.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

@SuppressWarnings("unused")
public class ModPlacementModifierTypes {

    public static final Register<MapCodec<? extends PlacementModifier>> PLACEMENT_MODIFIER_TYPES = Register.create(Registries.PLACEMENT_MODIFIER_TYPE);

    public static final RegistryHolder<MapCodec<? extends PlacementModifier>, MapCodec<? extends PlacementModifier>> CEILING_HEIGHT_FILTER = PLACEMENT_MODIFIER_TYPES.register("ceiling_height_filter", () -> CeilingHeightFilter.CODEC);
    public static final RegistryHolder<MapCodec<? extends PlacementModifier>, MapCodec<? extends PlacementModifier>> CAMPSITE_COUNT = PLACEMENT_MODIFIER_TYPES.register("campsite_count", () -> CampsiteCountPlacement.CODEC);
    public static final RegistryHolder<MapCodec<? extends PlacementModifier>, MapCodec<? extends PlacementModifier>> CAMPSITE_HEIGHT_RANGE = PLACEMENT_MODIFIER_TYPES.register("campsite_height_range", () -> CampsiteHeightRangePlacement.CODEC);
    public static final RegistryHolder<MapCodec<? extends PlacementModifier>, MapCodec<? extends PlacementModifier>> CONFIG_VALUE_FILTER = PLACEMENT_MODIFIER_TYPES.register("config_value_filter", () -> ConfigValueFilter.CODEC);
    public static final RegistryHolder<MapCodec<? extends PlacementModifier>, MapCodec<? extends PlacementModifier>> SURFACE_FLATNESS_FILTER = PLACEMENT_MODIFIER_TYPES.register("surface_flatness_filter", () -> SurfaceFlatnessFilter.CODEC);
}
