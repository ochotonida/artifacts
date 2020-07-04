package artifacts.common.init;

import artifacts.common.item.*;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class Items {

    public static final Item PLASTIC_DRINKING_HAT = new DrinkingHatItem("plastic_drinking_hat", false);
    public static final Item NOVELTY_DRINKING_HAT = new DrinkingHatItem("novelty_drinking_hat", true);
    public static final Item SNORKEL = new SnorkelItem();
    public static final Item NIGHT_VISION_GOGGLES = new NightVisionGogglesItem();
    public static final Item PANIC_NECKLACE = new PanicNecklaceItem();
    public static final Item SHOCK_PENDANT = new PendantItem("shock_pendant");
    public static final Item FLAME_PENDANT = new PendantItem("flame_pendant");
    public static final Item THORN_PENDANT = new PendantItem("thorn_pendant");
    public static final Item FLIPPERS = new FlippersItem();

    public static void registerAll(IForgeRegistry<Item> registry) {
        registry.registerAll(
                PLASTIC_DRINKING_HAT,
                NOVELTY_DRINKING_HAT,
                SNORKEL,
                NIGHT_VISION_GOGGLES,
                PANIC_NECKLACE,
                SHOCK_PENDANT,
                FLAME_PENDANT,
                THORN_PENDANT,
                FLIPPERS
        );
    }
}
