package artifacts.common.init;

import artifacts.common.item.SnorkelItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class Items {

    public static final Item SNORKEL = new SnorkelItem();

    public static void registerAll(IForgeRegistry<Item> registry) {
        registry.registerAll(
                SNORKEL
        );
    }
}
