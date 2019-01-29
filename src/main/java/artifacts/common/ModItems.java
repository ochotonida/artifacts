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

    public static final BaubleBase SHINY_RED_BALLOON = new BaublePotionEffect("shiny_red_balloon", BaubleType.CHARM, MobEffects.JUMP_BOOST, 1).setEquipSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F);
    public static final BaubleBase SNORKEL = new BaubleSnorkel();
    public static final BaubleBase SHOCK_PENDANT = new BaubleAmulet("shock_pendant", new ResourceLocation(Artifacts.MODID,"textures/entity/layer/shock_pendant.png"));
    public static final BaubleBase FLAME_PENDANT = new BaubleAmulet("flame_pendant", new ResourceLocation(Artifacts.MODID,"textures/entity/layer/flame_pendant.png"));
    public static final BaubleBase THORN_PENDANT = new BaubleAmulet("thorn_pendant", new ResourceLocation(Artifacts.MODID,"textures/entity/layer/thorn_pendant.png"));
    public static final BaubleBase LUCKY_HORSESHOE = new BaubleBase("lucky_horseshoe", BaubleType.CHARM).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1);
    public static final BaubleBase COBALT_SHIELD = new BaubleBase("cobalt_shield", BaubleType.CHARM).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
    public static final BaubleBase OBSIDIAN_SKULL = new BaubleObsidianSkull();
    public static final BaubleBase PANIC_NECKLACE = new BaublePanicNecklace();
    public static final BaubleBase WHOOPIE_CUSHION = new BaubleWhoopieCushion();
    public static final BaubleBase BOTTLED_CLOUD = new BaubleBottledCloud("bottled_cloud", false);
    public static final BaubleBase BOTTLED_FART = new BaubleBottledCloud("bottled_fart", true);
    public static final BaubleBase MAGMA_STONE = new BaubleBase("magma_stone", BaubleType.RING);
    public static final BaubleBase FERAL_CLAWS = new BaubleFeralClaws();
    public static final BaubleBase POWER_GLOVE = new BaublePowerGlove();
    public static final BaubleBase MECHANICAL_GLOVE = new BaubleMechanicalGlove("mechanical_glove");
    public static final BaubleBase FIRE_GAUNTLET = new BaubleMechanicalGlove("fire_gauntlet");
    public static final BaubleBase PHILOSOPHERS_STONE = new BaubleBase("philosophers_stone", BaubleType.CHARM);
    public static final BaubleBase STAR_CLOAK = new BaubleStarCloak();

    public static final ItemEverlastingFood EVERLASTING_PORKCHOP = new ItemEverlastingFood("everlasting_porkchop", 3, 0.3F);
    public static final ItemEverlastingFood EVERLASTING_COOKED_PORKCHOP = new ItemEverlastingFood("everlasting_cooked_porkchop", 8, 0.8F);
    public static final ItemEverlastingFood EVERLASTING_BEEF = new ItemEverlastingFood("everlasting_beef", 3, 0.3F);
    public static final ItemEverlastingFood EVERLASTING_COOKED_BEEF = new ItemEverlastingFood("everlasting_cooked_beef", 8, 0.8F);
    public static final ItemEverlastingFood EVERLASTING_CHICKEN = (ItemEverlastingFood) new ItemEverlastingFood("everlasting_chicken", 2, 0.3F).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F);
    public static final ItemEverlastingFood EVERLASTING_COOKED_CHICKEN = new ItemEverlastingFood("everlasting_cooked_chicken", 6, 0.6F);
    public static final ItemEverlastingFood EVERLASTING_ROTTEN_FLESH = (ItemEverlastingFood) new ItemEverlastingFood("everlasting_rotten_flesh", 4, 0.1F).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.8F);
    public static final ItemEverlastingFood EVERLASTING_SPIDER_EYE = (ItemEverlastingFood) new ItemEverlastingFood("everlasting_spider_eye", 2, 0.8F).setPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0), 1);
    public static final ItemEverlastingFood EVERLASTING_RABBIT = new ItemEverlastingFood("everlasting_rabbit", 3, 0.3F);
    public static final ItemEverlastingFood EVERLASTING_COOKED_RABBIT = new ItemEverlastingFood("everlasting_cooked_rabbit", 5, 0.6F);
    public static final ItemEverlastingFood EVERLASTING_MUTTON = new ItemEverlastingFood("everlasting_mutton", 2, 0.3F);
    public static final ItemEverlastingFood EVERLASTING_COOKED_MUTTON = new ItemEverlastingFood("everlasting_cooked_mutton", 6, 0.8F);
    public static final ItemEverlastingFood EVERLASTING_RABBIT_STEW = new ItemEverlastingFood("everlasting_rabbit_stew", 10, 0.6F);
    public static final ItemEverlastingFood EVERLASTING_COD = new ItemEverlastingFood("everlasting_cod", 2, 0.1F);
    public static final ItemEverlastingFood EVERLASTING_COOKED_COD = new ItemEverlastingFood("everlasting_cooked_cod", 5, 0.6F);
    public static final ItemEverlastingFood EVERLASTING_SALMON = new ItemEverlastingFood("everlasting_salmon", 2, 0.1F);
    public static final ItemEverlastingFood EVERLASTING_COOKED_SALMON = new ItemEverlastingFood("everlasting_cooked_salmon", 6, 0.8F);
    public static final ItemEverlastingFood EVERLASTING_CLOWNFISH = new ItemEverlastingFood("everlasting_clownfish", 1, 0.1F);

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.registerAll(
                SHINY_RED_BALLOON,
                OBSIDIAN_SKULL,
                SNORKEL,
                SHOCK_PENDANT,
                FLAME_PENDANT,
                THORN_PENDANT,
                LUCKY_HORSESHOE,
                COBALT_SHIELD,
                PANIC_NECKLACE,
                WHOOPIE_CUSHION,
                BOTTLED_CLOUD,
                BOTTLED_FART,
                MAGMA_STONE,
                FERAL_CLAWS,
                POWER_GLOVE,
                MECHANICAL_GLOVE,
                FIRE_GAUNTLET,
                PHILOSOPHERS_STONE,
                STAR_CLOAK,

                EVERLASTING_PORKCHOP,
                EVERLASTING_COOKED_PORKCHOP,
                EVERLASTING_BEEF,
                EVERLASTING_COOKED_BEEF,
                EVERLASTING_CHICKEN,
                EVERLASTING_COOKED_CHICKEN,
                EVERLASTING_ROTTEN_FLESH,
                EVERLASTING_SPIDER_EYE,
                EVERLASTING_RABBIT,
                EVERLASTING_COOKED_RABBIT,
                EVERLASTING_MUTTON,
                EVERLASTING_COOKED_MUTTON,
                EVERLASTING_RABBIT_STEW,
                EVERLASTING_COD,
                EVERLASTING_COOKED_COD,
                EVERLASTING_SALMON,
                EVERLASTING_COOKED_SALMON,
                EVERLASTING_CLOWNFISH
        );
    }

    public static void registerModels() {
        SHINY_RED_BALLOON.registerModel();
        OBSIDIAN_SKULL.registerModel();
        SNORKEL.registerModel();
        SHOCK_PENDANT.registerModel();
        FLAME_PENDANT.registerModel();
        THORN_PENDANT.registerModel();
        LUCKY_HORSESHOE.registerModel();
        COBALT_SHIELD.registerModel();
        PANIC_NECKLACE.registerModel();
        WHOOPIE_CUSHION.registerModel();
        BOTTLED_CLOUD.registerModel();
        BOTTLED_FART.registerModel();
        MAGMA_STONE.registerModel();
        FERAL_CLAWS.registerModel();
        POWER_GLOVE.registerModel();
        MECHANICAL_GLOVE.registerModel();
        FIRE_GAUNTLET.registerModel();
        PHILOSOPHERS_STONE.registerModel();
        STAR_CLOAK.registerModel();

        EVERLASTING_PORKCHOP.registerModel();
        EVERLASTING_COOKED_PORKCHOP.registerModel();
        EVERLASTING_BEEF.registerModel();
        EVERLASTING_COOKED_BEEF.registerModel();
        EVERLASTING_CHICKEN.registerModel();
        EVERLASTING_COOKED_CHICKEN.registerModel();
        EVERLASTING_ROTTEN_FLESH.registerModel();
        EVERLASTING_SPIDER_EYE.registerModel();
        EVERLASTING_RABBIT.registerModel();
        EVERLASTING_COOKED_RABBIT.registerModel();
        EVERLASTING_MUTTON.registerModel();
        EVERLASTING_COOKED_MUTTON.registerModel();
        EVERLASTING_RABBIT_STEW.registerModel();
        EVERLASTING_COD.registerModel();
        EVERLASTING_COOKED_COD.registerModel();
        EVERLASTING_SALMON.registerModel();
        EVERLASTING_COOKED_SALMON.registerModel();
        EVERLASTING_CLOWNFISH.registerModel();
    }
}
