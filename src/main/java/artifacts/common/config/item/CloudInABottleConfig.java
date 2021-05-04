package artifacts.common.config.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class CloudInABottleConfig extends ItemConfig {

    public double sprintJumpHeightMultiplier;
    public double sprintJumpDistanceMultiplier;

    private ForgeConfigSpec.DoubleValue sprintJumpHeightMultiplierValue;
    private ForgeConfigSpec.DoubleValue sprintJumpDistanceMultiplierValue;

    public CloudInABottleConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "cloud_in_a_bottle");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        sprintJumpHeightMultiplierValue = builder
                .comment(
                        "Affects double jump height while sprinting",
                        "1 for no height bonus"
                )
                .translation(translate("sprint_jump_height_multiplier"))
                .defineInRange("sprint_jump_height_multiplier", 1.5D, 0, Double.MAX_VALUE);
        sprintJumpDistanceMultiplierValue = builder
                .comment(
                        "Affects double jump distance while sprinting",
                        "0 for no distance bonus"
                )
                .translation(translate("sprint_jump_distance_multiplier"))
                .defineInRange("sprint_jump_distance_multiplier", 0.5D, 0, Double.MAX_VALUE);
    }

    @Override
    public void bake() {
        sprintJumpHeightMultiplier = sprintJumpHeightMultiplierValue.get();
        sprintJumpDistanceMultiplier = sprintJumpDistanceMultiplierValue.get();
    }
}
