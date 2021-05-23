package artifacts.common.config.item.curio.feet;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class RunningShoesConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue speedMultiplier;

    public RunningShoesConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.RUNNING_SHOES.getId().getPath(), "Affects how many seconds the player can sprint using the running shoes");
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
