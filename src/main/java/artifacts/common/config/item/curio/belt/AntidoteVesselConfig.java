package artifacts.common.config.item.curio.belt;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class AntidoteVesselConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue maxEffectDuration;

    public AntidoteVesselConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.ANTIDOTE_VESSEL);
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        maxEffectDuration = builder
                .worldRestart()
                .comment("The maximum duration (in ticks) a negative effect can last with the antidote vessel equipped")
                .translation(translate("max_effect_duration"))
                .defineInRange("max_effect_duration", 120, 0, Integer.MAX_VALUE);
    }
}
