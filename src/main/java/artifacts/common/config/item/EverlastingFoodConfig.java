package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.RegistryObject;

public class EverlastingFoodConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue cooldown;
    public ForgeConfigSpec.IntValue useDuration;

    public EverlastingFoodConfig(ForgeConfigSpec.Builder builder, RegistryObject<?> item) {
        super(builder, item, "Affects how many times this item can be eaten before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        cooldown = builder
                .comment("Cooldown (in ticks) before the item can be eaten again")
                .translation(Artifacts.MODID + ".server.items.cooldown")
                .defineInRange("cooldown", 300, 0, Integer.MAX_VALUE);
        useDuration = builder
                .comment("Time (in ticks) it takes to eat this item")
                .translation(Artifacts.MODID + ".server.everlasting_foods.use_duration")
                .defineInRange("use_duration", 24, 0, Integer.MAX_VALUE);
    }
}
