package artifacts.forge.registry;

import artifacts.forge.item.UmbrellaItem;
import artifacts.forge.item.wearable.belt.CloudInABottleItem;
import artifacts.forge.item.wearable.belt.HeliumFlamingoItem;
import artifacts.forge.item.wearable.feet.AquaDashersItem;
import artifacts.forge.item.wearable.feet.BunnyHoppersItem;
import artifacts.forge.item.wearable.feet.FlippersItem;
import artifacts.forge.item.wearable.feet.KittySlippersItem;
import artifacts.forge.item.wearable.hands.DiggingClawsItem;
import artifacts.forge.item.wearable.hands.GoldenHookItem;
import artifacts.forge.item.wearable.hands.VampiricGloveItem;
import artifacts.forge.item.wearable.head.DrinkingHatItem;
import artifacts.forge.item.wearable.necklace.CharmOfSinkingItem;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class ModItemsForge {

    public static void registerItems() {
        ModItems.MIMIC_SPAWN_EGG = ModItems.ITEMS.register("mimic_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.MIMIC, 0x805113, 0x212121, new Item.Properties())); // TODO add to creative tab
        ModItems.UMBRELLA = ModItems.ITEMS.register("umbrella", UmbrellaItem::new);

        // head
        ModItems.PLASTIC_DRINKING_HAT = ModItems.ITEMS.register("plastic_drinking_hat", () -> new DrinkingHatItem(ModGameRules.PLASTIC_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER, ModGameRules.PLASTIC_DRINKING_HAT_EATING_DURATION_MULTIPLIER, false));
        ModItems.NOVELTY_DRINKING_HAT = ModItems.ITEMS.register("novelty_drinking_hat", () -> new DrinkingHatItem(ModGameRules.NOVELTY_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER, ModGameRules.NOVELTY_DRINKING_HAT_EATING_DURATION_MULTIPLIER, true));

        ModItems.CHARM_OF_SINKING = ModItems.ITEMS.register("charm_of_sinking", CharmOfSinkingItem::new);

        ModItems.CLOUD_IN_A_BOTTLE = ModItems.ITEMS.register("cloud_in_a_bottle", CloudInABottleItem::new);
        ModItems.HELIUM_FLAMINGO = ModItems.ITEMS.register("helium_flamingo", HeliumFlamingoItem::new);

        ModItems.DIGGING_CLAWS = ModItems.ITEMS.register("digging_claws", DiggingClawsItem::new);
        ModItems.VAMPIRIC_GLOVE = ModItems.ITEMS.register("vampiric_glove", VampiricGloveItem::new);
        ModItems.GOLDEN_HOOK = ModItems.ITEMS.register("golden_hook", GoldenHookItem::new);

        ModItems.AQUA_DASHERS = ModItems.ITEMS.register("aqua_dashers", AquaDashersItem::new);
        ModItems.BUNNY_HOPPERS = ModItems.ITEMS.register("bunny_hoppers", BunnyHoppersItem::new);
        ModItems.KITTY_SLIPPERS = ModItems.ITEMS.register("kitty_slippers", KittySlippersItem::new);
        ModItems.FLIPPERS = ModItems.ITEMS.register("flippers", FlippersItem::new);
    }
}
