package artifacts;

import artifacts.client.render.curio.CurioRenderers;
import artifacts.client.render.entity.MimicRenderer;
import artifacts.common.capability.killtracker.EntityKillTrackerCapability;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.config.ModConfig;
import artifacts.common.init.*;
import artifacts.common.network.NetworkHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(Artifacts.MODID)
public class Artifacts {

    public static final String MODID = "artifacts";

    public static final Logger LOGGER = LogManager.getLogger();

    public Artifacts() {
        ModConfig.registerCommon();
        ModConfig.registerClient();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.REGISTRY.register(modBus);
        ModSoundEvents.REGISTRY.register(modBus);
        ModFeatures.FEATURE_REGISTRY.register(modBus);
        ModFeatures.PLACEMENT_REGISTRY.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::enqueueIMC);

        modBus.addGenericListener(EntityType.class, ModEntities::register);
        modBus.addGenericListener(GlobalLootModifierSerializer.class, ModLootConditions::register);
        modBus.addListener(ModEntities::registerAttributes);

        MinecraftForge.EVENT_BUS.addListener(this::addFeatures);
    }

    public void addFeatures(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND && !ModConfig.common.isBlacklisted(event.getName())) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_STRUCTURES).add(() -> ModFeatures.UNDERGROUND_CAMPSITE);
        }
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        ModConfig.registerServer();
        event.enqueueWork(() -> {
            ModFeatures.registerConfiguredFeatures();
            NetworkHandler.register();
            EntityKillTrackerCapability.register();
            SwimHandlerCapability.register();
        });
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MIMIC, MimicRenderer::new);
        ItemModelsProperties.register(ModItems.UMBRELLA.get(), new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
        CurioRenderers.setupCurioRenderers();
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = {SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BELT};
        for (SlotTypePreset type : types) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("feet").priority(220).icon(PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS).build());
    }
}
