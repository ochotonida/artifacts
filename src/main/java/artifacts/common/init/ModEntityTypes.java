package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Artifacts.MODID)
public class ModEntityTypes {

    @SuppressWarnings("unchecked") // TODO use DeferredRegistry (https://github.com/MinecraftForge/MinecraftForge/pull/8044)
    public static final EntityType<MimicEntity> MIMIC = (EntityType<MimicEntity>) EntityType.Builder.of(MimicEntity::new, MobCategory.MISC)
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
