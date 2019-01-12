package artifacts;

import artifacts.common.item.PotionEffectBauble;
import artifacts.common.item.BaubleBase;
import baubles.api.BaubleType;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ArtifactsItems {

    public static BaubleBase baubleBalloon = new PotionEffectBauble("bauble_balloon", BaubleType.RING, MobEffects.JUMP_BOOST, 1);
    public static BaubleBase baubleRebreather = new PotionEffectBauble("bauble_rebreather", BaubleType.HEAD, MobEffects.WATER_BREATHING, 0);

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.registerAll(
                baubleBalloon,
                baubleRebreather
        );
    }

    public static void registerModels() {
        Artifacts.proxy.registerItemRenderer(baubleBalloon, 0, baubleBalloon.name);
        Artifacts.proxy.registerItemRenderer(baubleRebreather, 0, baubleRebreather.name);

    }
}
