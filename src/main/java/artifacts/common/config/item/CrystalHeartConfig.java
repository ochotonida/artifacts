package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class CrystalHeartConfig extends ItemConfig {

    public int healthBonus;

    private ForgeConfigSpec.IntValue healthBonusValue;

    public CrystalHeartConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "crystal_heart");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        healthBonusValue = builder
                .worldRestart()
                .comment("The amount additional health applied to the wearer")
                .translation(translate("health_bonus"))
                .defineInRange("health_bonus", 10, 1, Integer.MAX_VALUE);
    }

    @Override
    public void bake() {
        healthBonus = healthBonusValue.get();
    }
}
