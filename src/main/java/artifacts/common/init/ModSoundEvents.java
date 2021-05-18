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
            POP = register("generic.pop"),
            MIMIC_HURT = register("entity.mimic.hurt"),
            MIMIC_DEATH = register("entity.mimic.death"),
            MIMIC_OPEN = register("entity.mimic.open"),
            MIMIC_CLOSE = register("entity.mimic.close"),
            FART = register("item.whoopee_cushion.fart"),
            WATER_STEP = register("block.water.step");

    private static RegistryObject<SoundEvent> register(String name) {
        return REGISTRY.register(name, () -> new SoundEvent(new ResourceLocation(Artifacts.MODID, name)));
    }
}
