package artifacts.common.init;

import artifacts.common.item.DrinkingHatItem;
import artifacts.common.item.NightVisionGogglesItem;
import artifacts.common.item.PanicNecklaceItem;
import artifacts.common.item.SnorkelItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class Items {

    public static final Item PLASTIC_DRINKING_HAT = new DrinkingHatItem("plastic_drinking_hat", false);
    public static final Item NOVELTY_DRINKING_HAT = new DrinkingHatItem("novelty_drinking_hat", true);
    public static final Item SNORKEL = new SnorkelItem();
    public static final Item NIGHT_VISION_GOGGLES = new NightVisionGogglesItem();
    public static final Item PANIC_NECKLACE = new PanicNecklaceItem();

    public static void registerAll(IForgeRegistry<Item> registry) {
        registry.registerAll(
                PLASTIC_DRINKING_HAT,
                NOVELTY_DRINKING_HAT,
                SNORKEL,
                NIGHT_VISION_GOGGLES,
                PANIC_NECKLACE
        );
    }
}
