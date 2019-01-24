package artifacts.common;

import artifacts.Artifacts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSoundEvents {

    public static final SoundEvent FART = new SoundEvent(new ResourceLocation(Artifacts.MODID, "fart")).setRegistryName("fart");

    public static final SoundEvent MIMIC_HURT = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.hurt")).setRegistryName("mimic.hurt");
    public static final SoundEvent MIMIC_DEATH = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.death")).setRegistryName("mimic.death");
    public static final SoundEvent MIMIC_OPEN = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.open")).setRegistryName("mimic.open");
    public static final SoundEvent MIMIC_CLOSE = new SoundEvent(new ResourceLocation(Artifacts.MODID, "mimic.close")).setRegistryName("mimic.close");

}
