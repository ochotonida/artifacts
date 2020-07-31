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

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                new SpawnEggItem(Entities.MIMIC, 0x805113, 0x212121, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(new ResourceLocation(Artifacts.MODID, "mimic_spawn_egg")),
                new UmbrellaItem(),
                new EverlastingFoodItem(new Item.Properties().food(Foods.BEEF), "everlasting_beef"),
                new EverlastingFoodItem(new Item.Properties().food(Foods.COOKED_BEEF), "eternal_steak"),
                new DrinkingHatItem("plastic_drinking_hat", new ResourceLocation(Artifacts.MODID, "textures/entity/curio/plastic_drinking_hat.png")),
                new DrinkingHatItem("novelty_drinking_hat", new ResourceLocation(Artifacts.MODID, "textures/entity/curio/novelty_drinking_hat.png")),
                new SnorkelItem(),
                new NightVisionGogglesItem(),
                new SuperstitiousHatItem(),
                new LuckyScarfItem(),
                new ScarfOfInvisibilityItem(),
                new CrossNecklaceItem(),
                new PanicNecklaceItem(),
                new ShockPendantItem(),
                new FlamePendantItem(),
                new ThornPendantItem(),
                new ObsidianSkullItem(),
                new AntidoteVesselItem(),
                new UniversalAttractorItem(),
                new CrystalHeartItem(),
                new DiggingClawsItem(),
                new FeralClawsItem(),
                new PowerGloveItem(),
                new FireGauntletItem(),
                new PocketPistonItem(),
                new BunnyHoppersItem(),
                new KittySlippersItem(),
                new RunningShoesItem(),
                new SteadfastSpikesItem(),
                new FlippersItem()
        );
    }
}
