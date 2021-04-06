package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Artifacts.MODID)
public class ModEntities {

    @SuppressWarnings("unchecked")
    public static final EntityType<MimicEntity> MIMIC = (EntityType<MimicEntity>) EntityType.Builder.of(MimicEntity::new, EntityClassification.MONSTER)
            .sized(14 / 16F, 14 / 16F)
            .setTrackingRange(64)
            .build(new ResourceLocation(Artifacts.MODID, "mimic").toString())
            .setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic"));

    public static void register(IForgeRegistry<EntityType<?>> registry) {
        GlobalEntityTypeAttributes.put(MIMIC, MimicEntity.createMobAttributes().build());
        registry.register(MIMIC);
    }
}
