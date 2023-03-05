package artifacts.common.config.item.curio.hands;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.TierSortingRegistry;

import javax.annotation.Nullable;

public class DiggingClawsConfig extends ItemConfig {

    @Nullable
    public Tier toolTier;

    public ForgeConfigSpec.DoubleValue miningSpeedBonus;
    public ForgeConfigSpec.ConfigValue<String> toolTierValue;

    public DiggingClawsConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.DIGGING_CLAWS.getId().getPath());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        miningSpeedBonus = builder
                .worldRestart()
                .comment("Mining speed bonus applied by digging claws")
                .translation(translate("mining_speed_bonus"))
                .defineInRange("mining_speed_bonus", 3.2, 0, Double.POSITIVE_INFINITY);
        toolTierValue = builder
                .comment(
                        "The tool tier of the digging claws",
                        "To modify mineable blocks, use the 'artifacts:mineable/digging_claws' block tag"
                )
                .translation(translate("tool_tier"))
                .define("tool_tier", new ResourceLocation("stone").toString());
    }

    public void bake() {
        ResourceLocation location = new ResourceLocation(toolTierValue.get());
        toolTier = TierSortingRegistry.byName(location);
    }
}
