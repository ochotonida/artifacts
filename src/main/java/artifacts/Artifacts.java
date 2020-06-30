package artifacts;

import artifacts.common.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("artifacts")
@SuppressWarnings("unused")
public class Artifacts {

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onItemsRegistry(RegistryEvent.Register<Item> event) {
            Items.registerAll(event.getRegistry());
        }
    }
}
