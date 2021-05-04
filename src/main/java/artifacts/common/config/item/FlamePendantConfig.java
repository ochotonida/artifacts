package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public class FlamePendantConfig extends ItemConfig {

    public double strikeChance;
    public int fireDuration;

    private ForgeConfigSpec.DoubleValue strikeChanceValue;
    private ForgeConfigSpec.IntValue fireDurationValue;

    public FlamePendantConfig(ForgeConfigSpec.Builder builder) {
        super(builder, "flame_pendant");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        strikeChanceValue = builder
                .comment("Chance for the pendant to strike an attacker when the wearer is attacked")
                .translation(Artifacts.MODID + ".config.server.pendant.strike_chance")
                .defineInRange("strike_chance", 0.4, 0, 1);
        fireDurationValue = builder
                .comment("Duration (equal to total fire damage) for which entities are set on fire")
                .translation(translate("fire_duration"))
                .defineInRange("fire_duration", 10, 1, Integer.MAX_VALUE);
    }

    @Override
    public void bake() {
        strikeChance = strikeChanceValue.get();
        fireDuration = fireDurationValue.get();
    }
}
