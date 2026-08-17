package artifacts;

import artifacts.config.ConfigManager;
import artifacts.config.ModConfig;
import artifacts.equipment.EquipmentSlotManager;
import artifacts.integration.ModCompat;
import artifacts.integration.accessories.AccessoriesCompat;
import artifacts.integration.minecraft.ArmorSlotProvider;
import artifacts.integration.trinkets.TrinketsCompat;
import artifacts.network.NetworkHandler;
import artifacts.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class Artifacts {

    public static final String MOD_ID = "artifacts";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ModConfig CONFIG;

    @Nullable
    private static MinecraftServer currentServer;

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation id(String path, String... args) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, String.format(path, (Object[]) args));
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, id(path));
    }

    public static @Nullable MinecraftServer getCurrentServer() {
        return currentServer;
    }

    public static void setup() {
        initConfigs();
        setupIntegrations();
        if (ModCompat.TRINKETS.isLoaded()) {
            TrinketsCompat.setup();
        }
        if (ModCompat.ACCESSORIES.isLoaded()) {
            AccessoriesCompat.setup();
        }

        NetworkHandler.initPayloads();

        ModMobEffects.MOB_EFFECTS.register();
        ModDataComponents.DATA_COMPONENT_TYPES.register();
        ModSoundEvents.SOUND_EVENTS.register();
        ModLootConditions.LOOT_CONDITION_TYPES.register();
        ModLootFunctions.LOOT_FUNCTION_TYPES.register();
        ModPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register();
        ModAttributes.ATTRIBUTES.register();
        ModEntityTypes.ENTITY_TYPES.register();
        ModItems.ITEMS.register();
        ModItems.CREATIVE_MODE_TABS.register();
        ModFeatures.FEATURES.register();
        ModGameEvents.GAME_EVENTS.register();
    }

    public static void initConfigs() {
        CONFIG = new ModConfig();
        CONFIG.setup();
        for (ConfigManager config : CONFIG.configs) {
            config.readValuesFromConfig();
        }
    }

    public static void setupIntegrations() {
        EquipmentSlotManager.register(new ArmorSlotProvider());
    }

    public static void onServerStarting(MinecraftServer server) {
        currentServer = server;
        for (ConfigManager config : CONFIG.configs) {
            config.readValuesFromConfig();
        }
    }

    public static void onServerStopping() {
        currentServer = null;
    }

    public static void onPlayerJoin(ServerPlayer player) {
        CONFIG.items.sendToClient(player);
    }
}
