package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.registry.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.extensions.IHolderExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MobEffectTags extends TagsProvider<MobEffect> {

    public MobEffectTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput,
                Registries.MOB_EFFECT,
                lookupProvider,
                Artifacts.MOD_ID
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        List<Holder<MobEffect>> effects = List.of(
                MobEffects.BLINDNESS,
                MobEffects.NAUSEA,
                MobEffects.MINING_FATIGUE,
                MobEffects.HUNGER,
                MobEffects.LEVITATION,
                MobEffects.SLOWNESS,
                MobEffects.POISON,
                MobEffects.WEAKNESS,
                MobEffects.WITHER
        );

        tag(ModTags.ANTIDOTE_VESSEL_CANCELLABLE).addAll(effects.stream().map(IHolderExtension::getKey));
    }

    @Override
    public String getName() {
        return "Mob Effect Tags";
    }
}
