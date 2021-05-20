package artifacts.common.config.item.curio.belt;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class CrystalHeartConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue healthBonus;

    public CrystalHeartConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.CRYSTAL_HEART.get(), "Affects how much damage the player can take while wearing the crystal heart before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        healthBonus = builder
                .worldRestart()
                .comment("The amount additional health applied to the wearer")
                .translation(translate("health_bonus"))
                .defineInRange("health_bonus", 10, 0, Integer.MAX_VALUE);
    }
}
