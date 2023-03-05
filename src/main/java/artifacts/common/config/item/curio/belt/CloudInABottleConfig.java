package artifacts.common.config.item.curio.belt;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class CloudInABottleConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue sprintJumpHeightMultiplier;
    public ForgeConfigSpec.DoubleValue sprintJumpDistanceMultiplier;

    public CloudInABottleConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.CLOUD_IN_A_BOTTLE.getId().getPath());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        sprintJumpHeightMultiplier = builder
                .comment(
                        "Affects double jump height while sprinting",
                        "1 for no height bonus"
                )
                .translation(translate("sprint_jump_height_multiplier"))
                .defineInRange("sprint_jump_height_multiplier", 1.5D, 0, Double.POSITIVE_INFINITY);
        sprintJumpDistanceMultiplier = builder
                .comment(
                        "Affects double jump distance while sprinting",
                        "0 for no distance bonus"
                )
                .translation(translate("sprint_jump_distance_multiplier"))
                .defineInRange("sprint_jump_distance_multiplier", 0.5D, 0, Double.POSITIVE_INFINITY);
    }
}
