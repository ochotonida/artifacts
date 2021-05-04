package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class DiggingClawsConfig extends ItemConfig {

    public float miningSpeedBonus;
    public int harvestLevel;

    private ForgeConfigSpec.DoubleValue miningSpeedBonusValue;
    private ForgeConfigSpec.IntValue harvestLevelValue;

    public DiggingClawsConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "digging_claws");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        miningSpeedBonusValue = builder
                .worldRestart()
                .comment("Mining speed bonus applied by digging claws")
                .translation(translate("mining_speed_bonus"))
                .defineInRange("mining_speed_bonus", 3.2, 0, Double.MAX_VALUE);
        harvestLevelValue = builder
                .comment(
                        "The player's base harvest level when wearing digging claws",
                        "0 for no harvest level increase"
                )
                .translation(translate("harvest_level"))
                .defineInRange("harvest_level", 1, 0, Integer.MAX_VALUE);
    }

    @Override
    public void bake() {
        miningSpeedBonus = (float) (double) miningSpeedBonusValue.get();
        harvestLevel = harvestLevelValue.get();
    }
}
