package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class ThornPendantConfig extends PendantConfig {

    public ForgeConfigSpec.IntValue minDamage;
    public ForgeConfigSpec.IntValue maxDamage;

    public ThornPendantConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.THORN_PENDANT.get());
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        super.addConfigs(builder);
        minDamage = builder
                .comment(
                        "Minimum damage dealt by thorn pendant",
                        "Damage dealt is a random number between min_damage and max_damage"
                )
                .translation(translate("min_damage"))
                .defineInRange("min_damage", 2, 0, Integer.MAX_VALUE);
        maxDamage = builder
                .comment("Maximum damage dealt by thorn pendant")
                .translation(translate("max_damage"))
                .defineInRange("max_damage", 6, 0, Integer.MAX_VALUE);
    }

    @Override
    protected double getDefaultStrikeChance() {
        return 0.5;
    }
}
