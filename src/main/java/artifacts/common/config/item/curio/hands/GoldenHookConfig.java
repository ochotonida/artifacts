package artifacts.common.config.item.curio.hands;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class GoldenHookConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue trackedEntities;
    public ForgeConfigSpec.DoubleValue maximumKillRatio;
    public ForgeConfigSpec.DoubleValue minExperienceMultiplier;
    public ForgeConfigSpec.DoubleValue maxExperienceMultiplier;
    public ForgeConfigSpec.IntValue maxExperience;

    public GoldenHookConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.GOLDEN_HOOK.getId().getPath(), "Affects how many enemies can be killed using the golden hook");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        trackedEntities = builder
                .worldRestart()
                .comment(
                        "Affects the amount of recently killed entities to track",
                        "The amount of extra experience gained from using the golden hook depends on the last x amount of entities killed",
                        "The fewer entities of the same type recently killed, the more experience is dropped"
                )
                .translation(translate("tracked_entities"))
                .defineInRange("tracked_entities", 25, 1, Integer.MAX_VALUE);
        maximumKillRatio = builder
                .comment(
                        "Affects the amount of entities of a type that can be killed before entities of that type will no longer drop extra experience",
                        "If the ratio of entities of the same type recently killed over the total amount of entities recently killed is greater than this value,",
                        "the extra experience dropped is equal to min_experience_multiplier"
                )
                .translation(translate("maximum_kill_ratio"))
                .defineInRange("maximum_kill_ratio", 0.5, 0, 1);
        maxExperienceMultiplier = builder
                .comment(
                        "The maximum amount of extra experience (multiplied with total xp) dropped from entities while wearing the golden hook",
                        "The maximum amount is only dropped when none of the recently killed entities are of the same type",
                        "Otherwise the amount of experience dropped will be somewhere between the maximum and minimum"
                )
                .translation(translate("max_experience_multiplier"))
                .defineInRange("max_experience_multiplier", 5, 0, Double.POSITIVE_INFINITY);
        minExperienceMultiplier = builder
                .comment(
                        "The minimum amount of extra experience (multiplied with total xp) dropped from entities while wearing the golden hook",
                        "1 if entities should drop no extra experience if the maximum kill ratio has been reached"
                )
                .translation(translate("min_experience_multiplier"))
                .defineInRange("min_experience_multiplier", 0, 0, Double.POSITIVE_INFINITY);
        maxExperience = builder
                .comment("The maximum amount of extra experience dropped by entities")
                .translation(translate("max_experience"))
                .defineInRange("max_experience", 50, 0, Integer.MAX_VALUE);
    }
}
