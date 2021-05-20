package artifacts.common.config.item.curio.hands;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class FeralClawsConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue attackSpeedBonus;

    public FeralClawsConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.FERAL_CLAWS.get(), "Affects how many enemies can be hit using the feral claws before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        attackSpeedBonus = builder
                .worldRestart()
                .comment("Attack speed bonus applied by feral claws")
                .translation(translate("attack_speed_bonus"))
                .defineInRange("attack_speed_bonus", 0.75, 0, Double.POSITIVE_INFINITY);
    }
}
