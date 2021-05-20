package artifacts.common.config.item.curio.feet;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class BunnyHoppersConfig extends ItemConfig {

    public ForgeConfigSpec.BooleanValue shouldCancelFallDamage;
    public ForgeConfigSpec.IntValue jumpBoostLevel;

    public BunnyHoppersConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.BUNNY_HOPPERS.get(), "Affects how many times the player can jump with the bunny hoppers before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        shouldCancelFallDamage = builder
                .comment(
                        "Whether Bunny Hoppers should cancel fall damage",
                        "Fall damage will still be reduced by the jump boost effect"
                )
                .translation(translate("should_cancel_fall_damage"))
                .define("should_cancel_fall_damage", true);
        jumpBoostLevel = builder
                .worldRestart()
                .comment(
                        "The level of the jump boost effect applied by the bunny hoppers",
                        "0 to not apply a jump boost effect"
                )
                .translation(translate("jump_boost_level"))
                .defineInRange("jump_boost_level", 2, 0, 128);
    }
}
