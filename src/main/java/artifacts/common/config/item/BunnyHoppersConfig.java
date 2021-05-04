package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class BunnyHoppersConfig {

    public boolean shouldCancelFallDamage;
    public int jumpBoostLevel;

    private final ForgeConfigSpec.BooleanValue shouldCancelFallDamageValue;
    private final ForgeConfigSpec.IntValue jumpBoostLevelValue;

    public BunnyHoppersConfig(ForgeConfigSpec.Builder builder) {
        builder.push("bunny_hoppers");
        shouldCancelFallDamageValue = builder
                .comment(
                        "Whether Bunny Hoppers should cancel fall damage",
                        "Fall damage will still be reduced by the jump boost effect"
                )
                .translation(Artifacts.MODID + ".config.server.bunny_hoppers.should_cancel_fall_damage")
                .define("should_cancel_fall_damage", true);
        jumpBoostLevelValue = builder
                .worldRestart()
                .comment(
                        "The level of the jump boost effect applied by the Bunny Hoppers",
                        "0 to not apply a jump boost effect"
                )
                .translation(Artifacts.MODID + ".config.server.bunny_hoppers.jump_boost_level")
                .defineInRange("jump_boost_level", 2, 0, 128);
        builder.pop();
    }

    public void bake() {
        shouldCancelFallDamage = shouldCancelFallDamageValue.get();
        jumpBoostLevel = jumpBoostLevelValue.get() - 1;
    }
}
