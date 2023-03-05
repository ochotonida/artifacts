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
    public final BunnyHoppersConfig bunnyHoppers;
    public final CloudInABottleConfig cloudInABottle;
    public final CrossNecklaceConfig crossNecklace;
    public final CrystalHeartConfig crystalHeart;
    public final DiggingClawsConfig diggingClaws;
    public final FeralClawsConfig feralClaws;
    public final FireGauntletConfig fireGauntlet;
    public final FlamePendantConfig flamePendant;
    public final FlippersConfig flippers;
    public final GoldenHookConfig goldenHook;
    public final HeliumFlamingoConfig heliumFlamingo;
    public final LuckyScarfConfig luckyScarf;
    public final ObsidianSkullConfig obsidianSkull;
    public final PanicNecklaceConfig panicNecklace;
    public final PocketPistonConfig pocketPiston;
    public final PowerGloveConfig powerGlove;
    public final RunningShoesConfig runningShoes;
    public final ShockPendantConfig shockPendant;
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
        bunnyHoppers = new BunnyHoppersConfig(builder);
        cloudInABottle = new CloudInABottleConfig(builder);
        crossNecklace = new CrossNecklaceConfig(builder);
        crystalHeart = new CrystalHeartConfig(builder);
        diggingClaws = new DiggingClawsConfig(builder);
        EverlastingFoodConfig eternalSteak = new EverlastingFoodConfig(builder, ModItems.ETERNAL_STEAK);
        EverlastingFoodConfig everlastingBeef = new EverlastingFoodConfig(builder, ModItems.EVERLASTING_BEEF);
        feralClaws = new FeralClawsConfig(builder);
        fireGauntlet = new FireGauntletConfig(builder);
        flamePendant = new FlamePendantConfig(builder);
        flippers = new FlippersConfig(builder);
        goldenHook = new GoldenHookConfig(builder);
        heliumFlamingo = new HeliumFlamingoConfig(builder);
        luckyScarf = new LuckyScarfConfig(builder);
        DrinkingHatConfig noveltyDrinkingHat = new DrinkingHatConfig(builder, ModItems.NOVELTY_DRINKING_HAT);
        obsidianSkull = new ObsidianSkullConfig(builder);
        panicNecklace = new PanicNecklaceConfig(builder);
        DrinkingHatConfig plasticDrinkingHat = new DrinkingHatConfig(builder, ModItems.PLASTIC_DRINKING_HAT);
        pocketPiston = new PocketPistonConfig(builder);
        powerGlove = new PowerGloveConfig(builder);
        runningShoes = new RunningShoesConfig(builder);
        shockPendant = new ShockPendantConfig(builder);
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

        builder.pop();
    }

    public void bake() {
        if (cosmeticsValue.get().contains("artifacts:*")) {
            // noinspection ConstantConditions
            cosmetics = ForgeRegistries.ITEMS.getValues()
                    .stream()
                    .filter(item -> ForgeRegistries.ITEMS.getKey(item).getNamespace().equals(Artifacts.MODID))
                    .collect(Collectors.toSet());
        } else {
            cosmetics = cosmeticsValue.get()
                    .stream()
                    .map(ResourceLocation::new)
                    .filter(registryName -> registryName.getNamespace().equals(Artifacts.MODID))
                    .map(ForgeRegistries.ITEMS::getValue)
                    .collect(Collectors.toSet());
        }

        diggingClaws.bake();
    }

    public boolean isCosmetic(Item item) {
        return cosmetics.contains(item);
    }
}
