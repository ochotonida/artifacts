package artifacts.common.config.item.curio.necklace;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class LuckyScarfConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue fortuneBonus;

    public LuckyScarfConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.LUCKY_SCARF.get(), "Affects how many blocks can be broken using the lucky scarf");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        fortuneBonus = builder
                .comment("Fortune bonus applied by the lucky scarf")
                .translation(translate("fortune_bonus"))
                .defineInRange("fortune_bonus", 1, 0, Integer.MAX_VALUE);
    }
}
