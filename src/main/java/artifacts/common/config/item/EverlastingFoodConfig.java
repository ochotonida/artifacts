package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

public class EverlastingFoodConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue cooldown;

    public EverlastingFoodConfig(ForgeConfigSpec.Builder builder, Item item) {
        super(builder, item);
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        cooldown = builder
                .comment("Cooldown (in ticks) before the item can be eaten again")
                .translation(Artifacts.MODID + ".server.items.cooldown")
                .defineInRange("cooldown", 300, 0, Integer.MAX_VALUE);
    }
}
