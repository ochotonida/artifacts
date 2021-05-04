package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class CrystalHeartConfig {

    public int healthBonus;

    private final ForgeConfigSpec.IntValue healthBonusValue;

    public CrystalHeartConfig(ForgeConfigSpec.Builder builder) {
        builder.push("crystal_heart");
        healthBonusValue = builder
                .worldRestart()
                .comment("The amount additional health applied to the wearer")
                .translation(Artifacts.MODID + ".config.server.crystal_heart.health_bonus")
                .defineInRange("health_bonus", 10, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    public void bake() {
        healthBonus = healthBonusValue.get();
    }
}
