package artifacts.common.config.item.curio.head;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class VillagerHatConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue reputationBonus;

    public VillagerHatConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.VILLAGER_HAT.get(), "Affects how many trades can be completed using the villager hat");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        reputationBonus = builder
                .comment("Affects how much prices are reduced by the villager hat")
                .translation(translate("reputation_bonus"))
                .defineInRange("reputation_bonus", 100, 0, Integer.MAX_VALUE);
    }
}
