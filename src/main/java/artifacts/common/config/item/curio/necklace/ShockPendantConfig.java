package artifacts.common.config.item.curio.necklace;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class ShockPendantConfig extends PendantConfig {

    public ShockPendantConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.SHOCK_PENDANT.get());
    }

    @Override
    protected double getDefaultStrikeChance() {
        return 0.25;
    }
}
