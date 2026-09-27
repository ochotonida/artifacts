package artifacts.registry;

import artifacts.Artifacts;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {

    public static final Register<SoundEvent> SOUND_EVENTS = Register.create(Registries.SOUND_EVENT);

    public static final RegistryHolder<SoundEvent, SoundEvent>
            POP = register("generic.pop"),
            MIMIC_HURT = register("entity.mimic.hurt"),
            MIMIC_DEATH = register("entity.mimic.death"),
            MIMIC_OPEN = register("entity.mimic.open"),
            MIMIC_CLOSE = register("entity.mimic.close"),
            FART = register("item.whoopee_cushion.fart"),
            WATER_STEP = register("block.water.step");

    private static RegistryHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Artifacts.id(name)));
    }
}
