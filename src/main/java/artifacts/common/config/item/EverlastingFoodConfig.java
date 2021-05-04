package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class EverlastingFoodConfig extends ItemConfig {

    public int cooldown;

    private ForgeConfigSpec.IntValue cooldownValue;

    public EverlastingFoodConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "everlasting_food");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        cooldownValue = builder
                .comment("Cooldown in ticks for the Everlasting Beef and Eternal Steak items")
                .translation(translate("cooldown"))
                .defineInRange("cooldown", 300, 0, Integer.MAX_VALUE);
    }

    @Override
    public void bake() {
        cooldown = cooldownValue.get();
    }
}
