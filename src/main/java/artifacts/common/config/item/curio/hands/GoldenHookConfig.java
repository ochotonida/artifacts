package artifacts.common.config.item.curio.hands;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class GoldenHookConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue experienceBonus;

    public GoldenHookConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.GOLDEN_HOOK.getId().getPath(), "Affects how many enemies can be killed using the golden hook before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        experienceBonus = builder
                .comment("The amount of extra experience (multiplied with total xp) entities should drop when killed by a player wearing the golden hook")
                .translation(translate("experience_bonus"))
                .defineInRange("experience_bonus", 0.75, 0, Double.POSITIVE_INFINITY);
    }
}
