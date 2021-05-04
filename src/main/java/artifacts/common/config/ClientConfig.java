package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public boolean modifyHurtSounds;
    public boolean showFirstPersonGloves;
    public boolean showTooltips;

    private final ForgeConfigSpec.BooleanValue modifyHurtSoundsValue;
    private final ForgeConfigSpec.BooleanValue showFirstPersonGlovesValue;
    private final ForgeConfigSpec.BooleanValue showTooltipsValue;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        modifyHurtSoundsValue = builder
                .worldRestart()
                .comment("Whether the Kitty Slippers and Bunny Hoppers should modify the player's hurt sounds")
                .translation(Artifacts.MODID + ".config.client.modify_hurt_sounds")
                .define("modify_hurt_sounds", true);
        showFirstPersonGlovesValue = builder
                .comment("Whether models for gloves should display on the player's hand in first person")
                .translation(Artifacts.MODID + ".config.client.show_first_person_gloves")
                .define("show_first_person_glove", true);
        showTooltipsValue = builder
                .comment("Whether artifacts should have a tooltip explaining their effect. These can still be found in JEI when disabled")
                .translation(Artifacts.MODID + ".config.client.show_tooltips")
                .define("show_tooltips", true);
        builder.pop();
    }

    void bake() {
        modifyHurtSounds = modifyHurtSoundsValue.get();
        showFirstPersonGloves = showFirstPersonGlovesValue.get();
        showTooltips = showTooltipsValue.get();
    }
}
