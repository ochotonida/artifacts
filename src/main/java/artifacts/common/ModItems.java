package artifacts.common;

import artifacts.Artifacts;
import artifacts.common.item.*;
import baubles.api.BaubleType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
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
    public static BaubleBase baubleMagmaStone = new BaubleBase("magma_stone", BaubleType.RING);
    public static ItemEverlastingFood itemEverlastingPorkchop = new ItemEverlastingFood("everlasting_porkchop", 3, 0.3F);
    public static ItemEverlastingFood itemEverlastingCookedPorkchop = new ItemEverlastingFood("everlasting_cooked_porkchop", 8, 0.8F);
    public static ItemEverlastingFood itemEverlastingBeef = new ItemEverlastingFood("everlasting_beef", 3, 0.3F);
    public static ItemEverlastingFood itemEverlastingCookedBeef = new ItemEverlastingFood("everlasting_cooked_beef", 8, 0.8F);
    public static ItemEverlastingFood itemEverlastingChicken = (ItemEverlastingFood) new ItemEverlastingFood("everlasting_chicken", 2, 0.3F).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F);
    public static ItemEverlastingFood itemEverlastingCookedChicken = new ItemEverlastingFood("everlasting_cooked_chicken", 6, 0.6F);
    public static ItemEverlastingFood itemEverlastingRottenFlesh = (ItemEverlastingFood) new ItemEverlastingFood("everlasting_rotten_flesh", 4, 0.1F).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.8F);
    public static ItemEverlastingFood itemEverlastingSpiderEye = (ItemEverlastingFood) new ItemEverlastingFood("everlasting_spider_eye", 2, 0.8F).setPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0), 1);
    public static ItemEverlastingFood itemEverlastingRabbit = new ItemEverlastingFood("everlasting_rabbit", 3, 0.3F);
    public static ItemEverlastingFood itemEverlastingCookedRabbit = new ItemEverlastingFood("everlasting_cooked_rabbit", 5, 0.6F);
    public static ItemEverlastingFood itemEverlastingMutton = new ItemEverlastingFood("everlasting_mutton", 2, 0.3F);
    public static ItemEverlastingFood itemEverlastingCookedMutton = new ItemEverlastingFood("everlasting_cooked_mutton", 6, 0.8F);
    public static ItemEverlastingFood itemEverlastingRabbitStew = new ItemEverlastingFood("everlasting_rabbit_stew", 10, 0.6F);
    public static ItemEverlastingFood itemEverlastingCod = new ItemEverlastingFood("everlasting_cod", 2, 0.1F);
    public static ItemEverlastingFood itemEverlastingCookedCod = new ItemEverlastingFood("everlasting_cooked_cod", 5, 0.6F);
    public static ItemEverlastingFood itemEverlastingSalmon = new ItemEverlastingFood("everlasting_salmon", 2, 0.1F);
    public static ItemEverlastingFood itemEverlastingCookedSalmon = new ItemEverlastingFood("everlasting_cooked_salmon", 6, 0.8F);
    public static ItemEverlastingFood itemEverlastingClownfish = new ItemEverlastingFood("everlasting_clownfish", 1, 0.1F);

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
                baubleMagmaStone,

                itemEverlastingPorkchop,
                itemEverlastingCookedPorkchop,
                itemEverlastingBeef,
                itemEverlastingCookedBeef,
                itemEverlastingChicken,
                itemEverlastingCookedChicken,
                itemEverlastingRottenFlesh,
                itemEverlastingSpiderEye,
                itemEverlastingRabbit,
                itemEverlastingCookedRabbit,
                itemEverlastingMutton,
                itemEverlastingCookedMutton,
                itemEverlastingRabbitStew,
                itemEverlastingCod,
                itemEverlastingCookedCod,
                itemEverlastingSalmon,
                itemEverlastingCookedSalmon,
                itemEverlastingClownfish
        );
    }

    public static void registerModels() {
        baubleRedShinyBalloon.registerModel();
        baubleObsidianSkull.registerModel();
        baubleSnorkel.registerModel();
        baubleShockPendant.registerModel();
        baubleFlamePendant.registerModel();
        baubleThornPendant.registerModel();
        baubleLuckyHorseshoe.registerModel();
        baubleCobaltShield.registerModel();
        baublePanicNecklace.registerModel();
        baubleWhoopieCushion.registerModel();
        baubleBottledCloud.registerModel();
        baubleBottledFart.registerModel();
        baubleMagmaStone.registerModel();

        itemEverlastingPorkchop.registerModel();
        itemEverlastingCookedPorkchop.registerModel();
        itemEverlastingBeef.registerModel();
        itemEverlastingCookedBeef.registerModel();
        itemEverlastingChicken.registerModel();
        itemEverlastingCookedChicken.registerModel();
        itemEverlastingRottenFlesh.registerModel();
        itemEverlastingSpiderEye.registerModel();
        itemEverlastingRabbit.registerModel();
        itemEverlastingCookedRabbit.registerModel();
        itemEverlastingMutton.registerModel();
        itemEverlastingCookedMutton.registerModel();
        itemEverlastingRabbitStew.registerModel();
        itemEverlastingCod.registerModel();
        itemEverlastingCookedCod.registerModel();
        itemEverlastingSalmon.registerModel();
        itemEverlastingCookedSalmon.registerModel();
        itemEverlastingClownfish.registerModel();
    }
}
