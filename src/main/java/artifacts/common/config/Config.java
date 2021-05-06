package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Artifacts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec COMMON_SPEC;
    private static final CommonConfig COMMON;
    private static Set<ResourceLocation> worldGenBiomeBlacklist;
    private static Set<String> worldGenModIdBlacklist;
    public static Double campsiteChance;
    public static Double campsiteMimicChance;
    public static Double campsiteOreChance;
    public static int campsiteMinY;
    public static int campsiteMaxY;

    public static int everlastingFoodCooldown;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static boolean isBlacklisted(@Nullable ResourceLocation biome) {
        return biome != null && (worldGenBiomeBlacklist.contains(biome) || worldGenModIdBlacklist.contains(biome.getNamespace()));
    }

    public static void bakeCommon() {
        Config.worldGenBiomeBlacklist = COMMON.biomeBlacklist.get()
                .stream()
                .filter(string -> !string.endsWith(":*"))
                .map(ResourceLocation::new)
                .collect(Collectors.toSet());
        Config.worldGenModIdBlacklist = COMMON.biomeBlacklist.get()
                .stream()
                .filter(string -> string.endsWith(":*"))
                .map(string -> string.substring(0, string.length() - 2))
                .collect(Collectors.toSet());
        Config.campsiteChance = COMMON.campsiteChance.get();
        Config.campsiteMimicChance = COMMON.campsiteMimicChance.get();
        Config.campsiteOreChance = COMMON.campsiteOreChance.get();
        Config.campsiteMinY = COMMON.campsiteMinY.get();
        Config.campsiteMaxY = COMMON.campsiteMaxY.get();

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
