package artifacts.common.config;

import artifacts.Artifacts;
import artifacts.common.config.item.EverlastingFoodConfig;
import artifacts.common.config.item.ItemConfig;
import artifacts.common.config.item.UmbrellaConfig;
import artifacts.common.config.item.curio.WhoopeeCushionConfig;
import artifacts.common.config.item.curio.belt.*;
import artifacts.common.config.item.curio.feet.BunnyHoppersConfig;
import artifacts.common.config.item.curio.feet.FlippersConfig;
import artifacts.common.config.item.curio.feet.RunningShoesConfig;
import artifacts.common.config.item.curio.hands.*;
import artifacts.common.config.item.curio.head.DrinkingHatConfig;
import artifacts.common.config.item.curio.head.SuperstitiousHatConfig;
import artifacts.common.config.item.curio.head.VillagerHatConfig;
import artifacts.common.config.item.curio.necklace.*;
import artifacts.common.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

public class ServerConfig {

    public Set<Item> cosmetics = Collections.emptySet();

    public final Map<Item, ItemConfig> items;

    public final AntidoteVesselConfig antidoteVessel;
    public final BunnyHoppersConfig bunnyHoppers;
    public final CloudInABottleConfig cloudInABottle;
    public final CrossNecklaceConfig crossNecklace;
    public final CrystalHeartConfig crystalHeart;
    public final DiggingClawsConfig diggingClaws;
    public final Map<Item, DrinkingHatConfig> drinkingHats;
    public final Map<Item, EverlastingFoodConfig> everlastingFoods;
    public final FeralClawsConfig feralClaws;
    public final FireGauntletConfig fireGauntlet;
    public final FlamePendantConfig flamePendant;
    public final FlippersConfig flippers;
    public final GoldenHookConfig goldenHook;
    public final HeliumFlamingoConfig heliumFlamingo;
    public final LuckyScarfConfig luckyScarf;
    public final ObsidianSkullConfig obsidianSkull;
    public final PanicNecklaceConfig panicNecklace;
    public final Map<Item, PendantConfig> pendants;
    public final PocketPistonConfig pocketPiston;
    public final PowerGloveConfig powerGlove;
    public final RunningShoesConfig runningShoes;
    public final SuperstitiousHatConfig superstitiousHat;
    public final ThornPendantConfig thornPendant;
    public final UmbrellaConfig umbrella;
    public final UniversalAttractorConfig universalAttractor;
    public final VampiricGloveConfig vampiricGlove;
    public final VillagerHatConfig villagerHat;
    public final WhoopeeCushionConfig whoopeeCushion;

    private final ForgeConfigSpec.ConfigValue<List<String>> cosmeticsValue;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        cosmeticsValue = builder
                .worldRestart()
                .comment(
                        "List of cosmetic-only items. All items in this list will have their effects disabled",
                        "To blacklist all items, use \"artifacts:*\"",
                        "Note: blacklisting an item while it is equipped may have unintended side effects",
                        "To completely prevent items from appearing, use a data pack"
                )
                .translation(Artifacts.MODID + ".config.server.cosmetics")
                .define("cosmetics", Lists.newArrayList(""));

        items = new HashMap<>();
        drinkingHats = new HashMap<>();
        everlastingFoods = new HashMap<>();
        pendants = new HashMap<>();

        antidoteVessel = addItemConfig(new AntidoteVesselConfig(builder));
        addItemConfig(new ItemConfig(builder, ModItems.AQUA_DASHERS.get(), "Affects how many seconds the player can run on fluids using the aqua dashers before breaking"));
        bunnyHoppers = addItemConfig(new BunnyHoppersConfig(builder));
        addItemConfig(new ItemConfig(builder, ModItems.CHARM_OF_SINKING.get(), "Affects how many seconds the player can stay underwater using the charm of sinking before breaking"));
        cloudInABottle = addItemConfig(new CloudInABottleConfig(builder));
        crossNecklace = addItemConfig(new CrossNecklaceConfig(builder));
        crystalHeart = addItemConfig(new CrystalHeartConfig(builder));
        diggingClaws = addItemConfig(new DiggingClawsConfig(builder));

        drinkingHats.put(ModItems.PLASTIC_DRINKING_HAT.get(), addItemConfig(new DrinkingHatConfig(builder, ModItems.PLASTIC_DRINKING_HAT.get())));
        drinkingHats.put(ModItems.NOVELTY_DRINKING_HAT.get(), addItemConfig(new DrinkingHatConfig(builder, ModItems.NOVELTY_DRINKING_HAT.get())));

        everlastingFoods.put(ModItems.EVERLASTING_BEEF.get(), addItemConfig(new EverlastingFoodConfig(builder, ModItems.EVERLASTING_BEEF.get())));
        everlastingFoods.put(ModItems.ETERNAL_STEAK.get(), addItemConfig(new EverlastingFoodConfig(builder, ModItems.ETERNAL_STEAK.get())));

        feralClaws = addItemConfig(new FeralClawsConfig(builder));
        fireGauntlet = addItemConfig(new FireGauntletConfig(builder));
        flamePendant = addItemConfig(new FlamePendantConfig(builder));
        pendants.put(ModItems.FLAME_PENDANT.get(), flamePendant);
        flippers = addItemConfig(new FlippersConfig(builder));
        goldenHook = addItemConfig(new GoldenHookConfig(builder));
        heliumFlamingo = addItemConfig(new HeliumFlamingoConfig(builder));
        addItemConfig(new ItemConfig(builder, ModItems.KITTY_SLIPPERS.get(), "Affects how many creepers the player can attack using the kitty slippers before breaking"));
        luckyScarf = addItemConfig(new LuckyScarfConfig(builder));
        addItemConfig(new ItemConfig(builder, ModItems.NIGHT_VISION_GOGGLES.get(), "Affects how many seconds the night vision effect should apply before breaking"));
        obsidianSkull = addItemConfig(new ObsidianSkullConfig(builder));
        panicNecklace = addItemConfig(new PanicNecklaceConfig(builder));
        pocketPiston = addItemConfig(new PocketPistonConfig(builder));
        powerGlove = addItemConfig(new PowerGloveConfig(builder));
        runningShoes = addItemConfig(new RunningShoesConfig(builder));
        addItemConfig(new ItemConfig(builder, ModItems.SCARF_OF_INVISIBILITY.get(), "Affects how many seconds the invisibility effect should apply before breaking"));
        pendants.put(ModItems.SHOCK_PENDANT.get(), addItemConfig(new ShockPendantConfig(builder)));
        addItemConfig(new ItemConfig(builder, ModItems.SNORKEL.get(), "Affects how many seconds the player can stay underwater using the snorkel before breaking"));
        addItemConfig(new ItemConfig(builder, ModItems.STEADFAST_SPIKES.get(), "Affects how many times the player can be hit while wearing steadfast spikes before breaking"));
        superstitiousHat = addItemConfig(new SuperstitiousHatConfig(builder));
        thornPendant = addItemConfig(new ThornPendantConfig(builder));
        pendants.put(ModItems.THORN_PENDANT.get(), thornPendant);
        umbrella = addItemConfig(new UmbrellaConfig(builder));
        universalAttractor = addItemConfig(new UniversalAttractorConfig(builder));
        vampiricGlove = addItemConfig(new VampiricGloveConfig(builder));
        villagerHat = addItemConfig(new VillagerHatConfig(builder));
        whoopeeCushion = addItemConfig(new WhoopeeCushionConfig(builder));

        builder.pop();
    }

    private <T extends ItemConfig> T addItemConfig(T config) {
        items.put(config.getItem(), config);
        return config;
    }

    public void bake() {
        if (cosmeticsValue.get().contains("artifacts:*")) {
            // noinspection ConstantConditions
            cosmetics = ForgeRegistries.ITEMS.getValues()
                    .stream()
                    .filter(item -> item.getRegistryName().getNamespace().equals(Artifacts.MODID))
                    .collect(Collectors.toSet());
        } else {
            cosmetics = cosmeticsValue.get()
                    .stream()
                    .map(ResourceLocation::new)
                    .filter(registryName -> registryName.getNamespace().equals(Artifacts.MODID))
                    .map(ForgeRegistries.ITEMS::getValue)
                    .collect(Collectors.toSet());
        }

        items.forEach((item, config) -> config.bake());
    }

    public boolean isCosmetic(Item item) {
        return cosmetics.contains(item);
    }
}
