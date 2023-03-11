package artifacts.registry;

import artifacts.Artifacts;
import artifacts.item.EverlastingFoodItem;
import artifacts.item.UmbrellaItem;
import artifacts.item.wearable.MobEffectItem;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.item.wearable.WhoopeeCushionItem;
import artifacts.item.wearable.belt.*;
import artifacts.item.wearable.feet.*;
import artifacts.item.wearable.hands.*;
import artifacts.item.wearable.head.DrinkingHatItem;
import artifacts.item.wearable.head.SuperstitiousHatItem;
import artifacts.item.wearable.head.VillagerHatItem;
import artifacts.item.wearable.necklace.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Artifacts.MODID);

    public static CreativeModeTab CREATIVE_TAB;

    public static void registerTab(CreativeModeTabEvent.Register event) {
        CREATIVE_TAB = event.registerCreativeModeTab(Artifacts.id("main"), builder -> builder
                .icon(() -> new ItemStack(ModItems.BUNNY_HOPPERS.get()))
                .title(Component.translatable("%s.creative_tab".formatted(Artifacts.MODID)))
                .displayItems((featureFlags, output, hasOp) -> ForgeRegistries.ITEMS.forEach(item -> {
                    ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
                    if (key != null && key.getNamespace().equals(Artifacts.MODID)) {
                        output.accept(item);
                    }
                }))
        );
    }

    public static final RegistryObject<Item> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.MIMIC, 0x805113, 0x212121, new Item.Properties()));
    public static final RegistryObject<Item> UMBRELLA = ITEMS.register("umbrella", UmbrellaItem::new);
    public static final RegistryObject<Item> EVERLASTING_BEEF = ITEMS.register("everlasting_beef", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build(), ModGameRules.EVERLASTING_BEEF_COOLDOWN, ModGameRules.EVERLASTING_BEEF_ENABLED));
    public static final RegistryObject<Item> ETERNAL_STEAK = ITEMS.register("eternal_steak", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build(), ModGameRules.ETERNAL_STEAK_COOLDOWN, ModGameRules.ETERNAL_STEAK_ENABLED));

    // head
    public static final RegistryObject<WearableArtifactItem> PLASTIC_DRINKING_HAT = ITEMS.register("plastic_drinking_hat", () -> new DrinkingHatItem(ModGameRules.PLASTIC_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER, ModGameRules.PLASTIC_DRINKING_HAT_EATING_DURATION_MULTIPLIER, false));
    public static final RegistryObject<WearableArtifactItem> NOVELTY_DRINKING_HAT = ITEMS.register("novelty_drinking_hat", () -> new DrinkingHatItem(ModGameRules.NOVELTY_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER, ModGameRules.NOVELTY_DRINKING_HAT_EATING_DURATION_MULTIPLIER, true));
    public static final RegistryObject<WearableArtifactItem> SNORKEL = ITEMS.register("snorkel", () -> new MobEffectItem(MobEffects.WATER_BREATHING, ModGameRules.SNORKEL_ENABLED));
    public static final RegistryObject<WearableArtifactItem> NIGHT_VISION_GOGGLES = ITEMS.register("night_vision_goggles", () -> new MobEffectItem(MobEffects.NIGHT_VISION, 320, ModGameRules.NIGHT_VISION_GOGGLES_ENABLED));
    public static final RegistryObject<WearableArtifactItem> VILLAGER_HAT = ITEMS.register("villager_hat", VillagerHatItem::new);
    public static final RegistryObject<WearableArtifactItem> SUPERSTITIOUS_HAT = ITEMS.register("superstitious_hat", SuperstitiousHatItem::new);

    // necklace
    public static final RegistryObject<WearableArtifactItem> LUCKY_SCARF = ITEMS.register("lucky_scarf", LuckyScarfItem::new);
    public static final RegistryObject<WearableArtifactItem> SCARF_OF_INVISIBILITY = ITEMS.register("scarf_of_invisibility", () -> new MobEffectItem(MobEffects.INVISIBILITY, ModGameRules.SCARF_OF_INVISIBILITY_ENABLED));
    public static final RegistryObject<WearableArtifactItem> CROSS_NECKLACE = ITEMS.register("cross_necklace", CrossNecklaceItem::new);
    public static final RegistryObject<WearableArtifactItem> PANIC_NECKLACE = ITEMS.register("panic_necklace", PanicNecklaceItem::new);
    public static final RegistryObject<WearableArtifactItem> SHOCK_PENDANT = ITEMS.register("shock_pendant", ShockPendantItem::new);
    public static final RegistryObject<WearableArtifactItem> FLAME_PENDANT = ITEMS.register("flame_pendant", FlamePendantItem::new);
    public static final RegistryObject<WearableArtifactItem> THORN_PENDANT = ITEMS.register("thorn_pendant", ThornPendantItem::new);
    public static final RegistryObject<WearableArtifactItem> CHARM_OF_SINKING = ITEMS.register("charm_of_sinking", CharmOfSinkingItem::new);

    // belt
    public static final RegistryObject<CloudInABottleItem> CLOUD_IN_A_BOTTLE = ITEMS.register("cloud_in_a_bottle", CloudInABottleItem::new);
    public static final RegistryObject<WearableArtifactItem> OBSIDIAN_SKULL = ITEMS.register("obsidian_skull", ObsidianSkullItem::new);
    public static final RegistryObject<WearableArtifactItem> ANTIDOTE_VESSEL = ITEMS.register("antidote_vessel", AntidoteVesselItem::new);
    public static final RegistryObject<WearableArtifactItem> UNIVERSAL_ATTRACTOR = ITEMS.register("universal_attractor", UniversalAttractorItem::new);
    public static final RegistryObject<WearableArtifactItem> CRYSTAL_HEART = ITEMS.register("crystal_heart", CrystalHeartItem::new);
    public static final RegistryObject<WearableArtifactItem> HELIUM_FLAMINGO = ITEMS.register("helium_flamingo", HeliumFlamingoItem::new);

    // hands
    public static final RegistryObject<WearableArtifactItem> DIGGING_CLAWS = ITEMS.register("digging_claws", DiggingClawsItem::new);
    public static final RegistryObject<WearableArtifactItem> FERAL_CLAWS = ITEMS.register("feral_claws", FeralClawsItem::new);
    public static final RegistryObject<WearableArtifactItem> POWER_GLOVE = ITEMS.register("power_glove", PowerGloveItem::new);
    public static final RegistryObject<WearableArtifactItem> FIRE_GAUNTLET = ITEMS.register("fire_gauntlet", FireGauntletItem::new);
    public static final RegistryObject<WearableArtifactItem> POCKET_PISTON = ITEMS.register("pocket_piston", PocketPistonItem::new);
    public static final RegistryObject<WearableArtifactItem> VAMPIRIC_GLOVE = ITEMS.register("vampiric_glove", VampiricGloveItem::new);
    public static final RegistryObject<WearableArtifactItem> GOLDEN_HOOK = ITEMS.register("golden_hook", GoldenHookItem::new);

    // feet
    public static final RegistryObject<AquaDashersItem> AQUA_DASHERS = ITEMS.register("aqua_dashers", AquaDashersItem::new);
    public static final RegistryObject<WearableArtifactItem> BUNNY_HOPPERS = ITEMS.register("bunny_hoppers", BunnyHoppersItem::new);
    public static final RegistryObject<WearableArtifactItem> KITTY_SLIPPERS = ITEMS.register("kitty_slippers", KittySlippersItem::new);
    public static final RegistryObject<WearableArtifactItem> RUNNING_SHOES = ITEMS.register("running_shoes", RunningShoesItem::new);
    public static final RegistryObject<WearableArtifactItem> STEADFAST_SPIKES = ITEMS.register("steadfast_spikes", SteadfastSpikesItem::new);
    public static final RegistryObject<WearableArtifactItem> FLIPPERS = ITEMS.register("flippers", FlippersItem::new);

    // curio
    public static final RegistryObject<WearableArtifactItem> WHOOPEE_CUSHION = ITEMS.register("whoopee_cushion", WhoopeeCushionItem::new);
}
