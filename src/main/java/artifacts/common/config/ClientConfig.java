package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    final ForgeConfigSpec.BooleanValue modifyHurtSounds;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        modifyHurtSounds = builder
                .worldRestart()
                .comment("Whether the Kitty Slippers and Bunny Hoppers should modify the player's hurt sounds")
                .translation(Artifacts.MODID + ".config.modify_hurt_sounds")
                .define("modify_hurt_sounds", true);
    }
}
