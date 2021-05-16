package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public final ForgeConfigSpec.BooleanValue modifyHurtSounds;
    public final ForgeConfigSpec.BooleanValue showFirstPersonGloves;
    public final ForgeConfigSpec.BooleanValue showTooltips;
    public final ForgeConfigSpec.BooleanValue useModdedMimicTextures;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        modifyHurtSounds = builder
                .worldRestart()
                .comment("Whether the Kitty Slippers and Bunny Hoppers should modify the player's hurt sounds")
                .translation(Artifacts.MODID + ".config.client.items.modify_hurt_sounds")
                .define("modify_hurt_sounds", true);
        showFirstPersonGloves = builder
                .comment("Whether models for gloves should display on the player's hand in first person")
                .translation(Artifacts.MODID + ".config.client.items.show_first_person_gloves")
                .define("show_first_person_glove", true);
        showTooltips = builder
                .comment("Whether artifacts should have a tooltip explaining their effect. These can still be found in JEI when disabled")
                .translation(Artifacts.MODID + ".config.client.items.show_tooltips")
                .define("show_tooltips", true);
        builder.pop();
        builder.push("Mimics");

        useModdedMimicTextures = builder
                .comment("Whether mimics should use chest textures from Lootr or Quark if these are installed")
                .translation(Artifacts.MODID + ".config.client.mimics.use_modded_textures")
                .define("use_modded_textures", true);
        builder.pop();
    }
}
