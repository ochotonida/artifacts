package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class Entities {

    public static final EntityType<MimicEntity> MIMIC = EntityType.Builder.create(MimicEntity::new, EntityClassification.MONSTER).size(14 / 16F, 14 / 16F).setTrackingRange(64).build(new ResourceLocation(Artifacts.MODID, "mimic").toString());

    public static void register(IForgeRegistry<EntityType<?>> registry) {
        MIMIC.setRegistryName(Artifacts.MODID, "mimic");
        registry.registerAll(MIMIC);
    }
}
