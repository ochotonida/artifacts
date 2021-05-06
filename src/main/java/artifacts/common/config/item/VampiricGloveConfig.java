package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class VampiricGloveConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue absorptionRatio;
    public ForgeConfigSpec.IntValue maxHealthAbsorbed;

    public VampiricGloveConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.VAMPIRIC_GLOVE.get());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        absorptionRatio = builder
                .comment("The ratio of dealt damage absorbed by the wearer")
                .translation(translate("absorption_ratio"))
                .defineInRange("absorption_ratio", 0.25, 0, Double.POSITIVE_INFINITY);
        maxHealthAbsorbed = builder
                .comment("The maximum amount of health that can be healed in a single hit")
                .translation(translate("max_health_absorbed"))
                .defineInRange("max_health_absorbed", 6, 0, Integer.MAX_VALUE);
    }
}
