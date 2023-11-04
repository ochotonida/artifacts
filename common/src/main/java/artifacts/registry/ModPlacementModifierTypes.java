package artifacts.registry;

import artifacts.Artifacts;
import artifacts.world.placement.CampsiteCountPlacement;
import artifacts.world.placement.CampsiteHeightRangePlacement;
import artifacts.world.placement.CeilingHeightFilter;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.function.Supplier;

public class ModPlacementModifierTypes {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Artifacts.MOD_ID, Registries.PLACEMENT_MODIFIER_TYPE);

    public static final RegistrySupplier<PlacementModifierType<CeilingHeightFilter>> CEILING_HEIGHT_FILTER = register("ceiling_height_filter", () -> () -> CeilingHeightFilter.CODEC);
    public static final RegistrySupplier<PlacementModifierType<CampsiteCountPlacement>> CAMPSITE_COUNT = register("campsite_count", () -> () -> CampsiteCountPlacement.CODEC);
    public static final RegistrySupplier<PlacementModifierType<CampsiteHeightRangePlacement>> CAMPSITE_HEIGHT_RANGE = register("campsite_height_range", () -> () -> CampsiteHeightRangePlacement.CODEC);

    private static <T extends PlacementModifierType<?>> RegistrySupplier<T> register(String name, Supplier<T> supplier) {
        return RegistrySupplier.of(PLACEMENT_MODIFIERS.register(name, supplier));
    }
}
