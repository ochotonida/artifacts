package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class CrossNecklaceConfig extends ItemConfig {

    public int invincibilityBonus;

    private ForgeConfigSpec.IntValue invincibilityBonusValue;

    public CrossNecklaceConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "cross_necklace");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        invincibilityBonusValue = builder
                .comment("The amount of additional ticks the wearer is invincible after being hit")
                .translation(translate("invincibility_bonus"))
                .defineInRange("invincibility_bonus", 20, 1, Integer.MAX_VALUE);
    }

    @Override
    public void bake() {
        invincibilityBonus = invincibilityBonusValue.get();
    }
}
