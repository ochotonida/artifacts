package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class CrossNecklaceConfig {

    public int invincibilityBonus;

    private final ForgeConfigSpec.IntValue invincibilityBonusValue;

    public CrossNecklaceConfig(ForgeConfigSpec.Builder builder) {
        builder.push("cross_necklace");
        invincibilityBonusValue = builder
                .comment("The amount of additional ticks the wearer is invincible after being hit")
                .translation(Artifacts.MODID + ".config.server.cross_necklace.invincibility_bonus")
                .defineInRange("invincibility_bonus", 20, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    public void bake() {
        invincibilityBonus = invincibilityBonusValue.get();
    }
}
