package artifacts.common.config.item.curio.necklace;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class PanicNecklaceConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue speedLevel;
    public ForgeConfigSpec.IntValue speedDuration;

    public PanicNecklaceConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.PANIC_NECKLACE.getId().getPath(), "Affects how many times the necklace's effect can be applied");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        speedLevel = builder
                .comment("The level of the speed effect applied by the panic necklace")
                .translation(translate("speed_level"))
                .defineInRange("speed_level", 1, 0, 128);
        speedDuration = builder
                .comment("The duration (in ticks) of the speed effect applied by the panic necklace")
                .translation(translate("speed_duration"))
                .defineInRange("speed_duration", 160, 0, Integer.MAX_VALUE);
    }
}
