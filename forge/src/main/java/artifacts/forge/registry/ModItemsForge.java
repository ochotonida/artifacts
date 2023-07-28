package artifacts.forge.registry;

import artifacts.forge.item.UmbrellaItem;
import artifacts.forge.item.wearable.belt.CloudInABottleItem;
import artifacts.forge.item.wearable.belt.HeliumFlamingoItem;
import artifacts.forge.item.wearable.belt.ObsidianSkullItem;
import artifacts.forge.item.wearable.belt.UniversalAttractorItem;
import artifacts.forge.item.wearable.feet.*;
import artifacts.forge.item.wearable.hands.*;
import artifacts.forge.item.wearable.head.DrinkingHatItem;
import artifacts.forge.item.wearable.necklace.*;
import artifacts.item.EverlastingFoodItem;
import artifacts.item.wearable.MobEffectItem;
import artifacts.item.wearable.WhoopeeCushionItem;
import artifacts.item.wearable.belt.AntidoteVesselItem;
import artifacts.item.wearable.belt.CrystalHeartItem;
import artifacts.item.wearable.feet.SteadfastSpikesItem;
import artifacts.item.wearable.hands.FeralClawsItem;
import artifacts.item.wearable.hands.PowerGloveItem;
import artifacts.item.wearable.head.SuperstitiousHatItem;
import artifacts.item.wearable.head.VillagerHatItem;
import artifacts.item.wearable.necklace.CrossNecklaceItem;
import artifacts.item.wearable.necklace.LuckyScarfItem;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class ModItemsForge {

    public static void registerItems() {
        ModItems.MIMIC_SPAWN_EGG = ModItems.ITEMS.register("mimic_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.MIMIC, 0x805113, 0x212121, new Item.Properties())); // TODO add to creative tab
        ModItems.UMBRELLA = ModItems.ITEMS.register("umbrella", UmbrellaItem::new);
        ModItems.EVERLASTING_BEEF = ModItems.ITEMS.register("everlasting_beef", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build(), ModGameRules.EVERLASTING_BEEF_COOLDOWN, ModGameRules.EVERLASTING_BEEF_ENABLED));
        ModItems.ETERNAL_STEAK = ModItems.ITEMS.register("eternal_steak", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build(), ModGameRules.ETERNAL_STEAK_COOLDOWN, ModGameRules.ETERNAL_STEAK_ENABLED));

        // head
        ModItems.PLASTIC_DRINKING_HAT = ModItems.ITEMS.register("plastic_drinking_hat", () -> new DrinkingHatItem(ModGameRules.PLASTIC_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER, ModGameRules.PLASTIC_DRINKING_HAT_EATING_DURATION_MULTIPLIER, false));
        ModItems.NOVELTY_DRINKING_HAT = ModItems.ITEMS.register("novelty_drinking_hat", () -> new DrinkingHatItem(ModGameRules.NOVELTY_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER, ModGameRules.NOVELTY_DRINKING_HAT_EATING_DURATION_MULTIPLIER, true));
        ModItems.SNORKEL = ModItems.ITEMS.register("snorkel", () -> new MobEffectItem(MobEffects.WATER_BREATHING, ModGameRules.SNORKEL_ENABLED));
        ModItems.NIGHT_VISION_GOGGLES = ModItems.ITEMS.register("night_vision_goggles", () -> new MobEffectItem(MobEffects.NIGHT_VISION, 320, ModGameRules.NIGHT_VISION_GOGGLES_ENABLED));
        ModItems.VILLAGER_HAT = ModItems.ITEMS.register("villager_hat", VillagerHatItem::new);
        ModItems.SUPERSTITIOUS_HAT = ModItems.ITEMS.register("superstitious_hat", SuperstitiousHatItem::new);

        ModItems.LUCKY_SCARF = ModItems.ITEMS.register("lucky_scarf", LuckyScarfItem::new);
        ModItems.SCARF_OF_INVISIBILITY = ModItems.ITEMS.register("scarf_of_invisibility", () -> new MobEffectItem(MobEffects.INVISIBILITY, ModGameRules.SCARF_OF_INVISIBILITY_ENABLED));
        ModItems.CROSS_NECKLACE = ModItems.ITEMS.register("cross_necklace", CrossNecklaceItem::new);
        ModItems.PANIC_NECKLACE = ModItems.ITEMS.register("panic_necklace", PanicNecklaceItem::new);
        ModItems.SHOCK_PENDANT = ModItems.ITEMS.register("shock_pendant", ShockPendantItem::new);
        ModItems.FLAME_PENDANT = ModItems.ITEMS.register("flame_pendant", FlamePendantItem::new);
        ModItems.THORN_PENDANT = ModItems.ITEMS.register("thorn_pendant", ThornPendantItem::new);
        ModItems.CHARM_OF_SINKING = ModItems.ITEMS.register("charm_of_sinking", CharmOfSinkingItem::new);

        ModItems.CLOUD_IN_A_BOTTLE = ModItems.ITEMS.register("cloud_in_a_bottle", CloudInABottleItem::new);
        ModItems.OBSIDIAN_SKULL = ModItems.ITEMS.register("obsidian_skull", ObsidianSkullItem::new);
        ModItems.ANTIDOTE_VESSEL = ModItems.ITEMS.register("antidote_vessel", AntidoteVesselItem::new);
        ModItems.UNIVERSAL_ATTRACTOR = ModItems.ITEMS.register("universal_attractor", UniversalAttractorItem::new);
        ModItems.CRYSTAL_HEART = ModItems.ITEMS.register("crystal_heart", CrystalHeartItem::new);
        ModItems.HELIUM_FLAMINGO = ModItems.ITEMS.register("helium_flamingo", HeliumFlamingoItem::new);

        ModItems.DIGGING_CLAWS = ModItems.ITEMS.register("digging_claws", DiggingClawsItem::new);
        ModItems.FERAL_CLAWS = ModItems.ITEMS.register("feral_claws", FeralClawsItem::new);
        ModItems.POWER_GLOVE = ModItems.ITEMS.register("power_glove", PowerGloveItem::new);
        ModItems.FIRE_GAUNTLET = ModItems.ITEMS.register("fire_gauntlet", FireGauntletItem::new);
        ModItems.POCKET_PISTON = ModItems.ITEMS.register("pocket_piston", PocketPistonItem::new);
        ModItems.VAMPIRIC_GLOVE = ModItems.ITEMS.register("vampiric_glove", VampiricGloveItem::new);
        ModItems.GOLDEN_HOOK = ModItems.ITEMS.register("golden_hook", GoldenHookItem::new);

        ModItems.AQUA_DASHERS = ModItems.ITEMS.register("aqua_dashers", AquaDashersItem::new);
        ModItems.BUNNY_HOPPERS = ModItems.ITEMS.register("bunny_hoppers", BunnyHoppersItem::new);
        ModItems.KITTY_SLIPPERS = ModItems.ITEMS.register("kitty_slippers", KittySlippersItem::new);
        ModItems.RUNNING_SHOES = ModItems.ITEMS.register("running_shoes", RunningShoesItem::new);
        ModItems.STEADFAST_SPIKES = ModItems.ITEMS.register("steadfast_spikes", SteadfastSpikesItem::new);
        ModItems.FLIPPERS = ModItems.ITEMS.register("flippers", FlippersItem::new);

        ModItems.WHOOPEE_CUSHION = ModItems.ITEMS.register("whoopee_cushion", WhoopeeCushionItem::new);
    }
}
