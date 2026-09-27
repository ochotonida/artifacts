package artifacts.config;

import artifacts.Artifacts;
import artifacts.component.ability.ToolTierUpgrade;
import artifacts.component.ability.retaliation.RetaliationEffect;
import artifacts.config.display.SharedConfigLang;
import artifacts.config.value.ConfigValue;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.item.ItemDamageProperties;
import artifacts.lang.LangEntry;
import artifacts.registry.ModItems;
import artifacts.registry.RegistryHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO: max damage default values
public final class ItemConfigs extends ConfigManager {

    private final Map<ResourceKey<Item>, ItemSubCategory> itemCategories = new HashMap<>();

    public final AnglersHat anglersHat = new AnglersHat();
    public final AntidoteVessel antidoteVessel = new AntidoteVessel();
    public final AquaDashers aquaDashers = new AquaDashers();
    public final BunnyHoppers bunnyHoppers = new BunnyHoppers();
    public final CharmOfShrinking charmOfShrinking = new CharmOfShrinking();
    public final CharmOfSinking charmOfSinking = new CharmOfSinking();
    public final ChorusTotem chorusTotem = new ChorusTotem();
    public final CloudInABottle cloudInABottle = new CloudInABottle();
    public final CowboyHat cowboyHat = new CowboyHat();
    public final CrossNecklace crossNecklace = new CrossNecklace();
    public final CrystalHeart crystalHeart = new CrystalHeart();
    public final DiggingClaws diggingClaws = new DiggingClaws();
    public final EternalSteak eternalSteak = new EternalSteak();
    public final EverlastingBeef everlastingBeef = new EverlastingBeef();
    public final FeralClaws feralClaws = new FeralClaws();
    public final FireGauntlet fireGauntlet = new FireGauntlet();
    public final FlamePendant flamePendant = new FlamePendant();
    public final Flippers flippers = new Flippers();
    public final GoldenHook goldenHook = new GoldenHook();
    public final HeliumFlamingo heliumFlamingo = new HeliumFlamingo();
    public final KittySlippers kittySlippers = new KittySlippers();
    public final LuckyScarf luckyScarf = new LuckyScarf();
    public final NightVisionGoggles nightVisionGoggles = new NightVisionGoggles();
    public final DrinkingHat noveltyDrinkingHat = new DrinkingHat(ModItems.NOVELTY_DRINKING_HAT, "Novelty Drinking Hat");
    public final ObsidianSkull obsidianSkull = new ObsidianSkull();
    public final OnionRing onionRing = new OnionRing();
    public final PanicNecklace panicNecklace = new PanicNecklace();
    public final PickaxeHeater pickaxeHeater = new PickaxeHeater();
    public final DrinkingHat plasticDrinkingHat = new DrinkingHat(ModItems.PLASTIC_DRINKING_HAT, "Plastic Drinking Hat");
    public final PocketPiston pocketPiston = new PocketPiston();
    public final PowerGlove powerGlove = new PowerGlove();
    public final RootedBoots rootedBoots = new RootedBoots();
    public final RunningShoes runningShoes = new RunningShoes();
    public final ScarfOfInvisibility scarfOfInvisibility = new ScarfOfInvisibility();
    public final ShockPendant shockPendant = new ShockPendant();
    public final Snorkel snorkel = new Snorkel();
    public final Snowshoes snowshoes = new Snowshoes();
    public final SteadfastSpikes steadfastSpikes = new SteadfastSpikes();
    public final StriderShoes striderShoes = new StriderShoes();
    public final SuperstitiousHat superstitiousHat = new SuperstitiousHat();
    public final ThornPendant thornPendant = new ThornPendant();
    public final Umbrella umbrella = new Umbrella();
    public final UniversalAttractor universalAttractor = new UniversalAttractor();
    public final VampiricGlove vampiricGlove = new VampiricGlove();
    public final VillagerHat villagerHat = new VillagerHat();
    public final WarpDrive warpDrive = new WarpDrive();
    public final WhoopeeCushion whoopeeCushion = new WhoopeeCushion();
    public final WitheredBracelet witheredBracelet = new WitheredBracelet();

    ItemConfigs() {
        super("items");
    }

    public final class AnglersHat extends ItemSubCategory {

        public final ConfigValue<Integer> luckOfTheSeaLevelBonus = define("luckOfTheSeaLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of luck of the sea that are granted by the Angler's Hat")
                .syncToClients().build();

        public final ConfigValue<Integer> lureLevelBonus = define("lureLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of lure that are granted by the Angler's Hat")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private AnglersHat() {
            super(ModItems.ANGLERS_HAT);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerItemFished = define("damagePerItemFished", 1)
                    .descriptionLine("The amount of durability lost every time the wearer fishes an item")
                    .build();

            private Durability() {
                super(320);
            }
        }
    }

    public final class AntidoteVessel extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Antidote Vessel reduces the duration of negative effects")
                .syncToClients().build();

        public final ConfigValue<Integer> maxEffectDuration = define("maxEffectDuration", ValueTypes.DURATION, 5)
                .descriptionLine("The maximum duration in seconds negative mob effects can last when wearing the Antidote Vessel")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(100);

        private AntidoteVessel() {
            super(ModItems.ANTIDOTE_VESSEL);
        }
    }

    public final class AquaDashers extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Aqua-Dashers allow the wearer to sprint on water")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private AquaDashers() {
            super(ModItems.AQUA_DASHERS);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(1)
                    .descriptionLine("The amount of durability lost per second when walking on water")
                    .build();

            private Durability() {
                super(1800);
            }
        }
    }

    public final class BunnyHoppers extends ItemSubCategory {

        public final ConfigValue<Boolean> modifyHurtSounds = define("modifyHurtSounds", true)
                .descriptionLine("Whether the Bunny Hoppers change the player's hurt sounds")
                .syncToClients().build();

        public final ConfigValue<Double> fallDamageMultiplier = define("fallDamageMultiplier", ValueTypes.ATTRIBUTE_MODIFIER, 0D)
                .descriptionLine("How much the Bunny Hoppers reduce or increase fall damage")
                .descriptionLine("Values between -1 and 0 reduce fall damage")
                .descriptionLine("Values above 0 increase fall damage")
                .syncToClients().build();

        public final ConfigValue<Double> jumpStrengthBonus = define("jumpStrengthBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.40)
                .descriptionLine("The amount of extra jump strength the Bunny Hoppers apply to players")
                .syncToClients().build();

        public final ConfigValue<Double> safeFallDistanceBonus = define("safeFallDistanceBonus", ValueTypes.ATTRIBUTE_MODIFIER, 10D)
                .descriptionLine("The amount of extra safe fall distance in blocks that is granted by the Bunny Hoppers")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private BunnyHoppers() {
            super(ModItems.BUNNY_HOPPERS);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerJump = define("damagePerJump", 0)
                    .descriptionLine("The amount of durability lost per jump")
                    .build();

            public final ConfigValue<Integer> damagePerFallSurvived = define("damagePerFallSurvived", 1)
                    .descriptionLine("The amount of durability lost after falling for more than 3 blocks without taking damage")
                    .build();

            private Durability() {
                super(200);
            }
        }
    }

    public final class CharmOfShrinking extends ItemSubCategory {

        public final ConfigValue<Double> scaleModifier = define("scaleModifier", ValueTypes.ATTRIBUTE_MODIFIER, -0.50)
                .descriptionLine("How much the Charm of Shrinking decreases or increases the player's Scale")
                .descriptionLine("Values between -1 and 0 reduce the player's scale")
                .descriptionLine("Values above 0 increase the player's scale")
                .syncToClients().build();

        // 1800 at 0.1/s = 5 hours
        public final ToggleBasedDurabilityCategory durability = new ToggleBasedDurabilityCategory(1800, 0.1);

        private CharmOfShrinking() {
            super(ModItems.CHARM_OF_SHRINKING);
        }
    }

    public final class CharmOfSinking extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Charm of Sinking removes the wearer's collision with water")
                .syncToClients()
                .requiresRestart().build();

        public final ConfigValue<Boolean> underwaterFallDamage = define("underwaterFallDamage", false)
                .descriptionLine("Whether it is possible to take fall damage underwater when wearing the Charm of Sinking")
                .syncToClients()
                .requiresRestart().build();

        public final ConfigValue<Double> oxygenBonus = define("oxygenBonus", ValueTypes.ATTRIBUTE_MODIFIER, 1.5)
                .descriptionLine("How much longer players wearing the Charm of Sinking can stay underwater")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private CharmOfSinking() {
            super(ModItems.CHARM_OF_SINKING);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(0.1)
                    .descriptionLine("The amount of durability lost per second spent underwater")
                    .build();

            private Durability() {
                // 900 at 0.1/s = 2.5 hours
                super(900);
            }
        }
    }

    public final class ChorusTotem extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Chorus Totem prevents the wearer's death")
                .syncToClients()
                .requiresRestart().build();

        public final ConfigValue<Integer> healthRestored = define("healthRestored", ValueTypes.NON_NEGATIVE_INT, 9)
                .descriptionLine("The amount of health points that are restored after the Chorus Totem activates")
                .syncToClients().build();

        private ChorusTotem() {
            super(ModItems.CHORUS_TOTEM);
        }
    }

    public final class CloudInABottle extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Cloud in a Bottle allows the wearer to double jump")
                .syncToClients().build();

        public final ConfigValue<Double> sprintJumpVerticalVelocity
                = define("sprintJumpVerticalVelocity", ValueTypes.NON_NEGATIVE_DOUBLE, 0.25)
                .descriptionLine("The amount of extra vertical velocity that is applied to players that double jump while sprinting using the Cloud in a Bottle")
                .syncToClients().build();

        public final ConfigValue<Double> sprintJumpHorizontalVelocity
                = define("sprintJumpHorizontalVelocity", ValueTypes.NON_NEGATIVE_DOUBLE, 0.25)
                .descriptionLine("The amount of extra horizontal velocity that is applied to players that double jump while sprinting using the Cloud in a Bottle")
                .syncToClients().build();

        public final ConfigValue<Double> safeFallDistanceBonus = define("safeFallDistanceBonus", ValueTypes.ATTRIBUTE_MODIFIER, 3D)
                .descriptionLine("The amount of extra safe fall distance in blocks that is granted by the Cloud in a Bottle")
                .syncToClients().build();

        public final ConfigValue<Double> fallDamageMultiplier = define("fallDamageMultiplier", ValueTypes.FRACTION, 0D)
                .descriptionLine("How much fall damage is dealt when double jumping with the Cloud in a Bottle")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private CloudInABottle() {
            super(ModItems.CLOUD_IN_A_BOTTLE);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerDoubleJump = define("damagePerDoubleJump", 1)
                    .descriptionLine("The amount of durability lost for every double jump")
                    .build();

            private Durability() {
                super(120);
            }
        }
    }

    public final class CowboyHat extends ItemSubCategory {

        public final ConfigValue<Double> mountSpeedBonus = define("mountSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.40)
                .descriptionLine("How much the Cowboy Hat increases the speed of ridden mounts")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private CowboyHat() {
            super(ModItems.COWBOY_HAT);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(0.1)
                    .descriptionLine("The amount of durability lost per second while controlling a mount")
                    .build();

            private Durability() {
                // 900 at 0.1 = 2.5 hours
                super(900);
            }
        }
    }

    public final class CrossNecklace extends ItemSubCategory {

        public final ConfigValue<Double> bonusInvincibilityTicks = define("bonusInvincibilityTicks", ValueTypes.ATTRIBUTE_MODIFIER, 20D)
                .descriptionLine("The amount of extra ticks the player stays invincible for after taking damage while wearing the Cross Necklace")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(2)
                .descriptionLine("The duration in seconds the Cross Necklace goes on cooldown for after activating")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(500);

        private CrossNecklace() {
            super(ModItems.CROSS_NECKLACE);
        }
    }

    public final class CrystalHeart extends ItemSubCategory {

        public final ConfigValue<Double> healthBonus = define("healthBonus", ValueTypes.ATTRIBUTE_MODIFIER, 10D)
                .descriptionLine("The amount of extra health points that are granted by the Crystal Heart")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private CrystalHeart() {
            super(ModItems.CRYSTAL_HEART);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damageWhenHurt = define("damageWhenHurt", 1)
                    .descriptionLine("The amount of durability lost every time the wearer takes damage")
                    .build();

            private Durability() {
                super(600);
            }
        }
    }

    public final class DiggingClaws extends ItemSubCategory {

        public final ConfigValue<Double> blockBreakSpeedBonus = define("blockBreakSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.30)
                .descriptionLine("How much the Digging Claws increase the wearer's mining speed")
                .syncToClients().build();

        public final ConfigValue<ToolTierUpgrade.Tier> toolTier = define("toolTier", ValueTypes.TOOL_TIER, ToolTierUpgrade.Tier.STONE)
                .descriptionLine("The tool tier that the Digging Claws increase the wearer's mining level to")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private DiggingClaws() {
            super(ModItems.DIGGING_CLAWS);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerBlockMined = define("damagePerBlockMined", 0)
                    .descriptionLine("The amount of durability lost per block mined")
                    .build();

            public final ConfigValue<Integer> damagePerBlockHarvested = define("damagePerBlockHarvested", 1)
                    .descriptionLine("The amount of durability lost per block mined that would not be harvestable without the digging claws")
                    .build();

            public final ConfigValue<Integer> damagePerOreMined = define("damagePerOreMined", 0)
                    .titleAndDescription(SharedConfigLang.DAMAGE_PER_ORE_MINED)
                    .build();

            private Durability() {
                super(500);
            }
        }
    }

    public final class DrinkingHat extends ItemSubCategory {

        public final ConfigValue<Double> drinkingSpeedBonus;
        public final ConfigValue<Double> eatingSpeedBonus;

        public final Durability durability = new Durability();

        private DrinkingHat(RegistryHolder<Item, Item> item, String itemName) {
            super(item);
            this.drinkingSpeedBonus = define("drinkingSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 1.50)
                    .descriptionLine("How much the %s increases the wearer's drinking speed".formatted(itemName))
                    .syncToClients().build();
            this.eatingSpeedBonus = define("eatingSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.50)
                    .descriptionLine("How much the %s increases the wearer's eating speed".formatted(itemName))
                    .syncToClients().build();
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerItemDrunk
                    = define("damagePerItemDrunk", 1)
                    .titleAndDescription(SharedConfigLang.DAMAGE_PER_ITEM_DRUNK)
                    .build();

            public final ConfigValue<Integer> damagePerItemEaten
                    = define("damagePerItemEaten", 1)
                    .titleAndDescription(SharedConfigLang.DAMAGE_PER_ITEM_EATEN)
                    .build();

            private Durability() {
                super(64 * 4);
            }
        }
    }

    public sealed abstract class EverlastingFood extends ItemSubCategory
            permits ItemConfigs.EverlastingBeef, ItemConfigs.EternalSteak {

        public final ConfigValue<Boolean> enabled;
        public final ConfigValue<Integer> cooldown;

        public final Durability durability;

        public EverlastingFood(RegistryHolder<Item, Item> holder, String itemName) {
            super(holder);
            enabled = defineEnabled(true)
                    .descriptionLine("Whether the %s can be eaten".formatted(itemName))
                    .syncToClients()
                    .requiresRestart().build();
            cooldown = defineCooldown(15)
                    .descriptionLine("The duration in seconds the %s goes on cooldown for after being eaten".formatted(itemName))
                    .syncToClients()
                    .requiresRestart().build();
            durability = new Durability();
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damageWhenConsumed
                    = define("damageWhenConsumed", 1)
                    .titleAndDescription(SharedConfigLang.DAMAGE_WHEN_CONSUMED)
                    .build();

            private Durability() {
                super(64 * 5);
            }
        }
    }

    public final class EternalSteak extends EverlastingFood {

        private EternalSteak() {
            super(ModItems.ETERNAL_STEAK, "Eternal Steak");
        }
    }

    public final class EverlastingBeef extends EverlastingFood {

        public final ConfigValue<Double> dropRate = define("dropRate", ValueTypes.FRACTION, 1 / 500D)
                .descriptionLine("The probability that Everlasting Beef drops when a cow or mooshroom is killed by a player").build();

        private EverlastingBeef() {
            super(ModItems.EVERLASTING_BEEF, "Everlasting Beef");
        }
    }

    public final class FeralClaws extends ItemSubCategory {

        public final ConfigValue<Double> attackSpeedBonus = define("attackSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.30)
                .descriptionLine("How much the Feral Claws increase the wearer's attack speed")
                .syncToClients().build();

        public final AttackBasedDurabilityCategory durability = new AttackBasedDurabilityCategory(1500);

        private FeralClaws() {
            super(ModItems.FERAL_CLAWS);
        }
    }

    public final class FireGauntlet extends ItemSubCategory {

        public final ConfigValue<Double> fireDuration = define("fireDuration", ValueTypes.ATTRIBUTE_MODIFIER, 8D)
                .descriptionLine("How long an entity is set on fire for after being attacked by an entity wearing the Fire Gauntlet")
                .syncToClients().build();

        public final AttackBasedDurabilityCategory durability = new AttackBasedDurabilityCategory(1500);

        private FireGauntlet() {
            super(ModItems.FIRE_GAUNTLET);
        }
    }

    public final class FlamePendant extends ItemSubCategory {

        public final ConfigValue<Double> strikeChance = define("strikeChance", ValueTypes.FRACTION, 0.40)
                .descriptionLine("The probability that the Flame Pendant lights an attacker on fire")
                .syncToClients().build();

        public final ConfigValue<Integer> fireDuration = define("fireDuration", ValueTypes.DURATION, 10)
                .descriptionLine("How long an attacking entity is set on fire for when the Flame Pendant activates")
                .syncToClients().build();

        public final ConfigValue<Boolean> grantFireResistance = define("grantFireResistance", true)
                .descriptionLine("Whether the Flame Pendant grants Fire Resistance after igniting an entity")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(2)
                .descriptionLine("The duration in seconds the Flame Pendant goes on cooldown for after setting an entity on fire")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(120);

        private FlamePendant() {
            super(ModItems.FLAME_PENDANT);
        }

        public RetaliationEffect.ActivationParams activationParams() {
            return new RetaliationEffect.ActivationParams(strikeChance, cooldown, durability.damagePerActivation);
        }
    }

    public final class Flippers extends ItemSubCategory {

        public final ConfigValue<Double> swimSpeedBonus = define("swimSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.70)
                .descriptionLine("How much the Flippers increase the wearer's swim speed")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private Flippers() {
            super(ModItems.FLIPPERS);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(0.1)
                    .descriptionLine("The amount of durability lost per second when swimming")
                    .build();

            private Durability() {
                // 900 at 0.1/s = 2 hours
                super(900);
            }
        }
    }

    public final class GoldenHook extends ItemSubCategory {

        public final ConfigValue<Double> entityExperienceBonus = define("entityExperienceBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.50)
                .descriptionLine("The amount of extra experience dropped by entities that are killed by players wearing the Golden Hook")
                .syncToClients().build();

        public final KillBasedDurabilityCategory durability = new KillBasedDurabilityCategory(300);

        private GoldenHook() {
            super(ModItems.GOLDEN_HOOK);
        }
    }

    public final class HeliumFlamingo extends ItemSubCategory {

        public final ConfigValue<Integer> flightDuration = define("flightDuration", ValueTypes.DURATION, 8)
                .descriptionLine("The amount of time in seconds a player can fly with the Helium Flamingo before needing to recharge")
                .syncToClients().build();

        public final ConfigValue<Integer> rechargeDuration = define("rechargeDuration", ValueTypes.DURATION, 15)
                .descriptionLine("The amount of time in seconds it takes for the Helium Flamingo to recharge")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(3)
                .descriptionLine("The duration in seconds the Helium Flamingo goes on cooldown for when stopping flight")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private HeliumFlamingo() {
            super(ModItems.HELIUM_FLAMINGO);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(1)
                    .descriptionLine("The amount of durability lost per second while flying with the Helium Flamingo")
                    .build();

            private Durability() {
                // 800 at 1/s & 8 seconds per flight = 100 flights
                super(800);
            }
        }
    }

    public final class KittySlippers extends ItemSubCategory {

        public final ConfigValue<Boolean> modifyHurtSounds = define("modifyHurtSounds", true)
                .descriptionLine("Whether the Kitty Slippers change the player's hurt sounds")
                .syncToClients().build();

        public final ConfigValue<Boolean> repelCreepers = define("repelCreepers", true)
                .descriptionLine("Whether the Kitty Slippers scare nearby creepers")
                .syncToClients().build();

        public final ConfigValue<Boolean> repelPhantoms = define("repelPhantoms", true)
                .descriptionLine("Whether the Kitty Slippers hiss at nearby phantoms")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private KittySlippers() {
            super(ModItems.KITTY_SLIPPERS);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerCreeperScared = define("damagePerCreeperScared", 1)
                    .descriptionLine("The amount of durability lost per creeper killed while wearing the kitty slippers")
                    .build();

            public final ConfigValue<Integer> damagePerPhantomScared = define("damagePerPhantomScared", 1)
                    .descriptionLine("The amount of durability lost per phantom killed while wearing the kitty slippers")
                    .build();

            private Durability() {
                super(120);
            }
        }
    }

    public final class LuckyScarf extends ItemSubCategory {

        public final ConfigValue<Integer> fortuneLevelBonus = define("fortuneLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of fortune that are granted by the Lucky Scarf")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private LuckyScarf() {
            super(ModItems.LUCKY_SCARF);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerOreMined = define("damagePerOreMined", 1)
                    .titleAndDescription(SharedConfigLang.DAMAGE_PER_ORE_MINED)
                    .build();

            private Durability() {
                super(256);
            }
        }
    }

    public final class NightVisionGoggles extends ItemSubCategory {

        public final ConfigValue<Double> strength = define("strength", ValueTypes.FRACTION, 0.15)
                .descriptionLine("The strength of the night vision effect applied by the Night Vision Goggles")
                .syncToClients().build();

        // 900 at 0.1/s = 2 hours
        public final ToggleBasedDurabilityCategory durability = new ToggleBasedDurabilityCategory(900, 0.1);

        private NightVisionGoggles() {
            super(ModItems.NIGHT_VISION_GOGGLES);
        }
    }

    public final class ObsidianSkull extends ItemSubCategory {

        public final ConfigValue<Integer> fireResistanceDuration = define("fireResistanceDuration", ValueTypes.DURATION, 30)
                .descriptionLine("The duration of the fire resistance effect that is applied when taking fire damage while wearing the Obsidian Skull")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(60)
                .descriptionLine("The amount of time in seconds the Obsidian Skull goes on cooldown for after taking fire damage")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(60);

        private ObsidianSkull() {
            super(ModItems.OBSIDIAN_SKULL);
        }
    }

    public final class OnionRing extends ItemSubCategory {

        public final ConfigValue<Integer> hasteDurationPerFoodPoint = define("hasteDurationPerFoodPoint", ValueTypes.DURATION, 6)
                .descriptionLine("The duration of haste that is applied per food point eaten while wearing the Onion Ring")
                .syncToClients().build();

        public final ConfigValue<Integer> hasteLevel = define("hasteLevel", ValueTypes.MOB_EFFECT_LEVEL, 2)
                .descriptionLine("The level of the haste effect that is applied by the Onion Ring")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(640);

        private OnionRing() {
            super(ModItems.ONION_RING);
        }
    }

    public final class PanicNecklace extends ItemSubCategory {

        public final ConfigValue<Integer> speedLevel = define("speedLevel", ValueTypes.MOB_EFFECT_LEVEL, 1)
                .descriptionLine("The level of the speed effect that is applied by the Panic Necklace")
                .syncToClients().build();

        public final ConfigValue<Integer> speedDuration = define("speedDuration", ValueTypes.DURATION, 8)
                .descriptionLine("The duration in seconds of the speed effect that is applied when taking damage while wearing the Panic Necklace")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(2)
                .descriptionLine("The duration in seconds the Panic Necklace goes on cooldown for after taking damage")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(500);

        private PanicNecklace() {
            super(ModItems.PANIC_NECKLACE);
        }
    }

    public final class PickaxeHeater extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Pickaxe Heater smelts mined ores")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private PickaxeHeater() {
            super(ModItems.PICKAXE_HEATER);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerOreMined = define("damagePerOreMined", 1)
                    .titleAndDescription(SharedConfigLang.DAMAGE_PER_ORE_MINED)
                    .build();

            private Durability() {
                super(256);
            }
        }
    }

    public final class PocketPiston extends ItemSubCategory {

        public final ConfigValue<Double> attackKnockbackBonus = define("attackKnockbackBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.75)
                .descriptionLine("The amount of extra knockback that is granted by the Pocket Piston")
                .syncToClients().build();

        public final AttackBasedDurabilityCategory durability = new AttackBasedDurabilityCategory(1500);

        private PocketPiston() {
            super(ModItems.POCKET_PISTON);
        }
    }

    public final class PowerGlove extends ItemSubCategory {

        public final ConfigValue<Double> attackDamageBonus = define("attackDamageBonus", ValueTypes.ATTRIBUTE_MODIFIER, 4D)
                .descriptionLine("The amount of extra damage that is dealt by melee attacks from players wearing the Power Glove")
                .syncToClients().build();

        public final AttackBasedDurabilityCategory durability = new AttackBasedDurabilityCategory(1500);

        private PowerGlove() {
            super(ModItems.POWER_GLOVE);
        }
    }

    public final class RootedBoots extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Rooted Boots replenish hunger when standing on grass")
                .syncToClients().build();

        public final ConfigValue<Integer> hungerReplenishingDuration = define("hungerReplenishingDuration", ValueTypes.DURATION, 10)
                .descriptionLine("The amount of time in seconds it takes to replenish a single point of hunger while wearing the Rooted Boots")
                .syncToClients().build();

        public final ConfigValue<Boolean> growPlantsAfterEating = define("growPlantsAfterEating", true)
                .descriptionLine("Whether the Rooted Boots apply a bone meal effect after eating food")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private RootedBoots() {
            super(ModItems.ROOTED_BOOTS);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerFoodPoint = define("damagePerFoodPoint", 1)
                    .descriptionLine("The amount of durability lost every time the Rooted Boots restore a food point")
                    .build();

            private Durability() {
                super(20 * 20);
            }
        }
    }

    public final class RunningShoes extends ItemSubCategory {

        public final ConfigValue<Double> sprintingSpeedBonus = define("sprintingSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.40)
                .descriptionLine("How much the Running Shoes increase the wearer's sprinting speed")
                .syncToClients().build();

        public final ConfigValue<Double> sprintingStepHeightBonus = define("sprintingStepHeightBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.5)
                .descriptionLine("How much the Running Shoes increase the wearer's step height while sprinting")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private RunningShoes() {
            super(ModItems.RUNNING_SHOES);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(0.1)
                    .descriptionLine("The amount of durability lost per second when sprinting")
                    .build();

            private Durability() {
                // 900 at 0.1/s = 2.5 hours
                super(900);
            }
        }
    }

    public final class ScarfOfInvisibility extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Scarf of Invisibility makes players invisible")
                .syncToClients()
                .requiresRestart().build();

        public final ConfigValue<Boolean> hideWhenInvisible = define("hideWhenInvisible", false)
                .descriptionLine("Whether the Scarf of Invisibility is hidden when the wearer is invisible")
                .syncToClients().build();

        public final ConfigValue<Boolean> hideEffectParticles = define("hidesEffectParticles", false)
                .descriptionLine("Whether the Scarf of Invisibility should prevent all status effects from spawning particles")
                .syncToClients().build();

        // 1800 at 0.1/s = 5 hours
        public final ToggleBasedDurabilityCategory durability = new ToggleBasedDurabilityCategory(1800, 0.1);

        private ScarfOfInvisibility() {
            super(ModItems.SCARF_OF_INVISIBILITY);
        }
    }

    public final class ShockPendant extends ItemSubCategory {

        public final ConfigValue<Double> strikeChance = define("strikeChance", ValueTypes.FRACTION, 0.25)
                .descriptionLine("The probability that the Shock Pendant strikes an attacking entity with lightning")
                .syncToClients().build();

        public final ConfigValue<Boolean> cancelLightningDamage = define("cancelLightningDamage", true)
                .descriptionLine("Whether the Shock Pendant cancels damage from lightning")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(2)
                .descriptionLine("The amount of time in seconds the Shock Pendant goes on cooldown for after striking an attacker with lightning")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(50);

        private ShockPendant() {
            super(ModItems.SHOCK_PENDANT);
        }

        public RetaliationEffect.ActivationParams activationParams() {
            return new RetaliationEffect.ActivationParams(strikeChance, cooldown, durability.damagePerActivation);
        }
    }

    public final class Snorkel extends ItemSubCategory {

        public final ConfigValue<Boolean> isInfinite = define("isInfinite", false)
                .descriptionLine("Whether the Snorkel's water breathing effect depletes when underwater")
                .syncToClients()
                .requiresRestart().build();

        public final ConfigValue<Integer> waterBreathingDuration = define("waterBreathingDuration", ValueTypes.DURATION, 30)
                .descriptionLine("The duration of the water breathing effect that is applied by the Snorkel")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private Snorkel() {
            super(ModItems.SNORKEL);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(0.2)
                    .descriptionLine("The amount of durability lost per second while underwater")
                    .build();

            private Durability() {
                // 900 at 0.2 = 1.25 hours
                super(900);
            }
        }
    }

    public final class Snowshoes extends ItemSubCategory {

        public final ConfigValue<Boolean> allowWalkingOnPowderedSnow = define("allowWalkingOnPowderedSnow", true)
                .descriptionLine("Whether the Snowshoes allow the wearer to walk on powdered snow")
                .syncToClients().build();

        public final ConfigValue<Double> movementSpeedOnSnowBonus = define("movementSpeedOnSnowBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.30)
                .descriptionLine("How much the Snowshoes increase the wearer's movement speed on snow blocks")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private Snowshoes() {
            super(ModItems.SNOWSHOES);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(0.1)
                    .descriptionLine("The amount of durability lost per second when walking on snow")
                    .build();

            private Durability() {
                super(1800);
            }
        }
    }

    public final class SteadfastSpikes extends ItemSubCategory {

        public final ConfigValue<Double> knockbackResistance = define("knockbackResistance", ValueTypes.ATTRIBUTE_MODIFIER, 1.00)
                .descriptionLine("How much knockback resistance is granted by the Steadfast Spikes")
                .syncToClients().build();

        public final ConfigValue<Double> slipperinessReduction = define("slipperinessReduction", ValueTypes.ATTRIBUTE_MODIFIER, 1.00)
                .descriptionLine("How much the Steadfast Spikes reduce the slipperiness of ice")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private SteadfastSpikes() {
            super(ModItems.STEADFAST_SPIKES);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damageWhenAttacked
                    = define("damageWhenAttacked", 1)
                    .descriptionLine("The amount of durability lost when the wearer is attacked by an entity or player")
                    .build();

            private Durability() {
                super(800);
            }
        }
    }

    public final class StriderShoes extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Strider Shoes allow sneaking on lava")
                .syncToClients().build();

        public final ConfigValue<Boolean> cancelHotFloorDamage = define("cancelHotFloorDamage", true)
                .descriptionLine("Whether the Strider Shoes make the wearer immune to hot floor damage")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private StriderShoes() {
            super(ModItems.STRIDER_SHOES);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive = damagePerSecondActive(1)
                    .descriptionLine("The amount of durability lost per second when walking on lava")
                    .build();

            private Durability() {
                super(1800);
            }
        }
    }

    public final class SuperstitiousHat extends ItemSubCategory {

        public final ConfigValue<Integer> lootingLevelBonus = define("lootingLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of Looting that are granted by the Superstitious Hat")
                .syncToClients().build();

        public final KillBasedDurabilityCategory durability = new KillBasedDurabilityCategory(300);

        private SuperstitiousHat() {
            super(ModItems.SUPERSTITIOUS_HAT);
        }
    }

    public final class ThornPendant extends ItemSubCategory {

        public final ConfigValue<Double> strikeChance = define("strikeChance", ValueTypes.FRACTION, 0.50)
                .descriptionLine("The probability that the Thorn Pendant damages an attacking entity")
                .syncToClients().build();

        public final ConfigValue<Integer> maxDamage = define("maxDamage", ValueTypes.NON_NEGATIVE_INT, 6)
                .descriptionLine("The maximum amount of damage that is dealt when the Thorn Pendant activates")
                .syncToClients().build();

        public final ConfigValue<Integer> minDamage = define("minDamage", ValueTypes.NON_NEGATIVE_INT, 2)
                .descriptionLine("The minimum amount of damage that is dealt when the Thorn Pendant activates")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(2)
                .descriptionLine("The duration in seconds the Thorn Pendant goes on cooldown for after activating")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(150);

        private ThornPendant() {
            super(ModItems.THORN_PENDANT);
        }

        public RetaliationEffect.ActivationParams activationParams() {
            return new RetaliationEffect.ActivationParams(strikeChance, cooldown, durability.damagePerActivation);
        }
    }

    public final class Umbrella extends ItemSubCategory {

        public final ConfigValue<Boolean> isShield = define("isShield", true)
                .descriptionLine("Whether the Umbrella can be used as a shield")
                .syncToClients()
                .requiresRestart().build();

        public final ConfigValue<Boolean> isGlider = define("isGlider", true)
                .descriptionLine("Whether the Umbrella slows the player's falling speed when held")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private Umbrella() {
            super(ModItems.UMBRELLA);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerAttack = define("damagePerAttack", 1)
                    .descriptionLine("The amount of durability lost for each attack performed with the umbrella")
                    .requiresRestart().build();

            public final ConfigValue<Integer> damagePerBlockedAttackBase
                    = define("damagePerBlockedAttackBase", ValueTypes.NON_NEGATIVE_INT, 1)
                    .title("Damage per Blocked Attack (Base)")
                    .descriptionLine("The constant amount of damage that should be applied to the umbrella when an attack is blocked")
                    .requiresRestart().build();

            public final ConfigValue<Double> damagePerBlockedAttackFactor
                    = define("damagePerBlockedAttackFactor", ValueTypes.NON_NEGATIVE_DOUBLE, 1D)
                    .title("Damage per Blocked Attack (Factor)")
                    .descriptionLine("The fraction of the dealt damage that should be applied to the umbrella when an attack is blocked")
                    .requiresRestart().build();

            private Durability() {
                // default max damage is twice that of a normal shield
                super(336 * 2);
            }
        }
    }

    public final class UniversalAttractor extends ItemSubCategory {

        public final ConfigValue<Integer> magnetismLevel = define("magnetismLevel", ValueTypes.MOB_EFFECT_LEVEL, 5)
                .descriptionLine("The level of the magnetism effect that is applied by the Universal Attractor")
                .syncToClients().build();

        // 1800 at 0.1/s = 5 hours
        public final ToggleBasedDurabilityCategory durability = new ToggleBasedDurabilityCategory(1800, 0.1);

        private UniversalAttractor() {
            super(ModItems.UNIVERSAL_ATTRACTOR);
        }
    }

    public final class VampiricGlove extends ItemSubCategory {

        public final ConfigValue<Double> absorptionRatio = define("absorptionRatio", ValueTypes.NON_NEGATIVE_DOUBLE, 0.20)
                .descriptionLine("The proportion of melee damage dealt that is absorbed by the Vampiric Gloves")
                .syncToClients().build();

        public final ConfigValue<Double> absorptionChance = define("absorptionChance", ValueTypes.FRACTION, 1D)
                .descriptionLine("The probability that damage is absorbed when attacking an entity with the Vampiric Gloves")
                .syncToClients().build();

        public final ConfigValue<Integer> maxHealingPerHit = define("maxHealingPerHit", ValueTypes.NON_NEGATIVE_INT, 6)
                .descriptionLine("The maximum amount of healing that can be absorbed in a single hit when attacking an entity while wearing the Vampiric Glove")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(120);

        private VampiricGlove() {
            super(ModItems.VAMPIRIC_GLOVE);
        }
    }

    public final class VillagerHat extends ItemSubCategory {

        public final ConfigValue<Double> reputationBonus = define("reputationBonus", ValueTypes.ATTRIBUTE_MODIFIER, 75D)
                .descriptionLine("The amount of extra reputation that is granted by the Villager Hat when trading with villagers")
                .syncToClients().build();

        public final Durability durability = new Durability();

        private VillagerHat() {
            super(ModItems.VILLAGER_HAT);
        }

        public final class Durability extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerTrade = define("damagePerTrade", 1)
                    .descriptionLine("The amount of durability lost per trade completed with the Villager Hat")
                    .build();

            private Durability() {
                super(240);
            }
        }
    }

    public final class WarpDrive extends ItemSubCategory {

        public final ConfigValue<Boolean> enabled = defineEnabled(true)
                .descriptionLine("Whether the Warp Drive causes ender pearls to not be consumed")
                .syncToClients().build();

        public final ConfigValue<Integer> hungerCost = define("hungerCost", ValueTypes.NON_NEGATIVE_INT, 2)
                .descriptionLine("How many hunger points it costs to throw an Ender Pearl using the Warp Drive")
                .syncToClients().build();

        public final ConfigValue<Boolean> nullifyEnderPearlDamage = define("nullifyEnderPearlDamage", true)
                .descriptionLine("Whether the Warp Drive causes Ender Pearls not to deal any damage")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(0)
                .descriptionLine("The duration Ender Pearls go on cooldown for after being thrown using the Warp Drive")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(128);

        private WarpDrive() {
            super(ModItems.WARP_DRIVE);
        }
    }

    public final class WhoopeeCushion extends ItemSubCategory {

        public final ConfigValue<Double> fartChance = define("fartChance", ValueTypes.ATTRIBUTE_MODIFIER, 0.12)
                .descriptionLine("The probability that a fart sound plays when sneaking or double jumping while wearing the Whoopee Cushion")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(250);

        private WhoopeeCushion() {
            super(ModItems.WHOOPEE_CUSHION);
        }
    }

    public final class WitheredBracelet extends ItemSubCategory {

        public final ConfigValue<Double> witherChance = define("witherChance", ValueTypes.FRACTION, 0.3)
                .descriptionLine("The probability that the Withered Bracelet inflicts a wither effect")
                .syncToClients().build();

        public final ConfigValue<Integer> witherDuration = define("witherDuration", ValueTypes.DURATION, 8)
                .descriptionLine("The duration of the wither effect applied by the Withered Bracelet")
                .syncToClients().build();

        public final ConfigValue<Integer> witherLevel = define("witherLevel", ValueTypes.MOB_EFFECT_LEVEL, 2)
                .descriptionLine("The level of the wither effect that is inflicted by the Withered Bracelet")
                .syncToClients().build();

        public final ConfigValue<Integer> cooldown = defineCooldown(2)
                .descriptionLine("The duration the Withered Bracelet goes on cooldown for after inflicting wither on an entity")
                .syncToClients().build();

        public final TriggerBasedDurabilityCategory durability = new TriggerBasedDurabilityCategory(450);

        private WitheredBracelet() {
            super(ModItems.WITHERED_BRACELET);
        }
    }

    public Value<Boolean> generatesAsLoot(Item item) {
        Optional<ResourceKey<Item>> key = BuiltInRegistries.ITEM.getResourceKey(item);
        if (key.isEmpty() || !itemCategories.containsKey(key.get())) {
            return null;
        }
        return itemCategories.get(key.get()).generateAsLoot;
    }

    @SuppressWarnings("unchecked")
    public <CONFIG> @Nullable CONFIG get(ResourceKey<Item> key) {
        return (CONFIG) itemCategories.get(key);
    }

    private abstract class ItemSubCategory extends SubCategory {

        public final ConfigValue<Boolean> generateAsLoot = define("generateAsLoot", true)
                .titleAndDescription(SharedConfigLang.GENERATE_AS_LOOT)
                .displayPriority(-2)
                .build();

        public ItemSubCategory(RegistryHolder<Item, Item> holder) {
            super(holder.getKey().identifier().getPath());
            setTitle(new LangEntry(getKey().joinedPath()).withPrefix("item.artifacts"));
            // shouldn't really do this from a constructor but whatever
            ItemConfigs.this.itemCategories.put(holder.getKey(), this);
        }

        protected ConfigValueBuilder<Boolean> defineEnabled(boolean defaultValue) {
            return define("enabled", defaultValue)
                    .displayPriority(-1)
                    .title(SharedConfigLang.ENABLED);
        }

        protected ConfigValueBuilder<Integer> defineCooldown(int defaultValue) {
            return define("cooldown", ValueTypes.DURATION, defaultValue)
                    .title(SharedConfigLang.COOLDOWN);
        }

        protected class DurabilityCategory extends SubCategory implements ItemDamageProperties {

            private final TagKey<Item> repairMaterials;

            private final ConfigValue<Boolean> canBeDamaged;
            private final ConfigValue<Boolean> canBeRepaired;
            private final ConfigValue<Boolean> indestructible;
            private final ConfigValue<Integer> maxDamage;

            private DurabilityCategory(int maxDamage) {
                super(ItemSubCategory.this, "durability");
                this.repairMaterials = TagKey.create(
                        Registries.ITEM,
                        Artifacts.id("repairs_%s".formatted(ItemSubCategory.this.getKey().path().getLast()))
                );
                setTitle(SharedConfigLang.DURABILITY);
                this.canBeDamaged = define("canBeDamaged", false)
                        .titleAndDescription(SharedConfigLang.CAN_BE_DAMAGED)
                        .requiresRestart()
                        .displayPriority(-4)
                        .build();
                this.canBeRepaired = define("canBeRepaired", false)
                        .title(SharedConfigLang.CAN_BE_REPAIRED.title())
                        .descriptionLine(SharedConfigLang.CAN_BE_REPAIRED.description().withArgs(repairMaterials.location().toString()))
                        .requiresRestart()
                        .displayPriority(-3)
                        .build();
                this.indestructible = define("indestructible", true)
                        .titleAndDescription(SharedConfigLang.INDESTRUCTIBLE)
                        .syncToClients()
                        .displayPriority(-2)
                        .build();
                this.maxDamage = define("maxDamage", ValueTypes.NON_NEGATIVE_INT, maxDamage)
                        .titleAndDescription(SharedConfigLang.MAX_DAMAGE)
                        .requiresRestart()
                        .displayPriority(-1)
                        .build();
            }

            protected ConfigValueBuilder<Integer> define(String key, int defaultValue) {
                return define(key, ValueTypes.NON_NEGATIVE_INT, defaultValue);
            }

            protected ConfigValueBuilder<Double> damagePerSecondActive(double defaultValue) {
                return define("damagePerSecondActive", defaultValue)
                        .title(SharedConfigLang.DAMAGE_PER_SECOND_ACTIVE.title());
            }

            protected ConfigValueBuilder<Double> define(String key, double defaultValue) {
                return define(key, ValueTypes.NON_NEGATIVE_DOUBLE, defaultValue);
            }

            @Override
            public boolean canBeDamaged() {
                return canBeDamaged.get();
            }

            @Override
            public boolean canBeRepaired() {
                return canBeRepaired.get();
            }

            @Override
            public Value<Boolean> indestructible() {
                return indestructible;
            }

            @Override
            public int getMaxDamage() {
                return maxDamage.get();
            }

            @Override
            public TagKey<Item> getRepairMaterials() {
                return repairMaterials;
            }
        }

        public class TriggerBasedDurabilityCategory extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerActivation;

            private TriggerBasedDurabilityCategory(int maxDamage) {
                this(maxDamage, 1);
            }

            private TriggerBasedDurabilityCategory(int maxDamage, int damagePerActivation) {
                super(maxDamage);
                this.damagePerActivation = define("damagePerActivation", damagePerActivation)
                        .titleAndDescription(SharedConfigLang.DAMAGE_PER_ACTIVATION)
                        .build();
            }
        }

        public class AttackBasedDurabilityCategory extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerAttack;

            private AttackBasedDurabilityCategory(int maxDamage) {
                this(maxDamage, 1);
            }

            private AttackBasedDurabilityCategory(int maxDamage, int damagePerAttack) {
                super(maxDamage);
                this.damagePerAttack = define("damagePerAttack", damagePerAttack)
                        .titleAndDescription(SharedConfigLang.DAMAGE_PER_ATTACK)
                        .build();
            }
        }

        public class KillBasedDurabilityCategory extends DurabilityCategory {

            public final ConfigValue<Integer> damagePerKill;

            private KillBasedDurabilityCategory(int maxDamage) {
                this(maxDamage, 1);
            }

            private KillBasedDurabilityCategory(int maxDamage, int damagePerKill) {
                super(maxDamage);
                this.damagePerKill = define("damagePerKill", damagePerKill)
                        .titleAndDescription(SharedConfigLang.DAMAGE_PER_KILL)
                        .build();
            }
        }

        public class ToggleBasedDurabilityCategory extends DurabilityCategory {

            public final ConfigValue<Double> damagePerSecondActive;

            private ToggleBasedDurabilityCategory(int maxDamage, double damagePerSecond) {
                super(maxDamage);
                damagePerSecondActive = damagePerSecondActive(damagePerSecond)
                        .descriptionLine(SharedConfigLang.DAMAGE_PER_SECOND_ACTIVE.description())
                        .build();
            }
        }
    }
}
