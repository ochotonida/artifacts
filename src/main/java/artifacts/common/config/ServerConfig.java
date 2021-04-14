package artifacts.common.config;

import artifacts.Artifacts;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfig {

    final ForgeConfigSpec.ConfigValue<List<String>> cosmetics;
    final ForgeConfigSpec.IntValue everlastingFoodCooldown;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        cosmetics = builder
                .worldRestart()
                .comment("List of cosmetic-only items\nTo blacklist all items, use \"artifacts:*\"\nNote: blacklisting an item while it is equipped may have unintended side effects")
                .translation(Artifacts.MODID + ".config.server.cosmetics")
                .define("biome_blacklist", Lists.newArrayList(""));
        everlastingFoodCooldown = builder
                .comment("Cooldown in ticks for the Everlasting Beef and Eternal Steak items")
                .translation(Artifacts.MODID + ".config.server.eternal_food_cooldown")
                .defineInRange("eternal_food_cooldown", 300, 0, Integer.MAX_VALUE);
        builder.pop();
    }
}
