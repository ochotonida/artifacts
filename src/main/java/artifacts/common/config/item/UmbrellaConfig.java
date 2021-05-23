package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class UmbrellaConfig extends ItemConfig {

    public ForgeConfigSpec.BooleanValue isShield;

    public UmbrellaConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.UMBRELLA.getId().getPath(), "Affects how many attacks can be blocked using the umbrella");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        isShield = builder
                .comment("Whether umbrellas can be used as a shield")
                .translation(translate("is_shield"))
                .define("is_shield", true);
    }
}
