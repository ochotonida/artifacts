package artifacts.common.config.item;

import artifacts.common.init.ModItems;
import net.minecraftforge.common.ForgeConfigSpec;

public class VillagerHatConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue heroOfTheVillageLevel;

    public VillagerHatConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ModItems.VILLAGER_HAT.get(), "Affects how many trades can be completed using the villager hat");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        heroOfTheVillageLevel = builder
                .comment("The level of the hero of the village effect applied by the villager hat")
                .translation(translate("hero_of_the_village_level"))
                .defineInRange("hero_of_the_village_level", 2, 0, 128);
    }
}
