package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.item.EverlastingFoodItem;
import artifacts.common.item.UmbrellaItem;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.item.curio.WhoopeeCushionItem;
import artifacts.common.item.curio.belt.*;
import artifacts.common.item.curio.feet.*;
import artifacts.common.item.curio.hands.*;
import artifacts.common.item.curio.head.DrinkingHatItem;
import artifacts.common.item.curio.head.NightVisionGogglesItem;
import artifacts.common.item.curio.head.SnorkelItem;
import artifacts.common.item.curio.head.SuperstitiousHatItem;
import artifacts.common.item.curio.necklace.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
                .title(Component.translatable("itemGroup.%s".formatted(Artifacts.MODID)))
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
    public static final RegistryObject<Item> EVERLASTING_BEEF = ITEMS.register("everlasting_beef", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build()));
    public static final RegistryObject<Item> ETERNAL_STEAK = ITEMS.register("eternal_steak", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build()));

    // head
    public static final RegistryObject<CurioItem> PLASTIC_DRINKING_HAT = ITEMS.register("plastic_drinking_hat", DrinkingHatItem::new);
    public static final RegistryObject<CurioItem> NOVELTY_DRINKING_HAT = ITEMS.register("novelty_drinking_hat", DrinkingHatItem::new);
    public static final RegistryObject<CurioItem> SNORKEL = ITEMS.register("snorkel", SnorkelItem::new);
    public static final RegistryObject<CurioItem> NIGHT_VISION_GOGGLES = ITEMS.register("night_vision_goggles", NightVisionGogglesItem::new);
    public static final RegistryObject<CurioItem> VILLAGER_HAT = ITEMS.register("villager_hat", CurioItem::new);
    public static final RegistryObject<CurioItem> SUPERSTITIOUS_HAT = ITEMS.register("superstitious_hat", SuperstitiousHatItem::new);

    // necklace
    public static final RegistryObject<CurioItem> LUCKY_SCARF = ITEMS.register("lucky_scarf", LuckyScarfItem::new);
    public static final RegistryObject<CurioItem> SCARF_OF_INVISIBILITY = ITEMS.register("scarf_of_invisibility", ScarfOfInvisibilityItem::new);
    public static final RegistryObject<CurioItem> CROSS_NECKLACE = ITEMS.register("cross_necklace", CrossNecklaceItem::new);
    public static final RegistryObject<CurioItem> PANIC_NECKLACE = ITEMS.register("panic_necklace", PanicNecklaceItem::new);
    public static final RegistryObject<CurioItem> SHOCK_PENDANT = ITEMS.register("shock_pendant", ShockPendantItem::new);
    public static final RegistryObject<CurioItem> FLAME_PENDANT = ITEMS.register("flame_pendant", FlamePendantItem::new);
    public static final RegistryObject<CurioItem> THORN_PENDANT = ITEMS.register("thorn_pendant", ThornPendantItem::new);
    public static final RegistryObject<CurioItem> CHARM_OF_SINKING = ITEMS.register("charm_of_sinking", CharmOfSinkingItem::new);

    // belt
    public static final RegistryObject<CloudInABottleItem> CLOUD_IN_A_BOTTLE = ITEMS.register("cloud_in_a_bottle", CloudInABottleItem::new);
    public static final RegistryObject<CurioItem> OBSIDIAN_SKULL = ITEMS.register("obsidian_skull", ObsidianSkullItem::new);
    public static final RegistryObject<CurioItem> ANTIDOTE_VESSEL = ITEMS.register("antidote_vessel", AntidoteVesselItem::new);
    public static final RegistryObject<CurioItem> UNIVERSAL_ATTRACTOR = ITEMS.register("universal_attractor", UniversalAttractorItem::new);
    public static final RegistryObject<CurioItem> CRYSTAL_HEART = ITEMS.register("crystal_heart", CrystalHeartItem::new);
    public static final RegistryObject<CurioItem> HELIUM_FLAMINGO = ITEMS.register("helium_flamingo", HeliumFlamingoItem::new);

    // hands
    public static final RegistryObject<CurioItem> DIGGING_CLAWS = ITEMS.register("digging_claws", DiggingClawsItem::new);
    public static final RegistryObject<CurioItem> FERAL_CLAWS = ITEMS.register("feral_claws", FeralClawsItem::new);
    public static final RegistryObject<CurioItem> POWER_GLOVE = ITEMS.register("power_glove", PowerGloveItem::new);
    public static final RegistryObject<CurioItem> FIRE_GAUNTLET = ITEMS.register("fire_gauntlet", FireGauntletItem::new);
    public static final RegistryObject<CurioItem> POCKET_PISTON = ITEMS.register("pocket_piston", PocketPistonItem::new);
    public static final RegistryObject<CurioItem> VAMPIRIC_GLOVE = ITEMS.register("vampiric_glove", VampiricGloveItem::new);
    public static final RegistryObject<CurioItem> GOLDEN_HOOK = ITEMS.register("golden_hook", GoldenHookItem::new);

    // feet
    public static final RegistryObject<AquaDashersItem> AQUA_DASHERS = ITEMS.register("aqua_dashers", AquaDashersItem::new);
    public static final RegistryObject<CurioItem> BUNNY_HOPPERS = ITEMS.register("bunny_hoppers", BunnyHoppersItem::new);
    public static final RegistryObject<CurioItem> KITTY_SLIPPERS = ITEMS.register("kitty_slippers", KittySlippersItem::new);
    public static final RegistryObject<CurioItem> RUNNING_SHOES = ITEMS.register("running_shoes", RunningShoesItem::new);
    public static final RegistryObject<CurioItem> STEADFAST_SPIKES = ITEMS.register("steadfast_spikes", SteadfastSpikesItem::new);
    public static final RegistryObject<CurioItem> FLIPPERS = ITEMS.register("flippers", FlippersItem::new);

    // curio
    public static final RegistryObject<CurioItem> WHOOPEE_CUSHION = ITEMS.register("whoopee_cushion", WhoopeeCushionItem::new);
}
