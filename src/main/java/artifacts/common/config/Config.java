package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Artifacts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec COMMON_SPEC;
    private static final CommonConfig COMMON;
    public static Set<ResourceLocation> biomeBlacklist;
    public static int campsiteChance;
    public static int campsiteMinY;
    public static int campsiteMaxY;
    public static double campsiteMimicChance;
    public static double campsiteOreChance;
    public static boolean useModdedChests;

    public static int everlastingFoodCooldown;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static void bakeCommon() {
        Config.biomeBlacklist = COMMON.biomeBlacklist.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        Config.campsiteChance = COMMON.campsiteChance.get();
        Config.campsiteMimicChance = COMMON.campsiteMimicChance.get() / 100D;
        Config.campsiteOreChance = COMMON.campsiteOreChance.get() / 100D;
        Config.campsiteMinY = COMMON.campsiteMinY.get();
        Config.campsiteMaxY = COMMON.campsiteMaxY.get();
        Config.useModdedChests = COMMON.useModdedChests.get();

        Config.everlastingFoodCooldown = COMMON.everlastingFoodCooldown.get();
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onModConfigEvent(ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == Config.COMMON_SPEC) {
            bakeCommon();
        }
    }
}
