package artifacts.common.config.item.curio.head;

import artifacts.Artifacts;
import artifacts.common.config.item.ItemConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.RegistryObject;

public class DrinkingHatConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue drinkingDurationMultiplier;
    public ForgeConfigSpec.DoubleValue eatingDurationMultiplier;

    public DrinkingHatConfig(ForgeConfigSpec.Builder builder, RegistryObject<?> item) {
        super(builder, item);
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        drinkingDurationMultiplier = builder
                .worldRestart()
                .comment("Drinking duration multiplier applied by the drinking hat")
                .translation(Artifacts.MODID + ".server.drinking_hat.drinking_duration_multiplier")
                .defineInRange("drinking_duration_multiplier", 0.3, 0, Double.POSITIVE_INFINITY);
        eatingDurationMultiplier = builder
                .worldRestart()
                .comment("Eating duration multiplier applied by the drinking hat")
                .translation(Artifacts.MODID + ".server.drinking_hat.eating_duration_multiplier")
                .defineInRange("eating_duration_multiplier", 0.6, 0, Double.POSITIVE_INFINITY);
    }
}
