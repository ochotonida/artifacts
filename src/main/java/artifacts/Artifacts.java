package artifacts;

import artifacts.common.CommonProxy;
import artifacts.common.ModItems;
import artifacts.common.ModRecipes;
import artifacts.common.ModSoundEvents;
import artifacts.common.compat.JustEnoughResources;
import artifacts.common.loot.functions.AddRandomEffect;
import artifacts.common.loot.functions.GenerateEverlastingFish;
import artifacts.common.worldgen.WorldGenOceanShrine;
import artifacts.common.worldgen.WorldGenUndergroundChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static artifacts.Artifacts.*;

@SuppressWarnings("unused")
@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = "required-after:baubles", updateJSON = "https://github.com/ochotonida/artifacts/blob/master/update.json")
public class Artifacts {

    public static final String MODID = "artifacts";
    public static final String MODNAME = "Artifacts";
    public static final String VERSION = "1.12.2-1.0.1";

    public static final CreativeTab CREATIVE_TAB = new CreativeTab();

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
        GameRegistry.registerWorldGenerator(new WorldGenUndergroundChest(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenOceanShrine(), 0);
        ModRecipes.initRecipes();
        LootFunctionManager.registerFunction(new GenerateEverlastingFish.Serializer());
        LootFunctionManager.registerFunction(new AddRandomEffect.Serializer());
        JustEnoughResources.init();
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().register(ModSoundEvents.FART);
            event.getRegistry().register(ModSoundEvents.MIMIC_CLOSE);
            event.getRegistry().register(ModSoundEvents.MIMIC_OPEN);
            event.getRegistry().register(ModSoundEvents.MIMIC_HURT);
            event.getRegistry().register(ModSoundEvents.MIMIC_DEATH);
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
