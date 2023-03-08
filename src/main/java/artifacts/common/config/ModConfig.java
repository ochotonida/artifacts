package artifacts.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {

    public static CommonConfig common;
    public static ClientConfig client;

    public static ForgeConfigSpec serverSpec;

    public static void registerCommon() {
        Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        ForgeConfigSpec commonSpec = commonSpecPair.getRight();
        common = commonSpecPair.getLeft();
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, commonSpec);
    }

    public static void registerClient() {
        Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        ForgeConfigSpec clientSpec = clientSpecPair.getRight();
        client = clientSpecPair.getLeft();
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, clientSpec);
    }
}
