package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class MobEffectTags extends ForgeRegistryTagsProvider<MobEffect> {

    public MobEffectTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForgeRegistries.MOB_EFFECTS, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.ANTIDOTE_VESSEL_CANCELLABLE).add(
                MobEffects.BLINDNESS,
                MobEffects.CONFUSION,
                MobEffects.DIG_SLOWDOWN,
                MobEffects.HUNGER,
                MobEffects.LEVITATION,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.POISON,
                MobEffects.WEAKNESS,
                MobEffects.WITHER
        );
    }

    public String getName() {
        return "Mob Effect Tags";
    }
}
