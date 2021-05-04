package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class FireGauntletConfig extends ItemConfig {

    public int fireDuration;

    private ForgeConfigSpec.IntValue fireDurationValue;

    public FireGauntletConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "fire_gauntlet");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        fireDurationValue = builder
                .comment("Duration (equal to total fire damage) for which entities are set on fire")
                .translation(translate("fire_duration"))
                .defineInRange("fire_duration", 8, 1, Integer.MAX_VALUE);
    }

    @Override
    public void bake() {
        fireDuration = fireDurationValue.get();
    }
}
