package artifacts.registry;

import artifacts.Artifacts;
import artifacts.world.placement.CampsiteCountPlacement;
import artifacts.world.placement.CampsiteHeightRangePlacement;
import artifacts.world.placement.CeilingHeightFilter;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ModPlacementModifierTypes {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Artifacts.MOD_ID, Registries.PLACEMENT_MODIFIER_TYPE);

    public static final RegistrySupplier<PlacementModifierType<CeilingHeightFilter>> CEILING_HEIGHT_FILTER = PLACEMENT_MODIFIERS.register("ceiling_height_filter", () -> () -> CeilingHeightFilter.CODEC);
    public static final RegistrySupplier<PlacementModifierType<CampsiteCountPlacement>> CAMPSITE_COUNT = PLACEMENT_MODIFIERS.register("campsite_count", () -> () -> CampsiteCountPlacement.CODEC);
    public static final RegistrySupplier<PlacementModifierType<CampsiteHeightRangePlacement>> CAMPSITE_HEIGHT_RANGE = PLACEMENT_MODIFIERS.register("campsite_height_range", () -> () -> CampsiteHeightRangePlacement.CODEC);
}
