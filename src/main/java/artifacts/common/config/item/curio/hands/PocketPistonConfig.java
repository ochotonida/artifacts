package artifacts.common.config.item.curio.hands;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class PocketPistonConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue knockbackBonus;

    public PocketPistonConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.POCKET_PISTON.getId().getPath());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        knockbackBonus = builder
                .comment("The amount of extra knockback applied by the pocket piston")
                .translation(translate("knockback_bonus"))
                .defineInRange("knockback_bonus", 1.5, 0, Double.POSITIVE_INFINITY);
    }
}
