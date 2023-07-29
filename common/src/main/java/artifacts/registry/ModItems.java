package artifacts.registry;

import artifacts.Artifacts;
import artifacts.item.EverlastingFoodItem;
import artifacts.item.wearable.MobEffectItem;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.item.wearable.WhoopeeCushionItem;
import artifacts.item.wearable.belt.AntidoteVesselItem;
import artifacts.item.wearable.belt.CrystalHeartItem;
import artifacts.item.wearable.belt.ObsidianSkullItem;
import artifacts.item.wearable.belt.UniversalAttractorItem;
import artifacts.item.wearable.feet.RunningShoesItem;
import artifacts.item.wearable.feet.SteadfastSpikesItem;
import artifacts.item.wearable.hands.FeralClawsItem;
import artifacts.item.wearable.hands.FireGauntletItem;
import artifacts.item.wearable.hands.PocketPistonItem;
import artifacts.item.wearable.hands.PowerGloveItem;
import artifacts.item.wearable.head.SuperstitiousHatItem;
import artifacts.item.wearable.head.VillagerHatItem;
import artifacts.item.wearable.necklace.*;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Artifacts.MOD_ID, Registries.ITEM);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Artifacts.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("main", () ->
            CreativeTabRegistry.create(
                    Component.translatable("%s.creative_tab".formatted(Artifacts.MOD_ID)),
                    () -> new ItemStack(ModItems.BUNNY_HOPPERS.get())
            )
    );

    public static RegistrySupplier<Item> MIMIC_SPAWN_EGG;
    public static RegistrySupplier<Item> UMBRELLA;
    public static RegistrySupplier<Item> EVERLASTING_BEEF = ITEMS.register("everlasting_beef", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build(), ModGameRules.EVERLASTING_BEEF_COOLDOWN, ModGameRules.EVERLASTING_BEEF_ENABLED));
    public static RegistrySupplier<Item> ETERNAL_STEAK = ITEMS.register("eternal_steak", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build(), ModGameRules.ETERNAL_STEAK_COOLDOWN, ModGameRules.ETERNAL_STEAK_ENABLED));

    // head
    public static RegistrySupplier<WearableArtifactItem> PLASTIC_DRINKING_HAT;
    public static RegistrySupplier<WearableArtifactItem> NOVELTY_DRINKING_HAT;
    public static RegistrySupplier<WearableArtifactItem> SNORKEL = ITEMS.register("snorkel", () -> new MobEffectItem(MobEffects.WATER_BREATHING, ModGameRules.SNORKEL_ENABLED));
    public static RegistrySupplier<WearableArtifactItem> NIGHT_VISION_GOGGLES = ITEMS.register("night_vision_goggles", () -> new MobEffectItem(MobEffects.NIGHT_VISION, 320, ModGameRules.NIGHT_VISION_GOGGLES_ENABLED));
    public static RegistrySupplier<WearableArtifactItem> VILLAGER_HAT = ITEMS.register("villager_hat", VillagerHatItem::new);
    public static RegistrySupplier<WearableArtifactItem> SUPERSTITIOUS_HAT = ITEMS.register("superstitious_hat", SuperstitiousHatItem::new);

    // necklace
    public static RegistrySupplier<WearableArtifactItem> LUCKY_SCARF = ITEMS.register("lucky_scarf", LuckyScarfItem::new);
    public static RegistrySupplier<WearableArtifactItem> SCARF_OF_INVISIBILITY = ITEMS.register("scarf_of_invisibility", () -> new MobEffectItem(MobEffects.INVISIBILITY, ModGameRules.SCARF_OF_INVISIBILITY_ENABLED));
    public static RegistrySupplier<WearableArtifactItem> CROSS_NECKLACE = ITEMS.register("cross_necklace", CrossNecklaceItem::new);
    public static RegistrySupplier<WearableArtifactItem> PANIC_NECKLACE = ITEMS.register("panic_necklace", PanicNecklaceItem::new);
    public static RegistrySupplier<WearableArtifactItem> SHOCK_PENDANT = ITEMS.register("shock_pendant", ShockPendantItem::new);
    public static RegistrySupplier<WearableArtifactItem> FLAME_PENDANT = ITEMS.register("flame_pendant", FlamePendantItem::new);
    public static RegistrySupplier<WearableArtifactItem> THORN_PENDANT = ITEMS.register("thorn_pendant", ThornPendantItem::new);
    public static RegistrySupplier<WearableArtifactItem> CHARM_OF_SINKING;

    // belt
    public static RegistrySupplier<WearableArtifactItem> CLOUD_IN_A_BOTTLE;
    public static RegistrySupplier<WearableArtifactItem> OBSIDIAN_SKULL = ITEMS.register("obsidian_skull", ObsidianSkullItem::new);
    public static RegistrySupplier<WearableArtifactItem> ANTIDOTE_VESSEL = ITEMS.register("antidote_vessel", AntidoteVesselItem::new);
    public static RegistrySupplier<WearableArtifactItem> UNIVERSAL_ATTRACTOR = ITEMS.register("universal_attractor", UniversalAttractorItem::new);
    public static RegistrySupplier<WearableArtifactItem> CRYSTAL_HEART = ITEMS.register("crystal_heart", CrystalHeartItem::new);
    public static RegistrySupplier<WearableArtifactItem> HELIUM_FLAMINGO;

    // hands
    public static RegistrySupplier<WearableArtifactItem> DIGGING_CLAWS;
    public static RegistrySupplier<WearableArtifactItem> FERAL_CLAWS = ITEMS.register("feral_claws", FeralClawsItem::new);
    public static RegistrySupplier<WearableArtifactItem> POWER_GLOVE = ITEMS.register("power_glove", PowerGloveItem::new);
    public static RegistrySupplier<WearableArtifactItem> FIRE_GAUNTLET = ITEMS.register("fire_gauntlet", FireGauntletItem::new);
    public static RegistrySupplier<WearableArtifactItem> POCKET_PISTON = ITEMS.register("pocket_piston", PocketPistonItem::new);
    public static RegistrySupplier<WearableArtifactItem> VAMPIRIC_GLOVE;
    public static RegistrySupplier<WearableArtifactItem> GOLDEN_HOOK;

    // feet
    public static RegistrySupplier<WearableArtifactItem> AQUA_DASHERS;
    public static RegistrySupplier<WearableArtifactItem> BUNNY_HOPPERS;
    public static RegistrySupplier<WearableArtifactItem> KITTY_SLIPPERS;
    public static RegistrySupplier<WearableArtifactItem> RUNNING_SHOES = ITEMS.register("running_shoes", RunningShoesItem::new);
    public static RegistrySupplier<WearableArtifactItem> STEADFAST_SPIKES = ITEMS.register("steadfast_spikes", SteadfastSpikesItem::new);
    public static RegistrySupplier<WearableArtifactItem> FLIPPERS;

    // curio
    public static RegistrySupplier<WearableArtifactItem> WHOOPEE_CUSHION = ITEMS.register("whoopee_cushion", WhoopeeCushionItem::new);
}
