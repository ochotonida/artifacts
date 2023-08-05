package artifacts.forge;

import artifacts.Artifacts;
import artifacts.ArtifactsClient;
import artifacts.config.ModConfig;
import artifacts.forge.capability.SwimDataCapability;
import artifacts.forge.curio.WearableArtifactCurio;
import artifacts.forge.event.ArtifactEventsForge;
import artifacts.forge.event.SwimEventsForge;
import artifacts.forge.registry.ModLootModifiers;
import artifacts.item.wearable.WearableArtifactItem;
import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.common.capability.CurioItemCapability;

@Mod(Artifacts.MOD_ID)
public class ArtifactsForge {

    public ArtifactsForge() {
        EventBuses.registerModEventBus(Artifacts.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        Artifacts.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArtifactsClient::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArtifactsForgeClient::new);

        registerConfig();

        SwimDataCapability.setup();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLootModifiers.LOOT_MODIFIERS.register(modBus);

        ArtifactEventsForge.register();
        SwimEventsForge.register();

        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::onAttachCapabilities);
    }

    private void registerConfig() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get()
                )
        );
    }

    private void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof WearableArtifactItem item) {
            event.addCapability(CuriosCapability.ID_ITEM, CurioItemCapability.createProvider(new WearableArtifactCurio(item, event.getObject())));
        }
    }
}
