package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class BunnyHoppersConfig extends ItemConfig {

    public boolean shouldCancelFallDamage;
    public int jumpBoostLevel;

    private ForgeConfigSpec.BooleanValue shouldCancelFallDamageValue;
    private ForgeConfigSpec.IntValue jumpBoostLevelValue;

    public BunnyHoppersConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "bunny_hoppers");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        shouldCancelFallDamageValue = builder
                .comment(
                        "Whether Bunny Hoppers should cancel fall damage",
                        "Fall damage will still be reduced by the jump boost effect"
                )
                .translation(translate("should_cancel_fall_damage"))
                .define("should_cancel_fall_damage", true);
        jumpBoostLevelValue = builder
                .worldRestart()
                .comment(
                        "The level of the jump boost effect applied by the Bunny Hoppers",
                        "0 to not apply a jump boost effect"
                )
                .translation(translate("jump_boost_level"))
                .defineInRange("jump_boost_level", 2, 0, 128);
    }

    @Override
    public void bake() {
        shouldCancelFallDamage = shouldCancelFallDamageValue.get();
        jumpBoostLevel = jumpBoostLevelValue.get() - 1;
    }
}
