package artifacts;

import artifacts.common.capability.SwimHandler;
import artifacts.common.config.ModConfig;
import artifacts.common.init.*;
import artifacts.common.network.NetworkHandler;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(Artifacts.MODID)
public class Artifacts {

    public static final String MODID = "artifacts";

    public Artifacts() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArtifactsClient::new);

        ModConfig.registerCommon();
        SwimHandler.init();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modBus);
        modBus.addListener(ModItems::registerTab);
        ModEntityTypes.ENTITY_TYPES.register(modBus);
        ModSoundEvents.SOUND_EVENTS.register(modBus);
        ModFeatures.PLACEMENT_MODIFIERS.register(modBus);
        ModFeatures.FEATURES.register(modBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modBus);
        ModLootConditions.LOOT_CONDITIONS.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::enqueueIMC);

        modBus.addListener(ModEntityTypes::registerAttributes);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        ModConfig.registerServer();
        event.enqueueWork(NetworkHandler::register);
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = {SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BELT};
        for (SlotTypePreset type : types) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("feet").priority(220).icon(InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS).build());
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Artifacts.MODID, path);
    }

    public static ResourceLocation id(String path, String... args) {
        return new ResourceLocation(Artifacts.MODID, String.format(path, (Object[]) args));
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, id(path));
    }
}
