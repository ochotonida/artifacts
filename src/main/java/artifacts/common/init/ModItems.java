package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.item.*;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Artifacts.MODID);

    public static final ItemGroup CREATIVE_TAB = new ItemGroup(Artifacts.MODID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(BUNNY_HOPPERS.get());
        }
    };

    // misc items
    public static final RegistryObject<Item>
            MIMIC_SPAWN_EGG = REGISTRY.register("mimic_spawn_egg", () -> new SpawnEggItem(ModEntities.MIMIC, 0x805113, 0x212121, new Item.Properties().group(ItemGroup.MISC))),
            UMBRELLA = REGISTRY.register("umbrella", UmbrellaItem::new),
            EVERLASTING_BEEF = REGISTRY.register("everlasting_beef", () -> new EverlastingFoodItem(new Food.Builder().hunger(3).saturation(0.3F).build())),
            ETERNAL_STEAK = REGISTRY.register("eternal_steak", () -> new EverlastingFoodItem(new Food.Builder().hunger(8).saturation(0.8F).build()));

    // head
    public static final RegistryObject<Item>
            PLASTIC_DRINKING_HAT = REGISTRY.register("plastic_drinking_hat", () -> new DrinkingHatItem(new ResourceLocation(Artifacts.MODID, "textures/entity/curio/plastic_drinking_hat.png"))),
            NOVELTY_DRINKING_HAT = REGISTRY.register("novelty_drinking_hat", () -> new DrinkingHatItem(new ResourceLocation(Artifacts.MODID, "textures/entity/curio/novelty_drinking_hat.png"))),
            SNORKEL = REGISTRY.register("snorkel", SnorkelItem::new),
            NIGHT_VISION_GOGGLES = REGISTRY.register("night_vision_goggles", NightVisionGogglesItem::new),
            VILLAGER_HAT = REGISTRY.register("villager_hat", VillagerHatItem::new),
            SUPERSTITIOUS_HAT = REGISTRY.register("superstitious_hat", SuperstitiousHatItem::new);

    // necklace
    public static final RegistryObject<Item>
            LUCKY_SCARF = REGISTRY.register("lucky_scarf", LuckyScarfItem::new),
            SCARF_OF_INVISIBILITY = REGISTRY.register("scarf_of_invisibility", ScarfOfInvisibilityItem::new),
            CROSS_NECKLACE = REGISTRY.register("cross_necklace", CrossNecklaceItem::new),
            PANIC_NECKLACE = REGISTRY.register("panic_necklace", PanicNecklaceItem::new),
            SHOCK_PENDANT = REGISTRY.register("shock_pendant", ShockPendantItem::new),
            FLAME_PENDANT = REGISTRY.register("flame_pendant", FlamePendantItem::new),
            THORN_PENDANT = REGISTRY.register("thorn_pendant", ThornPendantItem::new);

    // belt
    public static final RegistryObject<Item>
            OBSIDIAN_SKULL = REGISTRY.register("obsidian_skull", ObsidianSkullItem::new),
            ANTIDOTE_VESSEL = REGISTRY.register("antidote_vessel", AntidoteVesselItem::new),
            UNIVERSAL_ATTRACTOR = REGISTRY.register("universal_attractor", UniversalAttractorItem::new),
            CRYSTAL_HEART = REGISTRY.register("crystal_heart", CrystalHeartItem::new),
            CLOUD_IN_A_BOTTLE = REGISTRY.register("cloud_in_a_bottle", CloudInABottleItem::new);
    // HELIUM_FLAMINGO = REGISTRY.register("helium_flamingo", HeliumFlamingoItem::new);

    // hands
    public static final RegistryObject<Item>
            DIGGING_CLAWS = REGISTRY.register("digging_claws", DiggingClawsItem::new),
            FERAL_CLAWS = REGISTRY.register("feral_claws", FeralClawsItem::new),
            POWER_GLOVE = REGISTRY.register("power_glove", PowerGloveItem::new),
            FIRE_GAUNTLET = REGISTRY.register("fire_gauntlet", FireGauntletItem::new),
            POCKET_PISTON = REGISTRY.register("pocket_piston", PocketPistonItem::new),
            VAMPIRIC_GLOVE = REGISTRY.register("vampiric_glove", VampiricGloveItem::new),
            GOLDEN_HOOK = REGISTRY.register("golden_hook", GoldenHookItem::new);

    // feet
    public static final RegistryObject<Item>
            BUNNY_HOPPERS = REGISTRY.register("bunny_hoppers", BunnyHoppersItem::new),
            KITTY_SLIPPERS = REGISTRY.register("kitty_slippers", KittySlippersItem::new),
            RUNNING_SHOES = REGISTRY.register("running_shoes", RunningShoesItem::new),
            STEADFAST_SPIKES = REGISTRY.register("steadfast_spikes", SteadfastSpikesItem::new),
            FLIPPERS = REGISTRY.register("flippers", FlippersItem::new);

    // curio
    public static final RegistryObject<Item>
            WHOOPEE_CUSHION = REGISTRY.register("whoopee_cushion", WhoopeeCushionItem::new);
}
