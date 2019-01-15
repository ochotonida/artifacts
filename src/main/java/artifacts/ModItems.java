package artifacts;

import artifacts.common.item.BaubleLightningAmulet;
import artifacts.common.item.BaublePotionEffect;
import artifacts.common.item.BaubleBase;
import artifacts.common.item.BaubleSnorkel;
import baubles.api.BaubleType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static BaubleBase baubleBalloon = new BaublePotionEffect("bauble_balloon", BaubleType.RING, MobEffects.JUMP_BOOST, 1).setEquipSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F);
    public static BaubleBase baubleSnorkel = new BaubleSnorkel();
    public static BaubleBase baubleLightningAmulet = new BaubleLightningAmulet();

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.registerAll(
                baubleBalloon,
                baubleSnorkel,
                baubleLightningAmulet
        );
    }

    public static void registerModels() {
        Artifacts.proxy.registerItemRenderer(baubleBalloon, 0, baubleBalloon.name);
        Artifacts.proxy.registerItemRenderer(baubleSnorkel, 0, baubleSnorkel.name);
        Artifacts.proxy.registerItemRenderer(baubleLightningAmulet, 0, baubleLightningAmulet.name);
    }
}
