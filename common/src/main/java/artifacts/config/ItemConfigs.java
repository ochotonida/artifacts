package artifacts.config;

import artifacts.Artifacts;
import artifacts.component.ability.ToolTierUpgrade;
import artifacts.config.value.ConfigValue;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.network.NetworkHandler;
import artifacts.network.UpdateItemConfigPacket;
import artifacts.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ItemConfigs extends ConfigManager {

    private final Map<ResourceKey<Item>, ItemCategory> itemCategories = new HashMap<>();

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

    public final class AnglersHat extends ItemCategory {

        public final ConfigValue<Integer> luckOfTheSeaLevelBonus = define("luckOfTheSeaLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of luck of the sea that are granted by the Angler's Hat").build();

        public final ConfigValue<Integer> lureLevelBonus = define("lureLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of lure that are granted by the Angler's Hat").build();

        private AnglersHat() {
            super(ModItems.ANGLERS_HAT);
        }
    }

    public final class AntidoteVessel extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Antidote Vessel reduces the duration of negative effects").build();

        public final ConfigValue<Integer> maxEffectDuration = define("maxEffectDuration", ValueTypes.DURATION, 5)
                .descriptionLine("The maximum duration in seconds negative mob effects can last when wearing the Antidote Vessel").build();

        private AntidoteVessel() {
            super(ModItems.ANTIDOTE_VESSEL);
        }
    }

    public final class AquaDashers extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Aqua-Dashers allow the wearer to sprint on water").build();

        private AquaDashers() {
            super(ModItems.AQUA_DASHERS);
        }
    }

    public final class BunnyHoppers extends ItemCategory {

        public final ConfigValue<Boolean> modifyHurtSounds = define("modifyHurtSounds", true)
                .descriptionLine("Whether the Bunny Hoppers change the player's hurt sounds").build();

        public final ConfigValue<Double> fallDamageMultiplier = define("fallDamageMultiplier", ValueTypes.ATTRIBUTE_MODIFIER, 0D)
                .descriptionLine("How much the Bunny Hoppers reduce or increase fall damage")
                .descriptionLine("Values between -1 and 0 reduce fall damage")
                .descriptionLine("Values above 0 increase fall damage").build();

        public final ConfigValue<Double> jumpStrengthBonus = define("jumpStrengthBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.40)
                .descriptionLine("The amount of extra jump strength the Bunny Hoppers apply to players").build();

        public final ConfigValue<Double> safeFallDistanceBonus = define("safeFallDistanceBonus", ValueTypes.ATTRIBUTE_MODIFIER, 10D)
                .descriptionLine("The amount of extra safe fall distance in blocks that is granted by the Bunny Hoppers").build();

        private BunnyHoppers() {
            super(ModItems.BUNNY_HOPPERS);
        }
    }

    public final class CharmOfShrinking extends ItemCategory {

        public final ConfigValue<Double> scaleModifier = define("scaleModifier", ValueTypes.ATTRIBUTE_MODIFIER, -0.50)
                .descriptionLine("How much the Charm of Shrinking decreases or increases the player's Scale")
                .descriptionLine("Values between -1 and 0 reduce the player's scale")
                .descriptionLine("Values above 0 increase the player's scale").build();

        private CharmOfShrinking() {
            super(ModItems.CHARM_OF_SHRINKING);
        }
    }

    public final class CharmOfSinking extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Charm of Sinking removes the wearer's collision with water")
                .requiresRestart().build();

        public final ConfigValue<Boolean> underwaterFallDamage = define("underwaterFallDamage", false)
                .descriptionLine("Whether it is possible to take fall damage underwater when wearing the Charm of Sinking")
                .requiresRestart().build();

        public final ConfigValue<Double> oxygenBonus = define("oxygenBonus", ValueTypes.ATTRIBUTE_MODIFIER, 1.5)
                .descriptionLine("How much longer players wearing the Charm of Sinking can stay underwater").build();

        private CharmOfSinking() {
            super(ModItems.CHARM_OF_SINKING);
        }
    }

    public final class ChorusTotem extends ItemCategory {

        public final ConfigValue<Boolean> consumeOnUse = define("consumeOnUse", true)
                .descriptionLine("Whether the Chorus Totem is consumed after activating").build();

        public final ConfigValue<Double> teleportationChance = define("teleportationChance", ValueTypes.FRACTION, 1.00)
                .descriptionLine("The probability that the Chorus Totem activates when a player dies").build();

        public final ConfigValue<Integer> healthRestored = define("healthRestored", ValueTypes.NON_NEGATIVE_INT, 9)
                .descriptionLine("The amount of health points that are restored after the Chorus Totem activates").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The duration in seconds the Chorus Totem goes on cooldown for after activating").build();

        private ChorusTotem() {
            super(ModItems.CHORUS_TOTEM);
        }
    }

    public final class CloudInABottle extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Cloud in a Bottle allows the wearer to double jump").build();

        public final ConfigValue<Double> sprintJumpVerticalVelocity
                = define("sprintJumpVerticalVelocity", ValueTypes.NON_NEGATIVE_DOUBLE, 0.25)
                .descriptionLine("The amount of extra vertical velocity that is applied to players that double jump while sprinting using the Cloud in a Bottle").build();

        public final ConfigValue<Double> sprintJumpHorizontalVelocity
                = define("sprintJumpHorizontalVelocity", ValueTypes.NON_NEGATIVE_DOUBLE, 0.25)
                .descriptionLine("The amount of extra horizontal velocity that is applied to players that double jump while sprinting using the Cloud in a Bottle").build();

        public final ConfigValue<Double> safeFallDistanceBonus = define("safeFallDistanceBonus", ValueTypes.ATTRIBUTE_MODIFIER, 3D)
                .descriptionLine("The amount of extra safe fall distance in blocks that is granted by the Cloud in a Bottle").build();

        public final ConfigValue<Double> fallDamageMultiplier = define("fallDamageMultiplier", ValueTypes.FRACTION, 0D)
                .descriptionLine("How much fall damage is dealt when double jumping with the Cloud in a Bottle").build();

        private CloudInABottle() {
            super(ModItems.CLOUD_IN_A_BOTTLE);
        }
    }

    public final class CowboyHat extends ItemCategory {

        public final ConfigValue<Double> mountSpeedBonus = define("mountSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.40)
                .descriptionLine("How much the Cowboy Hat increases the speed of ridden mounts").build();

        private CowboyHat() {
            super(ModItems.COWBOY_HAT);
        }
    }

    public final class CrossNecklace extends ItemCategory {

        public final ConfigValue<Double> bonusInvincibilityTicks = define("bonusInvincibilityTicks", ValueTypes.ATTRIBUTE_MODIFIER, 20D)
                .descriptionLine("The amount of extra ticks the player stays invincible for after taking damage while wearing the Cross Necklace").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The duration in seconds the Cross Necklace goes on cooldown for after activating").build();

        private CrossNecklace() {
            super(ModItems.CROSS_NECKLACE);
        }
    }

    public final class CrystalHeart extends ItemCategory {

        public final ConfigValue<Double> healthBonus = define("healthBonus", ValueTypes.ATTRIBUTE_MODIFIER, 10D)
                .descriptionLine("The amount of extra health points that are granted by the Crystal Heart").build();

        private CrystalHeart() {
            super(ModItems.CRYSTAL_HEART);
        }
    }

    public final class DiggingClaws extends ItemCategory {

        public final ConfigValue<Double> blockBreakSpeedBonus = define("blockBreakSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.30)
                .descriptionLine("How much the Digging Claws increase the wearer's mining speed").build();

        public final ConfigValue<ToolTierUpgrade.Tier> toolTier = define("toolTier", ValueTypes.TOOL_TIER, ToolTierUpgrade.Tier.STONE)
                .descriptionLine("The tool tier that the Digging Claws increase the wearer's mining level to").build();

        private DiggingClaws() {
            super(ModItems.DIGGING_CLAWS);
        }
    }

    public final class DrinkingHat extends ItemCategory {

        public final ConfigValue<Double> drinkingSpeedBonus;
        public final ConfigValue<Double> eatingSpeedBonus;

        private DrinkingHat(Holder<Item> item, String itemName) {
            super(item);
            this.drinkingSpeedBonus = define("drinkingSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 1.50)
                    .descriptionLine("How much the %s increases the wearer's drinking speed".formatted(itemName)).build();
            this.eatingSpeedBonus = define("eatingSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.50)
                    .descriptionLine("How much the %s increases the wearer's eating speed".formatted(itemName)).build();
        }
    }

    public final class EternalSteak extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Eternal Steak can be eaten")
                .requiresRestart().build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 15)
                .descriptionLine("The duration in seconds the Eternal Steak goes on cooldown for after being eaten")
                .requiresRestart().build();

        private EternalSteak() {
            super(ModItems.ETERNAL_STEAK);
        }
    }

    public final class EverlastingBeef extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Everlasting Beef can be eaten")
                .requiresRestart().build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 15)
                .descriptionLine("The duration in seconds the Everlasting Beef goes on cooldown for after being eaten")
                .requiresRestart().build();

        public final ConfigValue<Double> dropRate = define("dropRate", ValueTypes.FRACTION, 1 / 500D)
                .descriptionLine("The probability that Everlasting Beef drops when a cow or mooshroom is killed by a player").build();

        private EverlastingBeef() {
            super(ModItems.EVERLASTING_BEEF);
        }
    }

    public final class FeralClaws extends ItemCategory {

        public final ConfigValue<Double> attackSpeedBonus = define("attackSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.30)
                .descriptionLine("How much the Feral Claws increase the wearer's attack speed").build();

        private FeralClaws() {
            super(ModItems.FERAL_CLAWS);
        }
    }

    public final class FireGauntlet extends ItemCategory {

        public final ConfigValue<Double> fireDuration = define("fireDuration", ValueTypes.ATTRIBUTE_MODIFIER, 8D)
                .descriptionLine("How long an entity is set on fire for after being attacked by an entity wearing the Fire Gauntlet").build();

        private FireGauntlet() {
            super(ModItems.FIRE_GAUNTLET);
        }
    }

    public final class FlamePendant extends ItemCategory {

        public final ConfigValue<Double> strikeChance = define("strikeChance", ValueTypes.FRACTION, 0.40)
                .descriptionLine("The probability that the Flame Pendant lights an attacker on fire").build();

        public final ConfigValue<Integer> fireDuration = define("fireDuration", ValueTypes.DURATION, 10)
                .descriptionLine("How long an attacking entity is set on fire for when the Flame Pendant activates").build();

        public final ConfigValue<Boolean> grantFireResistance = define("grantFireResistance", true)
                .descriptionLine("Whether the Flame Pendant grants Fire Resistance after igniting an entity").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The duration in seconds the Flame Pendant goes on cooldown for after setting an entity on fire").build();

        private FlamePendant() {
            super(ModItems.FLAME_PENDANT);
        }
    }

    public final class Flippers extends ItemCategory {

        public final ConfigValue<Double> swimSpeedBonus = define("swimSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.70)
                .descriptionLine("How much the Flippers increase the wearer's swim speed").build();

        private Flippers() {
            super(ModItems.FLIPPERS);
        }
    }

    public final class GoldenHook extends ItemCategory {

        public final ConfigValue<Double> entityExperienceBonus = define("entityExperienceBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.50)
                .descriptionLine("The amount of extra experience dropped by entities that are killed by players wearing the Golden Hook").build();

        private GoldenHook() {
            super(ModItems.GOLDEN_HOOK);
        }
    }

    public final class HeliumFlamingo extends ItemCategory {

        public final ConfigValue<Integer> flightDuration = define("flightDuration", ValueTypes.DURATION, 8)
                .descriptionLine("The amount of time in seconds a player can fly with the Helium Flamingo before needing to recharge").build();

        public final ConfigValue<Integer> rechargeDuration = define("rechargeDuration", ValueTypes.DURATION, 15)
                .descriptionLine("The amount of time in seconds it takes for the Helium Flamingo to recharge").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 3)
                .descriptionLine("The duration in seconds the Helium Flamingo goes on cooldown for when stopping flight").build();

        private HeliumFlamingo() {
            super(ModItems.HELIUM_FLAMINGO);
        }
    }

    public final class KittySlippers extends ItemCategory {

        public final ConfigValue<Boolean> modifyHurtSounds = define("modifyHurtSounds", true)
                .descriptionLine("Whether the Kitty Slippers change the player's hurt sounds").build();

        public final ConfigValue<Boolean> repelCreepers = define("repelCreepers", true)
                .descriptionLine("Whether the Kitty Slippers scare nearby creepers").build();

        public final ConfigValue<Boolean> repelPhantoms = define("repelPhantoms", true)
                .descriptionLine("Whether the Kitty Slippers hiss at nearby phantoms").build();

        private KittySlippers() {
            super(ModItems.KITTY_SLIPPERS);
        }
    }

    public final class LuckyScarf extends ItemCategory {

        public final ConfigValue<Integer> fortuneLevelBonus = define("fortuneLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of fortune that are granted by the Lucky Scarf").build();

        private LuckyScarf() {
            super(ModItems.LUCKY_SCARF);
        }
    }

    public final class NightVisionGoggles extends ItemCategory {

        public final ConfigValue<Double> strength = define("strength", ValueTypes.FRACTION, 0.15)
                .descriptionLine("The strength of the night vision effect applied by the Night Vision Goggles").build();

        private NightVisionGoggles() {
            super(ModItems.NIGHT_VISION_GOGGLES);
        }
    }

    public final class ObsidianSkull extends ItemCategory {

        public final ConfigValue<Integer> fireResistanceDuration = define("fireResistanceDuration", ValueTypes.DURATION, 30)
                .descriptionLine("The duration of the fire resistance effect that is applied when taking fire damage while wearing the Obsidian Skull").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 60)
                .descriptionLine("The amount of time in seconds the Obsidian Skull goes on cooldown for after taking fire damage").build();

        private ObsidianSkull() {
            super(ModItems.OBSIDIAN_SKULL);
        }
    }

    public final class OnionRing extends ItemCategory {

        public final ConfigValue<Integer> hasteDurationPerFoodPoint = define("hasteDurationPerFoodPoint", ValueTypes.DURATION, 6)
                .descriptionLine("The duration of haste that is applied per food point eaten while wearing the Onion Ring").build();

        public final ConfigValue<Integer> hasteLevel = define("hasteLevel", ValueTypes.MOB_EFFECT_LEVEL, 2)
                .descriptionLine("The level of the haste effect that is applied by the Onion Ring").build();

        private OnionRing() {
            super(ModItems.ONION_RING);
        }
    }

    public final class PanicNecklace extends ItemCategory {

        public final ConfigValue<Integer> speedLevel = define("speedLevel", ValueTypes.MOB_EFFECT_LEVEL, 1)
                .descriptionLine("The level of the speed effect that is applied by the Panic Necklace").build();

        public final ConfigValue<Integer> speedDuration = define("speedDuration", ValueTypes.DURATION, 8)
                .descriptionLine("The duration in seconds of the speed effect that is applied when taking damage while wearing the Panic Necklace").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The duration in seconds the Panic Necklace goes on cooldown for after taking damage").build();

        private PanicNecklace() {
            super(ModItems.PANIC_NECKLACE);
        }
    }

    public final class PickaxeHeater extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Pickaxe Heater smelts mined ores").build();

        private PickaxeHeater() {
            super(ModItems.PICKAXE_HEATER);
        }
    }

    public final class PocketPiston extends ItemCategory {

        public final ConfigValue<Double> attackKnockbackBonus = define("attackKnockbackBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.75)
                .descriptionLine("The amount of extra knockback that is granted by the Pocket Piston").build();

        private PocketPiston() {
            super(ModItems.POCKET_PISTON);
        }
    }

    public final class PowerGlove extends ItemCategory {

        public final ConfigValue<Double> attackDamageBonus = define("attackDamageBonus", ValueTypes.ATTRIBUTE_MODIFIER, 4D)
                .descriptionLine("The amount of extra damage that is dealt by melee attacks from players wearing the Power Glove").build();

        private PowerGlove() {
            super(ModItems.POWER_GLOVE);
        }
    }

    public final class RootedBoots extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Rooted Boots replenish hunger when standing on grass").build();

        public final ConfigValue<Integer> hungerReplenishingDuration = define("hungerReplenishingDuration", ValueTypes.DURATION, 10)
                .descriptionLine("The amount of time in seconds it takes to replenish a single point of hunger while wearing the Rooted Boots").build();

        public final ConfigValue<Boolean> growPlantsAfterEating = define("growPlantsAfterEating", true)
                .descriptionLine("Whether the Rooted Boots apply a bone meal effect after eating food").build();

        private RootedBoots() {
            super(ModItems.ROOTED_BOOTS);
        }
    }

    public final class RunningShoes extends ItemCategory {

        public final ConfigValue<Double> sprintingSpeedBonus = define("sprintingSpeedBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.40)
                .descriptionLine("How much the Running Shoes increase the wearer's sprinting speed").build();

        public final ConfigValue<Double> sprintingStepHeightBonus = define("sprintingStepHeightBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.5)
                .descriptionLine("How much the Running Shoes increase the wearer's step height while sprinting").build();

        private RunningShoes() {
            super(ModItems.RUNNING_SHOES);
        }
    }

    public final class ScarfOfInvisibility extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Scarf of Invisibility makes players invisible")
                .requiresRestart().build();

        public final ConfigValue<Boolean> hideWhenInvisible = define("hideWhenInvisible", false)
                .descriptionLine("Whether the Scarf of Invisibility is hidden when the wearer is invisible").build();

        public final ConfigValue<Boolean> hideEffectParticles = define("hidesEffectParticles", false)
                .descriptionLine("Whether the Scarf of Invisibility should prevent all status effects from spawning particles").build();

        private ScarfOfInvisibility() {
            super(ModItems.SCARF_OF_INVISIBILITY);
        }
    }

    public final class ShockPendant extends ItemCategory {

        public final ConfigValue<Double> strikeChance = define("strikeChance", ValueTypes.FRACTION, 0.25)
                .descriptionLine("The probability that the Shock Pendant strikes an attacking entity with lightning").build();

        public final ConfigValue<Boolean> cancelLightningDamage = define("cancelLightningDamage", true)
                .descriptionLine("Whether the Shock Pendant cancels damage from lightning").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The amount of time in seconds the Shock Pendant goes on cooldown for after striking an attacker with lightning").build();

        private ShockPendant() {
            super(ModItems.SHOCK_PENDANT);
        }
    }

    public final class Snorkel extends ItemCategory {

        public final ConfigValue<Boolean> isInfinite = define("isInfinite", false)
                .descriptionLine("Whether the Snorkel's water breathing effect depletes when underwater")
                .requiresRestart().build();

        public final ConfigValue<Integer> waterBreathingDuration = define("waterBreathingDuration", ValueTypes.DURATION, 30)
                .descriptionLine("The duration of the water breathing effect that is applied by the Snorkel").build();

        private Snorkel() {
            super(ModItems.SNORKEL);
        }
    }

    public final class Snowshoes extends ItemCategory {

        public final ConfigValue<Boolean> allowWalkingOnPowderedSnow = define("allowWalkingOnPowderedSnow", true)
                .descriptionLine("Whether the Snowshoes allow the wearer to walk on powdered snow").build();

        public final ConfigValue<Double> movementSpeedOnSnowBonus = define("movementSpeedOnSnowBonus", ValueTypes.ATTRIBUTE_MODIFIER, 0.30)
                .descriptionLine("How much the Snowshoes increase the wearer's movement speed on snow blocks").build();

        private Snowshoes() {
            super(ModItems.SNOWSHOES);
        }
    }

    public final class SteadfastSpikes extends ItemCategory {

        public final ConfigValue<Double> knockbackResistance = define("knockbackResistance", ValueTypes.ATTRIBUTE_MODIFIER, 1.00)
                .descriptionLine("How much knockback resistance is granted by the Steadfast Spikes").build();

        public final ConfigValue<Double> slipperinessReduction = define("slipperinessReduction", ValueTypes.ATTRIBUTE_MODIFIER, 1.00)
                .descriptionLine("How much the Steadfast Spikes reduce the slipperiness of ice").build();

        private SteadfastSpikes() {
            super(ModItems.STEADFAST_SPIKES);
        }
    }

    public final class StriderShoes extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Strider Shoes allow sneaking on lava").build();

        public final ConfigValue<Boolean> cancelHotFloorDamage = define("cancelHotFloorDamage", true)
                .descriptionLine("Whether the Strider Shoes make the wearer immune to hot floor damage").build();

        private StriderShoes() {
            super(ModItems.STRIDER_SHOES);
        }
    }

    public final class SuperstitiousHat extends ItemCategory {

        public final ConfigValue<Integer> lootingLevelBonus = define("lootingLevelBonus", ValueTypes.ENCHANTMENT_LEVEL, 1)
                .descriptionLine("The amount of extra levels of Looting that are granted by the Superstitious Hat").build();

        private SuperstitiousHat() {
            super(ModItems.SUPERSTITIOUS_HAT);
        }
    }

    public final class ThornPendant extends ItemCategory {

        public final ConfigValue<Double> strikeChance = define("strikeChance", ValueTypes.FRACTION, 0.50)
                .descriptionLine("The probability that the Thorn Pendant damages an attacking entity").build();

        public final ConfigValue<Integer> maxDamage = define("maxDamage", ValueTypes.NON_NEGATIVE_INT, 6)
                .descriptionLine("The maximum amount of damage that is dealt when the Thorn Pendant activates").build();

        public final ConfigValue<Integer> minDamage = define("minDamage", ValueTypes.NON_NEGATIVE_INT, 2)
                .descriptionLine("The minimum amount of damage that is dealt when the Thorn Pendant activates").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The duration in seconds the Thorn Pendant goes on cooldown for after activating").build();

        private ThornPendant() {
            super(ModItems.THORN_PENDANT);
        }
    }

    public final class Umbrella extends ItemCategory {

        public final ConfigValue<Boolean> isShield = define("isShield", true)
                .descriptionLine("Whether the Umbrella can be used as a shield")
                .requiresRestart().build();

        public final ConfigValue<Boolean> isGlider = define("isGlider", true)
                .descriptionLine("Whether the Umbrella slows the player's falling speed when held").build();

        private Umbrella() {
            super(ModItems.UMBRELLA);
        }
    }

    public final class UniversalAttractor extends ItemCategory {

        public final ConfigValue<Integer> magnetismLevel = define("magnetismLevel", ValueTypes.MOB_EFFECT_LEVEL, 5)
                .descriptionLine("The level of the magnetism effect that is applied by the Universal Attractor").build();

        private UniversalAttractor() {
            super(ModItems.UNIVERSAL_ATTRACTOR);
        }
    }

    public final class VampiricGlove extends ItemCategory {

        public final ConfigValue<Double> absorptionRatio = define("absorptionRatio", ValueTypes.NON_NEGATIVE_DOUBLE, 0.20)
                .descriptionLine("The proportion of melee damage dealt that is absorbed by the Vampiric Gloves").build();

        public final ConfigValue<Double> absorptionChance = define("absorptionChance", ValueTypes.FRACTION, 1D)
                .descriptionLine("The probability that damage is absorbed when attacking an entity with the Vampiric Gloves").build();

        public final ConfigValue<Integer> maxHealingPerHit = define("maxHealingPerHit", ValueTypes.NON_NEGATIVE_INT, 6)
                .descriptionLine("The maximum amount of healing that can be absorbed in a single hit when attacking an entity while wearing the Vampiric Glove").build();

        private VampiricGlove() {
            super(ModItems.VAMPIRIC_GLOVE);
        }
    }

    public final class VillagerHat extends ItemCategory {

        public final ConfigValue<Double> reputationBonus = define("reputationBonus", ValueTypes.ATTRIBUTE_MODIFIER, 75D)
                .descriptionLine("The amount of extra reputation that is granted by the Villager Hat when trading with villagers").build();

        private VillagerHat() {
            super(ModItems.VILLAGER_HAT);
        }
    }

    public final class WarpDrive extends ItemCategory {

        public final ConfigValue<Boolean> enabled = define("enabled", true)
                .descriptionLine("Whether the Warp Drive causes ender pearls to not be consumed").build();

        public final ConfigValue<Integer> hungerCost = define("hungerCost", ValueTypes.NON_NEGATIVE_INT, 2)
                .descriptionLine("How many hunger points it costs to throw an Ender Pearl using the Warp Drive").build();

        public final ConfigValue<Boolean> nullifyEnderPearlDamage = define("nullifyEnderPearlDamage", true)
                .descriptionLine("Whether the Warp Drive causes Ender Pearls not to deal any damage").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The duration Ender Pearls go on cooldown for after being thrown using the Warp Drive").build();

        private WarpDrive() {
            super(ModItems.WARP_DRIVE);
        }
    }

    public final class WhoopeeCushion extends ItemCategory {

        public final ConfigValue<Double> fartChance = define("fartChance", ValueTypes.ATTRIBUTE_MODIFIER, 0.12)
                .descriptionLine("The probability that a fart sound plays when sneaking or double jumping while wearing the Whoopee Cushion").build();

        private WhoopeeCushion() {
            super(ModItems.WHOOPEE_CUSHION);
        }
    }

    public final class WitheredBracelet extends ItemCategory {

        public final ConfigValue<Double> witherChance = define("witherChance", ValueTypes.FRACTION, 0.3)
                .descriptionLine("The probability that the Withered Bracelet inflicts a wither effect").build();

        public final ConfigValue<Integer> witherDuration = define("witherDuration", ValueTypes.DURATION, 8)
                .descriptionLine("The duration of the wither effect applied by the Withered Bracelet").build();

        public final ConfigValue<Integer> witherLevel = define("witherLevel", ValueTypes.MOB_EFFECT_LEVEL, 2)
                .descriptionLine("The level of the wither effect that is inflicted by the Withered Bracelet").build();

        public final ConfigValue<Integer> cooldown = define("cooldown", ValueTypes.DURATION, 0)
                .descriptionLine("The duration the Withered Bracelet goes on cooldown for after inflicting wither on an entity").build();

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

    @Override
    public void onConfigChanged() {
        // Clients should always receive item configs from the server, only servers should read this config from disk
        if (Artifacts.getCurrentServer() != null) {
            Artifacts.LOGGER.info("Sending updated item configs to connected clients");
            readValuesFromConfig();
            sendToClients(Artifacts.getCurrentServer());
        }
    }

    private void sendToClients(MinecraftServer server) {
        getValues().forEach((key, value) -> NetworkHandler.sendToPlayers(server.getPlayerList().getPlayers(), new UpdateItemConfigPacket(value)));
    }

    public void sendToClient(ServerPlayer player) {
        Artifacts.LOGGER.info("Sending item configs to client");
        getValues().forEach((key, value) -> NetworkHandler.sendToPlayer(player, new UpdateItemConfigPacket(value)));
    }

    private abstract class ItemCategory extends Category {

        public final ConfigValue<Boolean> generateAsLoot = define("generateAsLoot", true)
                .descriptionLine("Whether this item can be found in structures or drop from entities").build();

        public ItemCategory(Holder<Item> holder) {
            super(holder.unwrapKey().orElseThrow().location().getPath());
            // shouldn't really do this from a constructor but whatever
            ItemConfigs.this.itemCategories.put(holder.unwrapKey().orElseThrow(), this);
        }
    }
}
