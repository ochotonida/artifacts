package artifacts.common.config.item.curio.necklace;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class CrossNecklaceConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue invincibilityBonus;

    public CrossNecklaceConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.CROSS_NECKLACE.get(), "Affects how many times the player can take damage with the cross necklace before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        invincibilityBonus = builder
                .comment("The amount of additional ticks the wearer is invincible after being hit")
                .translation(translate("invincibility_bonus"))
                .defineInRange("invincibility_bonus", 20, 0, Integer.MAX_VALUE);
    }
}
