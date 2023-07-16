package artifacts.forge;

import artifacts.forge.capability.SwimHandler;
import artifacts.forge.config.ModConfig;
import artifacts.forge.data.DataGenerators;
import artifacts.forge.network.NetworkHandler;
import artifacts.forge.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ArtifactsForge.MOD_ID)
public class ArtifactsForge {

    public static final String MOD_ID = "artifacts";
    public static ModConfig CONFIG;

    public ArtifactsForge() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArtifactsForgeClient::new);

        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get()));
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        SwimHandler.setup();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.CREATIVE_MODE_TABS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModEntityTypes.ENTITY_TYPES.register(modBus);
        ModSoundEvents.SOUND_EVENTS.register(modBus);
        ModPlacementModifierTypes.PLACEMENT_MODIFIERS.register(modBus);
        ModFeatures.FEATURES.register(modBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modBus);
        ModLootConditions.LOOT_CONDITIONS.register(modBus);

        modBus.addListener(this::commonSetup);

        modBus.addListener(DataGenerators::gatherData);
        modBus.addListener(ModEntityTypes::registerAttributes);

        MinecraftForge.EVENT_BUS.addListener(ModGameRules::onPlayerJoinWorld);
        MinecraftForge.EVENT_BUS.addListener(ModGameRules::onServerStarted);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ArtifactsForge.MOD_ID, path);
    }

    public static ResourceLocation id(String path, String... args) {
        return new ResourceLocation(ArtifactsForge.MOD_ID, String.format(path, (Object[]) args));
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, id(path));
    }
}
