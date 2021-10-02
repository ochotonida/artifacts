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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

public class ServerConfig {

    public Set<Item> cosmetics = Collections.emptySet();

    public final Map<Item, ItemConfig> items = new HashMap<>();

    public final AntidoteVesselConfig antidoteVessel;
    public final ItemConfig aquaDashers;
    public final BunnyHoppersConfig bunnyHoppers;
    public final ItemConfig charmOfSinking;
    public final CloudInABottleConfig cloudInABottle;
    public final CrossNecklaceConfig crossNecklace;
    public final CrystalHeartConfig crystalHeart;
    public final DiggingClawsConfig diggingClaws;
    public final EverlastingFoodConfig eternalSteak;
    public final EverlastingFoodConfig everlastingBeef;
    public final FeralClawsConfig feralClaws;
    public final FireGauntletConfig fireGauntlet;
    public final FlamePendantConfig flamePendant;
    public final FlippersConfig flippers;
    public final GoldenHookConfig goldenHook;
    public final HeliumFlamingoConfig heliumFlamingo;
    public final ItemConfig kittySlippers;
    public final LuckyScarfConfig luckyScarf;
    public final ItemConfig nightVisionGoggles;
    public final DrinkingHatConfig noveltyDrinkingHat;
    public final ObsidianSkullConfig obsidianSkull;
    public final PanicNecklaceConfig panicNecklace;
    public final DrinkingHatConfig plasticDrinkingHat;
    public final PocketPistonConfig pocketPiston;
    public final PowerGloveConfig powerGlove;
    public final RunningShoesConfig runningShoes;
    public final ItemConfig scarfOfInvisibility;
    public final ShockPendantConfig shockPendant;
    public final ItemConfig snorkel;
    public final ItemConfig steadfastSpikes;
    public final SuperstitiousHatConfig superstitiousHat;
    public final ThornPendantConfig thornPendant;
    public final UmbrellaConfig umbrella;
    public final UniversalAttractorConfig universalAttractor;
    public final VampiricGloveConfig vampiricGlove;
    public final VillagerHatConfig villagerHat;
    public final WhoopeeCushionConfig whoopeeCushion;

    public final Map<Item, EverlastingFoodConfig> everlastingFoods = new HashMap<>();
    public final Map<Item, DrinkingHatConfig> drinkingHats = new HashMap<>();
    public final Map<Item, PendantConfig> pendants = new HashMap<>();

    private final ForgeConfigSpec.ConfigValue<List<String>> cosmeticsValue;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        cosmeticsValue = builder
                .worldRestart()
                .comment(
                        "List of cosmetic-only items. All items in this list will have their effects disabled",
                        "To blacklist all items, use \"artifacts:*\"",
                        "Note: blacklisting an item while it is equipped may have unintended side effects"
                )
                .translation(Artifacts.MODID + ".config.server.cosmetics")
                .define("cosmetics", Lists.newArrayList(""));

        antidoteVessel = new AntidoteVesselConfig(builder);
        aquaDashers = new ItemConfig(builder, ModItems.AQUA_DASHERS, "Affects how many seconds the player can run on fluids using the aqua dashers before breaking");
        bunnyHoppers = new BunnyHoppersConfig(builder);
        charmOfSinking = new ItemConfig(builder, ModItems.CHARM_OF_SINKING, "Affects how many seconds the player can stay underwater using the charm of sinking before breaking");
        cloudInABottle = new CloudInABottleConfig(builder);
        crossNecklace = new CrossNecklaceConfig(builder);
        crystalHeart = new CrystalHeartConfig(builder);
        diggingClaws = new DiggingClawsConfig(builder);
        eternalSteak = new EverlastingFoodConfig(builder, ModItems.ETERNAL_STEAK);
        everlastingBeef = new EverlastingFoodConfig(builder, ModItems.EVERLASTING_BEEF);
        feralClaws = new FeralClawsConfig(builder);
        fireGauntlet = new FireGauntletConfig(builder);
        flamePendant = new FlamePendantConfig(builder);
        flippers = new FlippersConfig(builder);
        goldenHook = new GoldenHookConfig(builder);
        heliumFlamingo = new HeliumFlamingoConfig(builder);
        kittySlippers = new ItemConfig(builder, ModItems.KITTY_SLIPPERS, "Affects how many creepers the player can attack using the kitty slippers before breaking");
        luckyScarf = new LuckyScarfConfig(builder);
        nightVisionGoggles = new ItemConfig(builder, ModItems.NIGHT_VISION_GOGGLES, "Affects how many seconds the night vision effect should apply before breaking");
        noveltyDrinkingHat = new DrinkingHatConfig(builder, ModItems.NOVELTY_DRINKING_HAT);
        obsidianSkull = new ObsidianSkullConfig(builder);
        panicNecklace = new PanicNecklaceConfig(builder);
        plasticDrinkingHat = new DrinkingHatConfig(builder, ModItems.PLASTIC_DRINKING_HAT);
        pocketPiston = new PocketPistonConfig(builder);
        powerGlove = new PowerGloveConfig(builder);
        runningShoes = new RunningShoesConfig(builder);
        scarfOfInvisibility = new ItemConfig(builder, ModItems.SCARF_OF_INVISIBILITY, "Affects how many seconds the invisibility effect should apply before breaking");
        shockPendant = new ShockPendantConfig(builder);
        snorkel = new ItemConfig(builder, ModItems.SNORKEL, "Affects how many seconds the player can stay underwater using the snorkel before breaking");
        steadfastSpikes = new ItemConfig(builder, ModItems.STEADFAST_SPIKES, "Affects how many times the player can be hit while wearing steadfast spikes before breaking");
        superstitiousHat = new SuperstitiousHatConfig(builder);
        thornPendant = new ThornPendantConfig(builder);
        umbrella = new UmbrellaConfig(builder);
        universalAttractor = new UniversalAttractorConfig(builder);
        vampiricGlove = new VampiricGloveConfig(builder);
        villagerHat = new VillagerHatConfig(builder);
        whoopeeCushion = new WhoopeeCushionConfig(builder);

        drinkingHats.put(ModItems.NOVELTY_DRINKING_HAT.get(), noveltyDrinkingHat);
        drinkingHats.put(ModItems.PLASTIC_DRINKING_HAT.get(), plasticDrinkingHat);
        everlastingFoods.put(ModItems.ETERNAL_STEAK.get(), eternalSteak);
        everlastingFoods.put(ModItems.EVERLASTING_BEEF.get(), everlastingBeef);
        pendants.put(ModItems.FLAME_PENDANT.get(), flamePendant);
        pendants.put(ModItems.SHOCK_PENDANT.get(), shockPendant);
        pendants.put(ModItems.THORN_PENDANT.get(), thornPendant);

        addItemConfigs();

        builder.pop();
    }

    private void addItemConfigs() {
        items.put(ModItems.ANTIDOTE_VESSEL.get(), antidoteVessel);
        items.put(ModItems.AQUA_DASHERS.get(), aquaDashers);
        items.put(ModItems.BUNNY_HOPPERS.get(), bunnyHoppers);
        items.put(ModItems.CHARM_OF_SINKING.get(), charmOfSinking);
        items.put(ModItems.CLOUD_IN_A_BOTTLE.get(), cloudInABottle);
        items.put(ModItems.CROSS_NECKLACE.get(), crossNecklace);
        items.put(ModItems.CRYSTAL_HEART.get(), crystalHeart);
        items.put(ModItems.DIGGING_CLAWS.get(), diggingClaws);
        items.put(ModItems.FERAL_CLAWS.get(), feralClaws);
        items.put(ModItems.FIRE_GAUNTLET.get(), fireGauntlet);
        items.put(ModItems.FLAME_PENDANT.get(), flamePendant);
        items.put(ModItems.FLIPPERS.get(), flippers);
        items.put(ModItems.GOLDEN_HOOK.get(), goldenHook);
        items.put(ModItems.HELIUM_FLAMINGO.get(), heliumFlamingo);
        items.put(ModItems.KITTY_SLIPPERS.get(), kittySlippers);
        items.put(ModItems.LUCKY_SCARF.get(), luckyScarf);
        items.put(ModItems.NIGHT_VISION_GOGGLES.get(), nightVisionGoggles);
        items.put(ModItems.OBSIDIAN_SKULL.get(), obsidianSkull);
        items.put(ModItems.PANIC_NECKLACE.get(), panicNecklace);
        items.put(ModItems.POCKET_PISTON.get(), pocketPiston);
        items.put(ModItems.POWER_GLOVE.get(), powerGlove);
        items.put(ModItems.RUNNING_SHOES.get(), runningShoes);
        items.put(ModItems.SCARF_OF_INVISIBILITY.get(), scarfOfInvisibility);
        items.put(ModItems.SHOCK_PENDANT.get(), shockPendant);
        items.put(ModItems.SNORKEL.get(), snorkel);
        items.put(ModItems.STEADFAST_SPIKES.get(), steadfastSpikes);
        items.put(ModItems.SUPERSTITIOUS_HAT.get(), superstitiousHat);
        items.put(ModItems.THORN_PENDANT.get(), thornPendant);
        items.put(ModItems.UMBRELLA.get(), umbrella);
        items.put(ModItems.UNIVERSAL_ATTRACTOR.get(), universalAttractor);
        items.put(ModItems.VAMPIRIC_GLOVE.get(), vampiricGlove);
        items.put(ModItems.VILLAGER_HAT.get(), villagerHat);
        items.put(ModItems.WHOOPEE_CUSHION.get(), whoopeeCushion);

        items.putAll(drinkingHats);
        items.putAll(everlastingFoods);
        items.putAll(pendants);
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
