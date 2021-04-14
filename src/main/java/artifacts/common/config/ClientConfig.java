package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    final ForgeConfigSpec.BooleanValue modifyHurtSounds;
    final ForgeConfigSpec.BooleanValue showFirstPersonGloves;
    final ForgeConfigSpec.BooleanValue showTooltips;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        modifyHurtSounds = builder
                .worldRestart()
                .comment("Whether the Kitty Slippers and Bunny Hoppers should modify the player's hurt sounds")
                .translation(Artifacts.MODID + ".config.client.modify_hurt_sounds")
                .define("modify_hurt_sounds", true);
        showFirstPersonGloves = builder
                .comment("Whether models for gloves should display on the player's hand in first person")
                .translation(Artifacts.MODID + ".config.client.show_first_person_gloves")
                .define("show_first_person_glove", true);
        showTooltips = builder
                .comment("Whether artifacts should have a tooltip explaining their effect. These can still be found in JEI when disabled")
                .translation(Artifacts.MODID + ".config.client.show_tooltips")
                .define("show_tooltips", true);
        builder.pop();
    }
}
