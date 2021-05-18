package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class SuperstitiousHatConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue lootingBonus;

    public SuperstitiousHatConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.SUPERSTITIOUS_HAT.get(), "Affects how many enemies can be killed using the superstitious hat");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        lootingBonus = builder
                .comment("Looting bonus applied by the superstitious hat")
                .translation(translate("looting_bonus"))
                .defineInRange("looting_bonus", 1, 0, Integer.MAX_VALUE);
    }
}
