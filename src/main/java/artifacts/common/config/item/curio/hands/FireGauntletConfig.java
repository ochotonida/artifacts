package artifacts.common.config.item.curio.hands;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class FireGauntletConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue fireDuration;

    public FireGauntletConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.FIRE_GAUNTLET.getId().getPath());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        fireDuration = builder
                .comment("Duration (equal to total fire damage) for which entities are set on fire")
                .translation(translate("fire_duration"))
                .defineInRange("fire_duration", 8, 0, Integer.MAX_VALUE);
    }
}
