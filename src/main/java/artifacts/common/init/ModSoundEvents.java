package artifacts.common.init;

import artifacts.Artifacts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Artifacts.MODID);

    public static final RegistryObject<SoundEvent>
            MIMIC_HURT = register("mimic.hurt"),
            MIMIC_DEATH = register("mimic.death"),
            MIMIC_OPEN = register("mimic.open"),
            MIMIC_CLOSE = register("mimic.close"),
            FART = register("fart");

    private static RegistryObject<SoundEvent> register(String name) {
        return REGISTRY.register(name, () -> new SoundEvent(new ResourceLocation(Artifacts.MODID, name)));
    }
}
