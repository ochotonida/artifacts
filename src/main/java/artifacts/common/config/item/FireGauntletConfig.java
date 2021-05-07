package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class FireGauntletConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue fireDuration;

    public FireGauntletConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.FIRE_GAUNTLET.get(), "Affects how many enemies can be hit using the fire gauntlet before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        fireDuration = builder
                .comment("Duration (equal to total fire damage) for which entities are set on fire")
                .translation(translate("fire_duration"))
                .defineInRange("fire_duration", 8, 0, Integer.MAX_VALUE);
    }
}
