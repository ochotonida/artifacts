package artifacts;

import artifacts.common.capability.SwimHandler;
import artifacts.common.config.ModConfig;
import artifacts.common.init.*;
import artifacts.common.network.NetworkHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
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
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArtifactsClient::new);

        ModConfig.registerCommon();
        SwimHandler.init();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.REGISTRY.register(modBus);
        ModSoundEvents.REGISTRY.register(modBus);
        // TODO ModFeatures.FEATURE_REGISTRY.register(modBus);
        // ModFeatures.PLACEMENT_REGISTRY.register(modBus);
        ModLootModifiers.REGISTRY.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::enqueueIMC);

        modBus.addGenericListener(EntityType.class, ModEntityTypes::register);
        modBus.addGenericListener(GlobalLootModifierSerializer.class, ModLootConditions::register);
        modBus.addListener(ModEntityTypes::registerAttributes);

        // MinecraftForge.EVENT_BUS.addListener(this::addFeatures);
    }
    /* TODO
    public void addFeatures(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.THEEND && !ModConfig.common.isBlacklisted(event.getName())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_STRUCTURES).add(() -> ModFeatures.UNDERGROUND_CAMPSITE);
        }
    }*/

    public void commonSetup(final FMLCommonSetupEvent event) {
        ModConfig.registerServer();
        event.enqueueWork(() -> {
            // TODO ModFeatures.registerConfiguredFeatures();
            NetworkHandler.register();
        });
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = {SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BELT};
        for (SlotTypePreset type : types) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("feet").priority(220).icon(InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS).build());
    }
}
