package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

public class DrinkingHatConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue drinkingDurationMultiplier;
    public ForgeConfigSpec.BooleanValue enableFastEating;

    public DrinkingHatConfig(ForgeConfigSpec.Builder builder, Item item) {
        super(builder, item);
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        drinkingDurationMultiplier = builder
                .worldRestart()
                .comment("Drinking duration multiplier applied by the drinking hat")
                .translation(Artifacts.MODID + ".server.drinking_hat.drinking_duration_multiplier")
                .defineInRange("drinking_duration_multiplier", 0.25, 0, Double.POSITIVE_INFINITY);
        enableFastEating = builder
                .comment("Whether the drinking hat should speed up the eating animation as well")
                .translation(Artifacts.MODID + ".server.drinking_hat.enable_fast_eating")
                .define("enable_fast_eating", false);
    }
}
