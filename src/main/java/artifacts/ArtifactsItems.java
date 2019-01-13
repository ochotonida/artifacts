package artifacts;

import artifacts.common.item.BaubleLightningCloak;
import artifacts.common.item.PotionEffectBauble;
import artifacts.common.item.BaubleBase;
import baubles.api.BaubleType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ArtifactsItems {

    public static BaubleBase baubleBalloon = new PotionEffectBauble("bauble_balloon", BaubleType.RING, MobEffects.JUMP_BOOST, 1).setEquipSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F);
    public static BaubleBase baubleSnorkel = new PotionEffectBauble("bauble_snorkel", BaubleType.HEAD, MobEffects.WATER_BREATHING, 0).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1);
    public static BaubleBase baubleLightningCloak = new BaubleLightningCloak();

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.registerAll(
                baubleBalloon,
                baubleSnorkel,
                baubleLightningCloak
        );
    }

    public static void registerModels() {
        Artifacts.proxy.registerItemRenderer(baubleBalloon, 0, baubleBalloon.name);
        Artifacts.proxy.registerItemRenderer(baubleSnorkel, 0, baubleSnorkel.name);
        Artifacts.proxy.registerItemRenderer(baubleLightningCloak, 0, baubleLightningCloak.name);
    }
}
