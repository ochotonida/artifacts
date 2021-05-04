package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class DrinkingHatConfig extends ItemConfig {

    public double drinkingDurationMultiplier;
    public boolean enableFastEating;

    private ForgeConfigSpec.DoubleValue drinkingDurationMultiplierValue;
    private ForgeConfigSpec.BooleanValue enableFastEatingValue;

    public DrinkingHatConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "drinking_hat");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        drinkingDurationMultiplierValue = builder
                .worldRestart()
                .comment("Mining speed bonus applied by digging claws")
                .translation(translate("drinking_duration_multiplier"))
                .defineInRange("mining_speed_bonus", 0.25, 0, Double.MAX_VALUE);
        enableFastEatingValue = builder
                .comment("Whether the drinking hat should speed up the eating animation as well")
                .translation(translate("enable_fast_eating"))
                .define("enable_fast_eating", false);
    }

    @Override
    public void bake() {
        drinkingDurationMultiplier = drinkingDurationMultiplierValue.get();
        enableFastEating = enableFastEatingValue.get();
    }
}
