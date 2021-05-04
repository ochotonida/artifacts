package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class CloudInABottleConfig {

    public double sprintJumpHeightMultiplier;
    public double sprintJumpDistanceMultiplier;

    private final ForgeConfigSpec.DoubleValue sprintJumpHeightMultiplierValue;
    private final ForgeConfigSpec.DoubleValue sprintJumpDistanceMultiplierValue;

    public CloudInABottleConfig(ForgeConfigSpec.Builder builder) {
        builder.push("cloud_in_a_bottle");
        sprintJumpHeightMultiplierValue = builder
                .comment(
                        "Affects double jump height while sprinting",
                        "1 for no height bonus"
                )
                .translation(Artifacts.MODID + ".config.server.cloud_in_a_bottle.sprint_jump_height_multiplier")
                .defineInRange("sprint_jump_height_multiplier", 1.5D, 0, Double.POSITIVE_INFINITY);
        sprintJumpDistanceMultiplierValue = builder
                .comment(
                        "Affects double jump distance while sprinting",
                        "0 for no distance bonus"
                )
                .translation(Artifacts.MODID + ".config.server.cloud_in_a_bottle.sprint_jump_distance_multiplier")
                .defineInRange("sprint_jump_distance_multiplier", 0.5D, 0, Double.POSITIVE_INFINITY);
        builder.pop();
    }

    public void bake() {
        sprintJumpHeightMultiplier = sprintJumpHeightMultiplierValue.get();
        sprintJumpDistanceMultiplier = sprintJumpDistanceMultiplierValue.get();
    }
}
