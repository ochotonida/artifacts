package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AntidoteVesselConfig extends ItemConfig {

    public Set<Effect> negativeEffects = Collections.emptySet();

    private ForgeConfigSpec.ConfigValue<List<String>> negativeEffectsValue;
    public ForgeConfigSpec.IntValue maxEffectDuration;

    public AntidoteVesselConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.ANTIDOTE_VESSEL.get(), "Affects how many times a negative effect can be shortened before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        negativeEffectsValue = builder
                .comment("List of negative effects that can be cancelled by the antidote vessel")
                .translation(translate("negative_effects"))
                .define("negative_effects", Lists.newArrayList(
                        "minecraft:slowness",
                        "minecraft:mining_fatigue",
                        "minecraft:nausea",
                        "minecraft:blindness",
                        "minecraft:hunger",
                        "minecraft:weakness",
                        "minecraft:poison",
                        "minecraft:wither",
                        "minecraft:levitation"
                ));
        maxEffectDuration = builder
                .worldRestart()
                .comment("The maximum duration (in ticks) a negative effect can last with the antidote vessel equipped")
                .translation(translate("max_effect_duration"))
                .defineInRange("max_effect_duration", 120, 0, Integer.MAX_VALUE);
    }

    @Override
    public void bake() {
        negativeEffects = negativeEffectsValue.get()
                .stream()
                .map(ResourceLocation::new)
                .map(ForgeRegistries.POTIONS::getValue)
                .collect(Collectors.toSet());
    }
}
