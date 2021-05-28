package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Artifacts.MODID)
public class ModEntities {

    @SuppressWarnings("unchecked")
    public static final EntityType<MimicEntity> MIMIC = (EntityType<MimicEntity>) EntityType.Builder.of(MimicEntity::new, EntityClassification.MISC)
            .sized(14 / 16F, 14 / 16F)
            .setTrackingRange(64)
            .build(new ResourceLocation(Artifacts.MODID, "mimic").toString())
            .setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic"));

    public static void register(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(MIMIC);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MIMIC, MimicEntity.createMobAttributes().build());
    }
}
