package artifacts.common.config.item.curio.feet;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class FlippersConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue swimSpeedBonus;

    public FlippersConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.FLIPPERS.getId().getPath());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        swimSpeedBonus = builder
                .worldRestart()
                .comment("Swim speed bonus applied by flippers")
                .translation(translate("swim_speed_bonus"))
                .defineInRange("swim_speed_bonus", 1, 0, Double.POSITIVE_INFINITY);
    }
}
