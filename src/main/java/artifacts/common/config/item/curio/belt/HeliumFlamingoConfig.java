package artifacts.common.config.item.curio.belt;

import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class HeliumFlamingoConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue maxFlightTime;
    public ForgeConfigSpec.IntValue rechargeTime;

    public HeliumFlamingoConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.HELIUM_FLAMINGO.getId().getPath());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        maxFlightTime = builder
                .comment(
                        "The maximum amount of time (in ticks) a player can fly using the helium flamingo",
                        "Set to 0 to allow swimming indefinitely"
                )
                .translation(translate("max_flight_time"))
                .defineInRange("max_flight_time", 150, 0, Integer.MAX_VALUE);
        rechargeTime = builder
                .comment(
                        "The amount of time (in ticks) it takes for the helium flamingo to recharge"
                )
                .translation(translate("recharge_time"))
                .defineInRange("recharge_time", 300, 0, Integer.MAX_VALUE);
    }
}
