package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class RunningShoesConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue speedMultiplier;

    public RunningShoesConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.RUNNING_SHOES.get());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        speedMultiplier = builder
                .worldRestart()
                .comment("The amount of extra speed applied by the running shoes")
                .translation(translate("speed_multiplier"))
                .defineInRange("speed_multiplier", 0.4, 0, Double.POSITIVE_INFINITY);
    }
}
