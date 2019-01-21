package artifacts;

import artifacts.common.CommonProxy;
import artifacts.common.ModItems;
import artifacts.common.item.BaubleWhoopieCushion;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static artifacts.Artifacts.MODID;
import static artifacts.Artifacts.MODNAME;
import static artifacts.Artifacts.VERSION;

@SuppressWarnings("unused")
@Mod(modid = MODID, name = MODNAME, version = VERSION, updateJSON = "https://github.com/ochotonida/artifacts/blob/master/update.json")
public class Artifacts {

    public static final String MODID = "artifacts";
    public static final String MODNAME = "Artifacts";
    public static final String VERSION = "1.12.2-0.0.0";

    @Mod.Instance(MODID)
    public static Artifacts instance;

    @SidedProxy(serverSide = "artifacts.common.CommonProxy", clientSide = "artifacts.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().register(BaubleWhoopieCushion.FART);
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.registerItems(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItemModels(ModelRegistryEvent event) {
            ModItems.registerModels();
        }
    }
}
