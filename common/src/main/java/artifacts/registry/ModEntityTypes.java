package artifacts.registry;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Artifacts.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<MimicEntity>> MIMIC = ENTITY_TYPES.register("mimic",
            () -> EntityType.Builder.of(MimicEntity::new, MobCategory.MISC)
                    .sized(14 / 16F, 14 / 16F)
                    .clientTrackingRange(8)
                    .build(Artifacts.id("mimic").toString())
    );
}
