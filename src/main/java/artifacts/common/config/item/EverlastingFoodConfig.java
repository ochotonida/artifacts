package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class EverlastingFoodConfig {

    public int cooldown;

    private final ForgeConfigSpec.IntValue cooldownValue;

    public EverlastingFoodConfig(ForgeConfigSpec.Builder builder) {
        builder.push("everlasting_food");
        cooldownValue = builder
                .comment("Cooldown in ticks for the Everlasting Beef and Eternal Steak items")
                .translation(Artifacts.MODID + ".config.server.everlasting_food.cooldown")
                .defineInRange("cooldown", 300, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    public void bake() {
        cooldown = cooldownValue.get();
    }
}
