package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class HeliumFlamingoConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue airSupplyDrainRate;

    public HeliumFlamingoConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.HELIUM_FLAMINGO.get(), "Affects how many seconds the player can fly using the helium flamingo before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        airSupplyDrainRate = builder
                .worldRestart()
                .comment(
                        "The rate at which the player's air supply drains each tick while swimming outside of water (players have an air supply of 300)",
                        "Set to 0 to allow swimming indefinitely, or 1 for vanilla drain rate"
                )
                .translation(translate("air_supply_drain_rate"))
                .defineInRange("air_supply_drain_rate", 2, 0, Integer.MAX_VALUE);
    }
}
