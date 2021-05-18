package artifacts.common.config.item;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class UniversalAttractorConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue range;
    public ForgeConfigSpec.DoubleValue motionMultiplier;
    public ForgeConfigSpec.IntValue cooldown;

    public UniversalAttractorConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.UNIVERSAL_ATTRACTOR.get(), "Affects how many items can be picked up using the universal attractor");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        range = builder
                .worldRestart()
                .comment("The range (in blocks) of the universal attractor")
                .translation(translate("range"))
                .defineInRange("range", 5, 0, Integer.MAX_VALUE);
        motionMultiplier = builder
                .comment("Affects how fast items are moved towards the player")
                .translation(translate("motion_multiplier"))
                .defineInRange("motionMultiplier", 0.6, 0, Double.POSITIVE_INFINITY);
        cooldown = builder
                .comment("The cooldown (in ticks) after throwing an item")
                .translation(Artifacts.MODID + ".server.items.cooldown")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
    }
}
