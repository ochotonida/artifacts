package artifacts.common.config;

import artifacts.Artifacts;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Artifacts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec COMMON_SPEC;
    private static final CommonConfig COMMON;

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ServerConfig SERVER;

    public static final ForgeConfigSpec CLIENT_SPEC;
    private static final ClientConfig CLIENT;

    static {
        Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
        Pair<ServerConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER = serverSpecPair.getLeft();
        SERVER_SPEC = serverSpecPair.getRight();
        Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    private static Set<ResourceLocation> worldGenBiomeBlacklist;
    private static Set<String> worldGenModIdBlacklist;
    public static int campsiteChance;
    public static int campsiteMinY;
    public static int campsiteMaxY;
    public static double campsiteMimicChance;
    public static double campsiteOreChance;
    public static boolean useModdedChests;

    public static Set<Item> cosmetics = Collections.emptySet();
    public static int everlastingFoodCooldown;

    public static boolean modifyHurtSounds;
    public static boolean showFirstPersonGloves;
    public static boolean showTooltips;

    public static boolean isBlacklisted(@Nullable ResourceLocation biome) {
        return biome != null && (worldGenBiomeBlacklist.contains(biome) || worldGenModIdBlacklist.contains(biome.getNamespace()));
    }

    public static boolean isCosmetic(Item item) {
        return cosmetics.contains(item);
    }

    public static void bakeCommon() {
        worldGenBiomeBlacklist = COMMON.biomeBlacklist.get()
                .stream()
                .filter(string -> !string.endsWith(":*"))
                .map(ResourceLocation::new)
                .collect(Collectors.toSet());
        worldGenModIdBlacklist = COMMON.biomeBlacklist.get()
                .stream()
                .filter(string -> string.endsWith(":*"))
                .map(string -> string.substring(0, string.length() - 2))
                .collect(Collectors.toSet());
        campsiteChance = COMMON.campsiteChance.get();
        campsiteMimicChance = COMMON.campsiteMimicChance.get() / 100D;
        campsiteOreChance = COMMON.campsiteOreChance.get() / 100D;
        campsiteMinY = COMMON.campsiteMinY.get();
        campsiteMaxY = COMMON.campsiteMaxY.get();
        useModdedChests = COMMON.useModdedChests.get();
    }

    public static void bakeServer() {
        if (SERVER.cosmetics.get().contains("artifacts:*")) {
            // noinspection ConstantConditions
            cosmetics = ForgeRegistries.ITEMS.getValues()
                    .stream()
                    .filter(item -> item.getRegistryName().getNamespace().equals(Artifacts.MODID))
                    .collect(Collectors.toSet());
        } else {
            cosmetics = SERVER.cosmetics.get()
                    .stream()
                    .map(ResourceLocation::new)
                    .filter(registryName -> registryName.getNamespace().equals(Artifacts.MODID))
                    .map(ForgeRegistries.ITEMS::getValue)
                    .collect(Collectors.toSet());
        }
        everlastingFoodCooldown = SERVER.everlastingFoodCooldown.get();
    }

    public static void bakeClient() {
        modifyHurtSounds = CLIENT.modifyHurtSounds.get();
        showFirstPersonGloves = CLIENT.showFirstPersonGloves.get();
        showTooltips = CLIENT.showTooltips.get();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == COMMON_SPEC) {
            bakeCommon();
        } else if (configEvent.getConfig().getSpec() == SERVER_SPEC) {
            bakeServer();
        } else if (configEvent.getConfig().getSpec() == CLIENT_SPEC) {
            bakeClient();
        }
    }
}
