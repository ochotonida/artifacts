package artifacts.common.config.item.curio.belt;

import artifacts.Artifacts;
import artifacts.common.config.item.ItemConfig;
import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class ObsidianSkullConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue cooldown;
    public ForgeConfigSpec.IntValue fireResistanceDuration;

    public ObsidianSkullConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.OBSIDIAN_SKULL.get(), "Affects how many times the fire resistance effect can be applied before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        cooldown = builder
                .comment("The cooldown (in ticks) on the obsidian skull after applying fire resistance")
                .translation(Artifacts.MODID + ".server.items.cooldown")
                .defineInRange("cooldown", 1200, 0, Integer.MAX_VALUE);
        fireResistanceDuration = builder
                .comment("The duration (in ticks) of the fire resistance effect applied by the obsidian skull after taking fire damage")
                .translation(translate("fire_resistance_duration"))
                .defineInRange("fire_resistance_duration", 600, 0, Integer.MAX_VALUE);
    }
}
