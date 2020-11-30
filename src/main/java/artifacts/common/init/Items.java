package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.item.*;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class Items {

    @ObjectHolder(Artifacts.MODID + ":bunny_hoppers")
    public static Item BUNNY_HOPPERS;
    @ObjectHolder(Artifacts.MODID + ":umbrella")
    public static Item UMBRELLA;
    @ObjectHolder(Artifacts.MODID + ":whoopee_cushion")
    public static Item WHOOPEE_CUSHION;

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(new SpawnEggItem(Entities.MIMIC, 0x805113, 0x212121, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic_spawn_egg")),
                // misc
                new UmbrellaItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "umbrella")),
                new EverlastingFoodItem(new Item.Properties().food(Foods.BEEF)).setRegistryName(new ResourceLocation(Artifacts.MODID, "everlasting_beef")),
                new EverlastingFoodItem(new Item.Properties().food(Foods.COOKED_BEEF)).setRegistryName(new ResourceLocation(Artifacts.MODID, "eternal_steak")),

                // head
                new DrinkingHatItem(new ResourceLocation(Artifacts.MODID, "textures/entity/curio/plastic_drinking_hat.png")).setRegistryName(new ResourceLocation(Artifacts.MODID, "plastic_drinking_hat")),
                new DrinkingHatItem(new ResourceLocation(Artifacts.MODID, "textures/entity/curio/novelty_drinking_hat.png")).setRegistryName(new ResourceLocation(Artifacts.MODID, "novelty_drinking_hat")),
                new SnorkelItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "snorkel")),
                new NightVisionGogglesItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "night_vision_goggles")),
                new VillagerHatItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "villager_hat")),
                new SuperstitiousHatItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "superstitious_hat")),

                // necklace
                new LuckyScarfItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "lucky_scarf")),
                new ScarfOfInvisibilityItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "scarf_of_invisibility")),
                new CrossNecklaceItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "cross_necklace")),
                new PanicNecklaceItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "panic_necklace")),
                new ShockPendantItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "shock_pendant")),
                new FlamePendantItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "flame_pendant")),
                new ThornPendantItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "thorn_pendant")),

                // belt
                new ObsidianSkullItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "obsidian_skull")),
                new AntidoteVesselItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "antidote_vessel")),
                new UniversalAttractorItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "universal_attractor")),
                new CrystalHeartItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "crystal_heart")),
                new CloudInABottleItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "cloud_in_a_bottle")),

                // hands
                new DiggingClawsItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "digging_claws")),
                new FeralClawsItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "feral_claws")),
                new PowerGloveItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "power_glove")),
                new FireGauntletItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "fire_gauntlet")),
                new PocketPistonItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "pocket_piston")),

                // feet
                new BunnyHoppersItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "bunny_hoppers")),
                new KittySlippersItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "kitty_slippers")),
                new RunningShoesItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "running_shoes")),
                new SteadfastSpikesItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "steadfast_spikes")),
                new FlippersItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "flippers")),

                // curio
                new WhoopeeCushionItem().setRegistryName(new ResourceLocation(Artifacts.MODID, "whoopee_cushion"))
        );
    }
}
