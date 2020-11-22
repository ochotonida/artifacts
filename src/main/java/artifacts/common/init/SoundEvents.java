package artifacts.common.init;

import artifacts.Artifacts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class SoundEvents {

    public static final SoundEvent MIMIC_HURT = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.hurt")).setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic.hurt"));
    public static final SoundEvent MIMIC_DEATH = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.death")).setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic.death"));
    public static final SoundEvent MIMIC_OPEN = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.open")).setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic.open"));
    public static final SoundEvent MIMIC_CLOSE = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.close")).setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic.close"));
    public static final SoundEvent FART = new SoundEvent(new ResourceLocation(Artifacts.MODID, "fart")).setRegistryName(new ResourceLocation(Artifacts.MODID, "fart"));

    public static void register(IForgeRegistry<SoundEvent> registry) {
        registry.register(MIMIC_CLOSE);
        registry.register(MIMIC_OPEN);
        registry.register(MIMIC_HURT);
        registry.register(MIMIC_DEATH);
        registry.register(FART);
    }
}
