package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class DiggingClawsConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue miningSpeedBonus;
    public ForgeConfigSpec.IntValue harvestLevel;
    public ForgeConfigSpec.ConfigValue<List<String>> toolTypes;

    public DiggingClawsConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.DIGGING_CLAWS.get(), "Affects how many blocks the player can break using the digging claws before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        miningSpeedBonus = builder
                .worldRestart()
                .comment("Mining speed bonus applied by digging claws")
                .translation(translate("mining_speed_bonus"))
                .defineInRange("mining_speed_bonus", 3.2, 0, Double.POSITIVE_INFINITY);
        harvestLevel = builder
                .comment(
                        "The player's base harvest level when wearing digging claws",
                        "The player's harvest level is equal to MAX(<digging claws harvest level>, <tool harvest level>)",
                        "Harvest level 0 is no tool, 1 for a wooden tool, 2 stone etc."
                )
                .translation(translate("harvest_level"))
                .defineInRange("harvest_level", 2, 0, Integer.MAX_VALUE);
        toolTypes = builder
                .comment(
                        "The tool types of the digging claws",
                        "Blocks that do not have these tool types are not affected by the digging claws",
                        "Use \"*\" to give digging claws all tool types"
                )
                .translation(translate("tool_types"))
                .define("tool_types", Lists.newArrayList("*"));
    }
}
