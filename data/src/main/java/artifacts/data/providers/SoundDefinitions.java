package artifacts.data.providers;

import artifacts.Artifacts;
import artifacts.registry.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class SoundDefinitions extends SoundDefinitionsProvider {

    public SoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, Artifacts.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        addSubtitled(ModSoundEvents.MIMIC_OPEN.get()).with(sound(SoundEvents.CHEST_OPEN));
        addSubtitled(ModSoundEvents.MIMIC_CLOSE.get()).with(sound(SoundEvents.CHEST_CLOSE));
        addSubtitled(ModSoundEvents.MIMIC_HURT.get()).with(sounds("mimic/hurt", 3));
        addSubtitled(ModSoundEvents.MIMIC_DEATH.get()).with(sounds("mimic/death", 2));

        add(ModSoundEvents.WATER_STEP.get())
                .with(sound(SoundEvents.COD_FLOP))
                .subtitle("subtitles.block.generic.footsteps");

        addSubtitled(ModSoundEvents.FART.get()).with(sounds("fart", 2));

        add(ModSoundEvents.POP.get()).with(sound(new ResourceLocation("random/pop")));

    }

    protected static SoundDefinition.Sound sound(SoundEvent soundEvent) {
        return sound(soundEvent.getLocation(), SoundDefinition.SoundType.EVENT);
    }

    protected static SoundDefinition.Sound sound(String path) {
        return sound(Artifacts.id(path));
    }

    protected static SoundDefinition.Sound[] sounds(String path, int count) {
        SoundDefinition.Sound[] result = new SoundDefinition.Sound[count];
        for (int i = 0; i < count; i++) {
            result[i] = sound(path + (i + 1));
        }
        return result;
    }

    private SoundDefinition add(SoundEvent soundEvent) {
        SoundDefinition result = definition();
        add(soundEvent, result);
        return result;
    }

    private SoundDefinition addSubtitled(SoundEvent soundEvent) {
        return add(soundEvent).subtitle("%s.subtitles.%s".formatted(Artifacts.MOD_ID, soundEvent.getLocation().getPath()));
    }
}
