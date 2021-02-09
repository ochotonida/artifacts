package artifacts;

import artifacts.client.render.MimicRenderer;
import artifacts.common.config.Config;
import artifacts.common.init.ModEntities;
import artifacts.common.init.ModFeatures;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.network.NetworkHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(Artifacts.MODID)
@SuppressWarnings("unused")
public class Artifacts {

    public static final String MODID = "artifacts";

    public Artifacts() {
        Config.register();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.REGISTRY.register(modEventBus);
        ModSoundEvents.REGISTRY.register(modEventBus);
        ModFeatures.FEATURE_REGISTRY.register(modEventBus);
        ModFeatures.PLACEMENT_REGISTRY.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(this::addFeatures);
    }

    public void addFeatures(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND && !Config.isBlacklisted(event.getName())) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_STRUCTURES).add(() -> ModFeatures.UNDERGROUND_CAMPSITE);
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                ModFeatures.registerConfiguredFeatures();
                NetworkHandler.register();
            });
        }

        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.MIMIC, MimicRenderer::new);
            ItemModelsProperties.registerProperty(ModItems.UMBRELLA.get(), new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1 : 0);
        }

        @SubscribeEvent
        public static void enqueueIMC(final InterModEnqueueEvent event) {
            SlotTypePreset[] types = {SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BELT};
            for (SlotTypePreset type : types) {
                InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
            }
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("feet").priority(220).icon(PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS).build());
        }

        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            ModEntities.register(event.getRegistry());
        }
    }
}
