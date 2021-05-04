package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class FeralClawsConfig extends ItemConfig {

    public double attackSpeedBonus;

    private ForgeConfigSpec.DoubleValue attackSpeedBonusValue;

    public FeralClawsConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "feral_claws");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        attackSpeedBonusValue = builder
                .worldRestart()
                .comment("Attack speed bonus applied by feral claws")
                .translation(translate("attack_speed_bonus"))
                .defineInRange("attack_speed_bonus", 0.6, 0, Double.MAX_VALUE);
    }

    @Override
    public void bake() {
        attackSpeedBonus = attackSpeedBonusValue.get();
    }
}
