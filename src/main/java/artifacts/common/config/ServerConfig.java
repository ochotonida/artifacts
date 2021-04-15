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
                .comment("List of cosmetic-only items. All items in this list will have their effects disabled\nTo blacklist all items, use \"artifacts:*\"\nNote: blacklisting an item while it is equipped may have unintended side effects\nTo completely prevent items from appearing, use a datapack")
                .translation(Artifacts.MODID + ".config.server.cosmetics")
                .define("cosmetics", Lists.newArrayList(""));
        everlastingFoodCooldown = builder
                .comment("Cooldown in ticks for the Everlasting Beef and Eternal Steak items")
                .translation(Artifacts.MODID + ".config.server.eternal_food_cooldown")
                .defineInRange("eternal_food_cooldown", 300, 0, Integer.MAX_VALUE);
        builder.pop();
    }
}
