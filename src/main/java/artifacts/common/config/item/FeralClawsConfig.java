package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class FeralClawsConfig {

    public double attackSpeedBonus;

    private final ForgeConfigSpec.DoubleValue attackSpeedBonusValue;

    public FeralClawsConfig(ForgeConfigSpec.Builder builder) {
        builder.push("feral_claws");
        attackSpeedBonusValue = builder
                .worldRestart()
                .comment("Attack speed bonus applied by feral claws")
                .translation(Artifacts.MODID + ".config.server.feral_claws.attack_speed_bonus")
                .defineInRange("attack_speed_bonus", 0.6, -1, Double.POSITIVE_INFINITY);
        builder.pop();
    }

    public void bake() {
        attackSpeedBonus = attackSpeedBonusValue.get();
    }
}
