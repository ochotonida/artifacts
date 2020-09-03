package artifacts;

import artifacts.common.IProxy;
import artifacts.common.init.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static artifacts.Artifacts.*;

@SuppressWarnings("unused")
@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = "required-after:baubles;after:artemislib", updateJSON = "https://github.com/ochotonida/artifacts/blob/master/update.json")
public class Artifacts {

    public static final String MODID = "artifacts";
    public static final String MODNAME = "Artifacts";
    public static final String VERSION = "1.12.2-1.2.2";

    public static final CreativeTab CREATIVE_TAB = new CreativeTab();

    @Mod.Instance(MODID)
    public static Artifacts instance;

    @SidedProxy(serverSide = "artifacts.common.ServerProxy", clientSide = "artifacts.client.ClientProxy")
    public static IProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        ModCompat.preInit();
        ModEntities.init();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        ModNetworkHandler.init();
        ModRecipes.init();
        ModLootTables.init();
        ModWorldGen.init();
        ModCompat.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
            ModSoundEvents.registerSoundEvents(event.getRegistry());
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

    public static class CreativeTab extends CreativeTabs {

        public CreativeTab() {
            super(MODID + ".creativetab");
        }

        @Override
        @Nonnull
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.PANIC_NECKLACE);
        }
    }
}
