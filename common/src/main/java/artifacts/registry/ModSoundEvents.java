package artifacts.registry;

import artifacts.Artifacts;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Artifacts.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent>
            POP = register("generic.pop"),
            MIMIC_HURT = register("entity.mimic.hurt"),
            MIMIC_DEATH = register("entity.mimic.death"),
            MIMIC_OPEN = register("entity.mimic.open"),
            MIMIC_CLOSE = register("entity.mimic.close"),
            FART = register("item.whoopee_cushion.fart"),
            WATER_STEP = register("block.water.step");

    private static RegistrySupplier<SoundEvent> register(String name) {
        return RegistrySupplier.of(SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Artifacts.id(name))));
    }
}
