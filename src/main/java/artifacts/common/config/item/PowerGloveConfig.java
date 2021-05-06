package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class PowerGloveConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue attackDamageBonus;

    public PowerGloveConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.POWER_GLOVE.get());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        attackDamageBonus = builder
                .worldRestart()
                .comment("The amount of extra attack damage applied by the power glove")
                .translation(translate("attack_damage_bonus"))
                .defineInRange("attack_damage_bonus", 4, 0, Integer.MAX_VALUE);
    }
}
