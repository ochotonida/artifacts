package artifacts.common.config.item.curio.necklace;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class FlamePendantConfig extends PendantConfig {

    public ForgeConfigSpec.IntValue fireDuration;

    public FlamePendantConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.FLAME_PENDANT.get());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        super.addConfigs(builder);
        fireDuration = builder
                .comment("Duration (equal to total fire damage) for which entities are set on fire")
                .translation(translate("fire_duration"))
                .defineInRange("fire_duration", 10, 0, Integer.MAX_VALUE);
    }

    @Override
    protected double getDefaultStrikeChance() {
        return 0.4;
    }
}
