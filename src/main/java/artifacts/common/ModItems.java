package artifacts.common;

import artifacts.Artifacts;
import artifacts.common.item.*;
import baubles.api.BaubleType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static BaubleBase baubleRedShinyBalloon = new BaublePotionEffect("shiny_red_balloon", BaubleType.RING, MobEffects.JUMP_BOOST, 1).setEquipSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F);
    public static BaubleBase baubleSnorkel = new BaubleSnorkel();
    public static BaubleBase baubleShockPendant = new BaubleAmulet("shock_pendant", new ResourceLocation(Artifacts.MODID,"textures/entity/layer/shock_pendant.png"));
    public static BaubleBase baubleFlamePendant = new BaubleAmulet("flame_pendant", new ResourceLocation(Artifacts.MODID,"textures/entity/layer/flame_pendant.png"));
    public static BaubleBase baubleThornPendant = new BaubleAmulet("thorn_pendant", new ResourceLocation(Artifacts.MODID,"textures/entity/layer/thorn_pendant.png"));
    public static BaubleBase baubleLuckyHorseshoe = new BaubleBase("lucky_horseshoe", BaubleType.CHARM).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1);
    public static BaubleBase baubleCobaltShield = new BaubleBase("cobalt_shield", BaubleType.CHARM).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
    public static BaubleBase baubleObsidianSkull = new BaubleObsidianSkull();
    public static BaubleBase baublePanicNecklace = new BaublePanicNecklace();
    public static BaubleBase baubleWhoopieCushion = new BaubleWhoopieCushion();
    public static BaubleBase baubleBottledCloud = new BaubleBottledCloud("bottled_cloud", false);
    public static BaubleBase baubleBottledFart = new BaubleBottledCloud("bottled_fart", true);
    public static BaubleBase baubleMagmaRing = new BaubleBase("magma_ring", BaubleType.RING);


    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.registerAll(
                baubleRedShinyBalloon,
                baubleObsidianSkull,
                baubleSnorkel,
                baubleShockPendant,
                baubleFlamePendant,
                baubleThornPendant,
                baubleLuckyHorseshoe,
                baubleCobaltShield,
                baublePanicNecklace,
                baubleWhoopieCushion,
                baubleBottledCloud,
                baubleBottledFart,
                baubleMagmaRing
        );
    }

    public static void registerModels() {
        registerAllModels(
                baubleRedShinyBalloon,
                baubleObsidianSkull,
                baubleSnorkel,
                baubleShockPendant,
                baubleFlamePendant,
                baubleThornPendant,
                baubleLuckyHorseshoe,
                baubleCobaltShield,
                baublePanicNecklace,
                baubleWhoopieCushion,
                baubleBottledCloud,
                baubleBottledFart,
                baubleMagmaRing
        );
    }

    public static void registerAllModels(BaubleBase... items) {
        for (BaubleBase item : items) {
            item.registerModel();
        }
    }
}
