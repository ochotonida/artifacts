package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.item.*;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class Items {

    public static final Item MIMIC_SPAWN_EGG = new SpawnEggItem(Entities.MIMIC, 0x805113, 0x212121, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic_spawn_egg"));
    public static final Item PLASTIC_DRINKING_HAT = new DrinkingHatItem("plastic_drinking_hat", false);
    public static final Item NOVELTY_DRINKING_HAT = new DrinkingHatItem("novelty_drinking_hat", true);
    public static final Item SNORKEL = new SnorkelItem();
    public static final Item NIGHT_VISION_GOGGLES = new NightVisionGogglesItem();
    public static final Item PANIC_NECKLACE = new PanicNecklaceItem();
    public static final Item SHOCK_PENDANT = new PendantItem("shock_pendant");
    public static final Item FLAME_PENDANT = new PendantItem("flame_pendant");
    public static final Item THORN_PENDANT = new PendantItem("thorn_pendant");
    public static final Item FLIPPERS = new FlippersItem();
    public static final Item OBSIDIAN_SKULL = new ObsidianSkullItem();
    public static final Item UMBRELLA = new UmbrellaItem();
    public static final Item EVERLASTING_BEEF = new EverlastingFoodItem(new Item.Properties().food(Foods.BEEF), "everlasting_beef");
    public static final Item ETERNAL_STEAK = new EverlastingFoodItem(new Item.Properties().food(Foods.COOKED_BEEF), "eternal_steak");
    public static final Item FIRE_GAUNTLET = new FireGauntletItem();
    public static final Item FERAL_CLAWS = new FeralClawsItem();
    public static final Item POCKET_PISTON = new PocketPistonItem();
    public static final Item POWER_GLOVE = new PowerGloveItem();
    public static final Item CROSS_NECKLACE = new CrossNecklaceItem();
    public static final Item ANTIDOTE_VESSEL = new AntidoteVesselItem();
    public static final Item LUCKY_SCARF = new LuckyScarfItem();
    public static final Item SUPERSTITIOUS_HAT = new SuperstitiousHatItem();
    public static final Item SCARF_OF_INVISIBILITY = new ScarfOfInvisibilityItem();
    public static final Item DIGGING_CLAWS = new DiggingClawsItem();
    public static final Item STEADFAST_SPIKES = new SteadfastSpikesItem();
    public static final Item UNIVERSAL_ATTRACTOR = new UniversalAttractorItem();
    public static final Item KITTY_SLIPPERS = new KittySlippersItem();

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                MIMIC_SPAWN_EGG,
                PLASTIC_DRINKING_HAT,
                NOVELTY_DRINKING_HAT,
                SNORKEL,
                NIGHT_VISION_GOGGLES,
                PANIC_NECKLACE,
                SHOCK_PENDANT,
                FLAME_PENDANT,
                THORN_PENDANT,
                FLIPPERS,
                OBSIDIAN_SKULL,
                UMBRELLA,
                EVERLASTING_BEEF,
                ETERNAL_STEAK,
                FIRE_GAUNTLET,
                FERAL_CLAWS,
                POCKET_PISTON,
                POWER_GLOVE,
                CROSS_NECKLACE,
                ANTIDOTE_VESSEL,
                LUCKY_SCARF,
                SUPERSTITIOUS_HAT,
                SCARF_OF_INVISIBILITY,
                DIGGING_CLAWS,
                STEADFAST_SPIKES,
                UNIVERSAL_ATTRACTOR,
                KITTY_SLIPPERS
        );
    }
}
