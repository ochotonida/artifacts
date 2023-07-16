package artifacts.forge.data;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MobEffectTags extends IntrinsicHolderTagsProvider<MobEffect> {

    public MobEffectTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput,
                Registries.MOB_EFFECT,
                lookupProvider,
                effect -> ForgeRegistries.MOB_EFFECTS.getResourceKey(effect).orElseThrow(),
                ArtifactsForge.MOD_ID,
                existingFileHelper
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
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
