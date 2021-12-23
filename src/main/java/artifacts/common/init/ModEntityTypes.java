package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, Artifacts.MODID);

    public static final RegistryObject<EntityType<MimicEntity>> MIMIC = REGISTRY.register("mimic",
            () -> EntityType.Builder.of(MimicEntity::new, MobCategory.MISC)
                    .sized(14 / 16F, 14 / 16F)
                    .setTrackingRange(64)
                    .build(new ResourceLocation(Artifacts.MODID, "mimic").toString())
    );

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MIMIC.get(), MimicEntity.createMobAttributes().build());
    }
}
