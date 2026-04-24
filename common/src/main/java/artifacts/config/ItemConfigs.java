package artifacts.config;

import artifacts.Artifacts;
import artifacts.component.ability.ToolTierUpgrade;
import artifacts.config.value.ConfigValue;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.network.NetworkHandler;
import artifacts.network.UpdateItemConfigPacket;
import artifacts.registry.ModItems;
import artifacts.registry.RegistryHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemConfigs extends ConfigManager {

    private final Map<ResourceKey<Item>, ConfigValue<Boolean>> generateAsLoot = new HashMap<>();

    public final ConfigValue<Boolean>
            antidoteVesselEnabled = defineBool(createKey(ModItems.ANTIDOTE_VESSEL, "enabled"),
                    "Whether the Antidote Vessel reduces the duration of negative effects"),
            aquaDashersEnabled = defineBool(createKey(ModItems.AQUA_DASHERS, "enabled"),
                    "Whether the Aqua-Dashers allow the wearer to sprint on water"),
            charmOfSinkingEnabled = defineBool(createKey(ModItems.CHARM_OF_SINKING, "enabled"), true, true,
                    "Whether the Charm of Sinking removes the wearer's collision with water"),
            cloudInABottleEnabled = defineBool(createKey(ModItems.CLOUD_IN_A_BOTTLE, "enabled"),
                    "Whether the Cloud in a Bottle allows the wearer to double jump"),
            eternalSteakEnabled = defineBool(createKey(ModItems.ETERNAL_STEAK, "enabled"),
                    "Whether the Eternal Steak can be eaten"),
            everlastingBeefEnabled = defineBool(createKey(ModItems.EVERLASTING_BEEF, "enabled"),
                    "Whether the Everlasting Beef can be eaten"),
            pickaxeHeaterEnabled = defineBool(createKey(ModItems.PICKAXE_HEATER, "enabled"),
                    "Whether the Pickaxe Heater smelts mined ores"),
            rootedBootsEnabled = defineBool(createKey(ModItems.ROOTED_BOOTS, "enabled"),
                    "Whether the Rooted Boots replenish hunger when standing on grass"),
            scarfOfInvisibilityEnabled = defineBool(createKey(ModItems.SCARF_OF_INVISIBILITY, "enabled"), true, true,
                    "Whether the Scarf of Invisibility makes players invisible"),
            striderShoesEnabled = defineBool(createKey(ModItems.STRIDER_SHOES, "enabled"),
                    "Whether the Strider Shoes allow sneaking on lava"),
            warpDriveEnabled = defineBool(createKey(ModItems.WARP_DRIVE, "enabled"),
                    "Whether the Warp Drive causes ender pearls to not be consumed"),

            chorusTotemConsumeOnUse = defineBool(createKey(ModItems.CHORUS_TOTEM, "consumeOnUse"),
                    "Whether the Chorus Totem is consumed after activating"),
            flamePendantGrantFireResistance = defineBool(createKey(ModItems.FLAME_PENDANT, "grantFireResistance"),
                    "Whether the Flame Pendant grants Fire Resistance after igniting an entity"),
            rootedBootsGrowPlantsAfterEating = defineBool(createKey(ModItems.ROOTED_BOOTS, "growPlantsAfterEating"),
                    "Whether the Rooted Boots apply a bone meal effect after eating food"),
            scarfOfInvisibilityHideWhenInvisible = defineBool(createKey(ModItems.SCARF_OF_INVISIBILITY, "hideWhenInvisible"), false, false,
                    "Whether the Scarf of Invisibility is hidden when the wearer is invisible"),
            shockPendantCancelLightningDamage = defineBool(createKey(ModItems.SHOCK_PENDANT, "cancelLightningDamage"),
                    "Whether the Shock Pendant cancels damage from lightning"),
            snorkelIsInfinite = defineBool(createKey(ModItems.SNORKEL, "isInfinite"), false, true,
                    "Whether the Snorkel's water breathing effect depletes when underwater"),
            snowshoesAllowWalkingOnPowderedSnow = defineBool(createKey(ModItems.SNOWSHOES, "allowWalkingOnPowderedSnow"),
                    "Whether the Snowshoes allow the wearer to walk on powdered snow"),
            striderShoesCancelHotFloorDamage = defineBool(createKey(ModItems.STRIDER_SHOES, "cancelHotFloorDamage"),
                    "Whether the Strider Shoes make the wearer immune to hot floor damage"),
            umbrellaIsShield = defineBool(createKey(ModItems.UMBRELLA, "isShield"),
                    "Whether the Umbrella can be used as a shield"),
            umbrellaIsGlider = defineBool(createKey(ModItems.UMBRELLA, "isGlider"),
                    "Whether the Umbrella slows the player's falling speed when held"),
            warpDriveNullifyEnderPearlDamage = defineBool(createKey(ModItems.WARP_DRIVE, "nullifyEnderPearlDamage"),
                    "Whether the Warp Drive causes Ender Pearls not to deal any damage"),
            bunnyHoppersModifyHurtSounds = defineBool(createKey(ModItems.BUNNY_HOPPERS, "modifyHurtSounds"),
                    "Whether the Bunny Hoppers change the player's hurt sounds"),
            kittySlippersModifyHurtSounds = defineBool(createKey(ModItems.KITTY_SLIPPERS, "modifyHurtSounds"),
                    "Whether the Kitty Slippers change the player's hurt sounds"),
            kittySlippersRepelCreepers = defineBool(createKey(ModItems.KITTY_SLIPPERS, "repelCreepers"),
                    "Whether the Kitty Slippers scare nearby creepers"),
            kittySlippersRepelPhantoms = defineBool(createKey(ModItems.KITTY_SLIPPERS, "repelPhantoms"),
                    "Whether the Kitty Slippers hiss at nearby phantoms"),
            charmOfSinkingUnderwaterFallDamage = defineBool(createKey(ModItems.CHARM_OF_SINKING, "underwaterFallDamage"), false, true,
                    "Whether it is possible to take fall damage underwater when wearing the Charm of Sinking");

    public final ConfigValue<Double>
            cloudInABottleSprintJumpVerticalVelocity = defineNonNegativeDouble(createKey(ModItems.CLOUD_IN_A_BOTTLE, "sprintJumpVerticalVelocity"), 0.25,
            "The amount of extra vertical velocity that is applied to players " +
                    "that double jump while sprinting using the Cloud in a Bottle"),
            cloudInABottleSprintJumpHorizontalVelocity = defineNonNegativeDouble(createKey(ModItems.CLOUD_IN_A_BOTTLE, "sprintJumpHorizontalVelocity"), 0.25,
                    "The amount of extra horizontal velocity that is applied to players " +
                    "that double jump while sprinting using the Cloud in a Bottle"),
            vampiricGloveAbsorptionRatio = defineNonNegativeDouble(createKey(ModItems.VAMPIRIC_GLOVE, "absorptionRatio"), 0.20,
                    "The proportion of melee damage dealt that is absorbed by the Vampiric Gloves");

    public final ConfigValue<Double>
            bunnyHoppersFallDamageMultiplier = defineAttributeModifier(createKey(ModItems.BUNNY_HOPPERS, "fallDamageMultiplier"), 0,
            "How much the Bunny Hoppers reduce or increase fall damage",
            "Values between -1 and 0 reduce fall damage",
            "Values above 0 increase fall damage"),
            bunnyHoppersJumpStrengthBonus = defineAttributeModifier(createKey(ModItems.BUNNY_HOPPERS, "jumpStrengthBonus"), 0.40,
                    "The amount of extra jump strength the Bunny Hoppers apply to players"),
            bunnyHoppersSafeFallDistanceBonus = defineAttributeModifier(createKey(ModItems.BUNNY_HOPPERS, "safeFallDistanceBonus"), 10,
                    "The amount of extra safe fall distance in blocks that is granted by the Bunny Hoppers"),
            charmOfShrinkingScaleModifier = defineAttributeModifier(createKey(ModItems.CHARM_OF_SHRINKING, "scaleModifier"), -0.50,
                    "How much the Charm of Shrinking decreases or increases the player's Scale",
                    "Values between -1 and 0 reduce the player's scale",
                    "Values above 0 increase the player's scale"),
            cloudInABottleSafeFallDistanceBonus = defineAttributeModifier(createKey(ModItems.CLOUD_IN_A_BOTTLE, "safeFallDistanceBonus"), 3,
                    "The amount of extra safe fall distance in blocks that is granted by the Cloud in a Bottle"),
            cowboyHatMountSpeedBonus = defineAttributeModifier(createKey(ModItems.COWBOY_HAT, "mountSpeedBonus"), 0.40,
                    "How much the Cowboy Hat increases the speed of ridden mounts"),
            crossNecklaceBonusInvincibilityTicks = defineAttributeModifier(createKey(ModItems.CROSS_NECKLACE, "bonusInvincibilityTicks"), 20,
                    "The amount of extra ticks the player stays invincible for " +
                    "after taking damage while wearing the Cross Necklace"),
            crystalHeartHealthBonus = defineAttributeModifier(createKey(ModItems.CRYSTAL_HEART, "healthBonus"), 10,
                    "The amount of extra health points that are granted by the Crystal Heart"),
            diggingClawsBlockBreakSpeedBonus = defineAttributeModifier(createKey(ModItems.DIGGING_CLAWS, "blockBreakSpeedBonus"), 0.30,
                    "How much the Digging Claws increase the wearer's mining speed"),
            feralClawsAttackSpeedBonus = defineAttributeModifier(createKey(ModItems.FERAL_CLAWS, "attackSpeedBonus"), 0.30,
                    "How much the Feral Claws increase the wearer's attack speed"),
            fireGauntletFireDuration = defineAttributeModifier(createKey(ModItems.FIRE_GAUNTLET, "fireDuration"), 8,
                    "How long an entity is set on fire for after being attacked by an entity wearing the Fire Gauntlet"),
            flippersSwimSpeedBonus = defineAttributeModifier(createKey(ModItems.FLIPPERS, "swimSpeedBonus"), 0.70,
                    "How much the Flippers increase the wearer's swim speed"),
            goldenHookEntityExperienceBonus = defineAttributeModifier(createKey(ModItems.GOLDEN_HOOK, "entityExperienceBonus"), 0.50,
                    "The amount of extra experience dropped by entities " +
                    "that are killed by players wearing the Golden Hook"),
            noveltyDrinkingHatDrinkingSpeedBonus = defineAttributeModifier(createKey(ModItems.NOVELTY_DRINKING_HAT, "drinkingSpeedBonus"), 1.50,
                    "How much the Novelty Drinking Hat increases the wearer's drinking speed"),
            noveltyDrinkingHatEatingSpeedBonus = defineAttributeModifier(createKey(ModItems.NOVELTY_DRINKING_HAT, "eatingSpeedBonus"), 0.50,
                    "How much the Novelty Drinking Hat increases the wearer's eating speed"),
            plasticDrinkingHatDrinkingSpeedBonus = defineAttributeModifier(createKey(ModItems.PLASTIC_DRINKING_HAT, "drinkingSpeedBonus"), 1.50,
                    "How much the Plastic Drinking Hat increases the wearer's drinking speed"),
            plasticDrinkingHatEatingSpeedBonus = defineAttributeModifier(createKey(ModItems.PLASTIC_DRINKING_HAT, "eatingSpeedBonus"), 0.50,
                    "How much the Plastic Drinking Hat increases the wearer's eating speed"),
            pocketPistonAttackKnockbackBonus = defineAttributeModifier(createKey(ModItems.POCKET_PISTON, "attackKnockbackBonus"), 0.75,
                    "The amount of extra knockback that is granted by the Pocket Piston"),
            powerGloveAttackDamageBonus = defineAttributeModifier(createKey(ModItems.POWER_GLOVE, "attackDamageBonus"), 4,
                    "The amount of extra damage that is dealt by melee attacks from players wearing the Power Glove"),
            runningShoesSprintingSpeedBonus = defineAttributeModifier(createKey(ModItems.RUNNING_SHOES, "sprintingSpeedBonus"), 0.40,
                    "How much the Running Shoes increase the wearer's sprinting speed"),
            runningShoesSprintingStepHeightBonus = defineAttributeModifier(createKey(ModItems.RUNNING_SHOES, "sprintingStepHeightBonus"), 0.5,
                    "How much the Running Shoes increase the wearer's step height while sprinting"),
            snowshoesMovementSpeedOnSnowBonus = defineAttributeModifier(createKey(ModItems.SNOWSHOES, "movementSpeedOnSnowBonus"), 0.30,
                    "How much the Snowshoes increase the wearer's movement speed on snow blocks"),
            steadfastSpikesKnockbackResistance = defineAttributeModifier(createKey(ModItems.STEADFAST_SPIKES, "knockbackResistance"), 1.00,
                    "How much knockback resistance is granted by the Steadfast Spikes"),
            steadfastSpikesSlipperinessReduction = defineAttributeModifier(createKey(ModItems.STEADFAST_SPIKES, "slipperinessReduction"), 1.00,
                    "How much the Steadfast Spikes reduce the slipperiness of ice"),
            villagerHatReputationBonus = defineAttributeModifier(createKey(ModItems.VILLAGER_HAT, "reputationBonus"), 75,
                    "The amount of extra reputation that is granted by the Villager Hat when trading with villagers"),
            charmOfSinkingOxygenBonus = defineAttributeModifier(createKey(ModItems.CHARM_OF_SINKING, "oxygenBonus"), 1.5D,
                    "How much longer players wearing the Charm of Sinking can stay underwater");

    public final ConfigValue<Double>
            whoopeeCushionFartChance = defineFraction(createKey(ModItems.WHOOPEE_CUSHION, "fartChance"), 0.12,
                    "The probability that a fart sound plays when sneaking " +
                    "or double jumping while wearing the Whoopee Cushion"),
            chorusTotemTeleportationChance = defineFraction(createKey(ModItems.CHORUS_TOTEM, "teleportationChance"), 1.00,
                    "The probability that the Chorus Totem activates when a player dies"),
            flamePendantStrikeChance = defineFraction(createKey(ModItems.FLAME_PENDANT, "strikeChance"), 0.40,
                    "The probability that the Flame Pendant lights an attacker on fire"),
            nightVisionGogglesStrength = defineFraction(createKey(ModItems.NIGHT_VISION_GOGGLES, "strength"), 0.15,
                    "The strength of the night vision effect applied by the Night Vision Goggles"),
            thornPendantStrikeChance = defineFraction(createKey(ModItems.THORN_PENDANT, "strikeChance"), 0.50,
                    "The probability that the Thorn Pendant damages an attacking entity"),
            shockPendantStrikeChance = defineFraction(createKey(ModItems.SHOCK_PENDANT, "strikeChance"), 0.25,
                    "The probability that the Shock Pendant strikes an attacking entity with lightning"),
            vampiricGloveAbsorptionChance = defineFraction(createKey(ModItems.VAMPIRIC_GLOVE, "absorptionChance"), 1,
                    "The probability that damage is absorbed when attacking an entity with the Vampiric Gloves"),
            witheredBraceletWitherChance = defineFraction(createKey(ModItems.WITHERED_BRACELET, "witherChance"), 0.3,
                    "The probability that the Withered Bracelet inflicts a wither effect"),
            everlastingBeefDropRate = defineFraction(createKey(ModItems.EVERLASTING_BEEF, "dropRate"), 1 / 500D,
                    "The probability that Everlasting Beef drops when a cow or mooshroom is killed by a player"),
            cloudInABottleFallDamageMultiplier = defineFraction(createKey(ModItems.CLOUD_IN_A_BOTTLE, "fallDamageMultiplier"), 0D,
                    "How much fall damage is dealt when double jumping with the Cloud in a Bottle");

    public final ConfigValue<Integer>
            chorusTotemHealthRestored = defineNonNegativeInt(createKey(ModItems.CHORUS_TOTEM, "healthRestored"), 10,
                    "The amount of health points that are restored after the Chorus Totem activates"),
            thornPendantMaxDamage = defineNonNegativeInt(createKey(ModItems.THORN_PENDANT, "maxDamage"), 6,
                    "The minimum amount of damage that is dealt when the Thorn Pendant activates"),
            thornPendantMinDamage = defineNonNegativeInt(createKey(ModItems.THORN_PENDANT, "minDamage"), 2,
                    "The maximum amount of damage that is dealt when the Thorn Pendant activates"),
            warpDriveHungerCost = defineNonNegativeInt(createKey(ModItems.WARP_DRIVE, "hungerCost"), 2,
                    "How many hunger points it costs to throw an Ender Pearl using the Warp Drive"),
            vampiricGloveMaxHealingPerHit = defineNonNegativeInt(createKey(ModItems.VAMPIRIC_GLOVE, "maxHealingPerHit"), 6,
                    "The maximum amount of healing that can be absorbed in a single hit " +
                            "when attacking an entity while wearing the Vampiric Glove");

    public final ConfigValue<Integer>
            antidoteVesselMaxEffectDuration = defineDuration(createKey(ModItems.ANTIDOTE_VESSEL, "maxEffectDuration"), 5,
                    "The maximum duration in seconds negative mob effects can last when wearing the Antidote Vessel"),
            chorusTotemCooldown = defineDuration(createKey(ModItems.CHORUS_TOTEM, "cooldown"), 0,
                    "The duration in seconds the Chorus Totem goes on cooldown for after activating"),
            crossNecklaceCooldown = defineDuration(createKey(ModItems.CROSS_NECKLACE, "cooldown"), 0,
                    "The duration in seconds the Cross Necklace goes on cooldown for after activating"),
            eternalSteakCooldown = defineDuration(createKey(ModItems.ETERNAL_STEAK, "cooldown"), 15,
                    "The duration in seconds the Eternal Steak goes on cooldown for after being eaten"),
            everlastingBeefCooldown = defineDuration(createKey(ModItems.EVERLASTING_BEEF, "cooldown"), 15,
                    "The duration in seconds the Everlasting Beef goes on cooldown for after being eaten"),
            flamePendantCooldown = defineDuration(createKey(ModItems.FLAME_PENDANT, "cooldown"), 0,
                    "The duration in seconds the Flame Pendant goes on cooldown for after setting an entity on fire"),
            flamePendantFireDuration = defineDuration(createKey(ModItems.FLAME_PENDANT, "fireDuration"), 10,
                    "How long an attacking entity is set on fire for when the Flame Pendant activates"),
            heliumFlamingoFlightDuration = defineDuration(createKey(ModItems.HELIUM_FLAMINGO, "flightDuration"), 8,
                    "The amount of time in seconds a player can fly with the Helium Flamingo before needing to recharge"),
            heliumFlamingoRechargeDuration = defineDuration(createKey(ModItems.HELIUM_FLAMINGO, "rechargeDuration"), 15,
                    "The amount of time in seconds it takes for the Helium Flamingo to recharge"),
            heliumFlamingoCooldown = defineDuration(createKey(ModItems.HELIUM_FLAMINGO, "cooldown"), 3,
                    "The duration in seconds the Helium Flamingo goes on cooldown for when stopping flight"),
            obsidianSkullCooldown = defineDuration(createKey(ModItems.OBSIDIAN_SKULL, "cooldown"), 60,
                    "The amount of time in seconds the Obsidian Skull goes on cooldown for after taking fire damage"),
            obsidianSkullFireResistanceDuration = defineDuration(createKey(ModItems.OBSIDIAN_SKULL, "fireResistanceDuration"), 30,
                    "The duration of the fire resistance effect that is applied when taking fire damage while wearing the Obsidian Skull"),
            onionRingHasteDurationPerFoodPoint = defineDuration(createKey(ModItems.ONION_RING, "hasteDurationPerFoodPoint"), 6,
                    "The duration of haste that is applied per food point eaten while wearing the Onion Ring"),
            panicNecklaceCooldown = defineDuration(createKey(ModItems.PANIC_NECKLACE, "cooldown"), 0,
                    "The duration in seconds the Panic Necklace goes on cooldown for after taking damage"),
            panicNecklaceSpeedDuration = defineDuration(createKey(ModItems.PANIC_NECKLACE, "speedDuration"), 8,
                    "The duration in seconds of the speed effect that is applied when taking damage while wearing the Panic Necklace"),
            rootedBootsHungerReplenishingDuration = defineDuration(createKey(ModItems.ROOTED_BOOTS, "hungerReplenishingDuration"), 10,
                    "The amount of time in seconds it takes to replenish a single point of hunger while wearing the Rooted Boots"),
            shockPendantCooldown = defineDuration(createKey(ModItems.SHOCK_PENDANT, "cooldown"), 0,
                    "The amount of time in seconds the Shock Pendant goes on cooldown for " +
                            "after striking an attacker with lightning"),
            snorkelWaterBreathingDuration = defineDuration(createKey(ModItems.SNORKEL, "waterBreathingDuration"), 30,
                    "The duration of the water breathing effect that is applied by the Snorkel"),
            thornPendantCooldown = defineDuration(createKey(ModItems.THORN_PENDANT, "cooldown"), 0,
                    "The duration in seconds the Thorn Pendant goes on cooldown for after activating"),
            warpDriveCooldown = defineDuration(createKey(ModItems.WARP_DRIVE, "cooldown"), 0,
                    "The duration Ender Pearls go on cooldown for after being thrown using the Warp Drive"),
            witheredBraceletCooldown = defineDuration(createKey(ModItems.WITHERED_BRACELET, "cooldown"), 0,
                    "The duration the Withered Bracelet goes on cooldown for after inflicting wither on an entity"),
            witheredBraceletWitherDuration = defineDuration(createKey(ModItems.WITHERED_BRACELET, "witherDuration"), 8,
                    "The duration of the wither effect applied by the Withered Bracelet");

    public final ConfigValue<Integer>
            anglersHatLuckOfTheSeaLevelBonus = defineEnchantmentLevel(createKey(ModItems.ANGLERS_HAT, "luckOfTheSeaLevelBonus"), 1,
                    "The amount of extra levels of luck of the sea that are granted by the Angler's Hat"),
            anglersHatLureLevelBonus = defineEnchantmentLevel(createKey(ModItems.ANGLERS_HAT, "lureLevelBonus"), 1,
                    "The amount of extra levels of lure that are granted by the Angler's Hat"),
            luckScarfFortuneBonus = defineEnchantmentLevel(createKey(ModItems.LUCKY_SCARF, "fortuneLevelBonus"), 1,
                    "The amount of extra levels of fortune that are granted by the Lucky Scarf"),
            superstitiousHatLootingLevelBonus = defineEnchantmentLevel(createKey(ModItems.SUPERSTITIOUS_HAT, "lootingLevelBonus"), 1,
                    "The amount of extra levels of Looting that are granted by the Superstitious Hat");

    public final ConfigValue<Integer>
            onionRingHasteLevel = defineMobEffectLevel(createKey(ModItems.ONION_RING, "hasteLevel"), 2,
                    "The level of the haste effect that is applied by the Onion Ring"),
            panicNecklaceSpeedLevel = defineMobEffectLevel(createKey(ModItems.PANIC_NECKLACE, "speedLevel"), 1,
                    "The level of the speed effect that is applied by the Panic Necklace"),
            universalAttractorMagnetismLevel = defineMobEffectLevel(createKey(ModItems.UNIVERSAL_ATTRACTOR, "magnetismLevel"), 5,
                    "The level of the magnetism effect that is applied by the Universal Attractor"),
            witheredBraceletWitherLevel = defineMobEffectLevel(createKey(ModItems.WITHERED_BRACELET, "witherLevel"), 2,
                    "The level of the wither effect that is inflicted by the Withered Bracelet");

    public final ConfigValue<ToolTierUpgrade.Tier>
            diggingClawsToolTier = defineEnum(createKey(ModItems.DIGGING_CLAWS, "toolTier"), ValueTypes.TOOL_TIER, ToolTierUpgrade.Tier.STONE, false,
                    "The tool tier that the Digging Claws increase the wearer's mining level to");

    protected ItemConfigs() {
        super("items");
        for (RegistryHolder<Item, ?> entry : ModItems.ITEMS.getEntries()) {
            if (entry != ModItems.ETERNAL_STEAK && entry != ModItems.MIMIC_SPAWN_EGG) {
                generateAsLoot.put(entry.unwrapKey().orElseThrow(), defineBool(createKey(entry, "generateAsLoot"),
                        "Whether this item can be found in structures or drop from entities"
                ));
            }
        }
    }

    private static String createKey(Holder<? extends Item> holder, String name) {
        return holder.unwrapKey().orElseThrow().location().getPath() + '.' + name;
    }

    public Value<Boolean> generatesAsLoot(Item item) {
        Optional<ResourceKey<Item>> key = BuiltInRegistries.ITEM.getResourceKey(item);
        if (key.isEmpty() || !generateAsLoot.containsKey(key.get())) {
            return null;
        }
        return generateAsLoot.get(key.get());
    }

    @Override
    public void onConfigChanged() {
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
}
