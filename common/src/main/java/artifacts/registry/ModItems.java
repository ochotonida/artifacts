package artifacts.registry;

import artifacts.Artifacts;
import artifacts.component.HurtSound;
import artifacts.component.ToggleIdentifier;
import artifacts.component.ability.*;
import artifacts.component.ability.mobeffect.AttackEffect;
import artifacts.component.ability.mobeffect.MobEffectProvider;
import artifacts.component.ability.mobeffect.PostDamageEffect;
import artifacts.component.ability.mobeffect.PostEatingEffect;
import artifacts.component.ability.retaliation.FireEffect;
import artifacts.component.ability.retaliation.LightningEffect;
import artifacts.component.ability.retaliation.RetaliationEffects;
import artifacts.component.ability.retaliation.ThornsEffect;
import artifacts.config.value.Value;
import artifacts.item.ArtifactProperties;
import artifacts.item.consumeeffects.HealConsumeEffect;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.feline.CatSoundVariants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.TeleportRandomlyConsumeEffect;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.equipment.Equippable;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {

    public static final Register<Item> ITEMS = Register.create(Registries.ITEM);
    public static final Register<CreativeModeTab> CREATIVE_MODE_TABS = Register.create(Registries.CREATIVE_MODE_TAB);

    @SuppressWarnings({"DataFlowIssue", "unused"})
    public static final RegistryHolder<CreativeModeTab, CreativeModeTab> CREATIVE_MODE_TAB = ModItems.CREATIVE_MODE_TABS.register(
            "main",
            () -> CreativeModeTab.builder(null, 0)
                    .title(Component.translatable("%s.creative_tab".formatted(Artifacts.MOD_ID)))
                    .icon(() -> new ItemStack(ModItems.BUNNY_HOPPERS.value()))
                    .displayItems((itemDisplayParameters, output) -> ITEMS.forEach(output::accept))
                    .build()
    );

    public static final RegistryHolder<Item, Item> MIMIC_SPAWN_EGG = register("mimic_spawn_egg", SpawnEggItem::new,
            () -> new Item.Properties().spawnEgg(ModEntityTypes.MIMIC.get())
    );

    // TODO: add some sort of overlay to display equipped items that are losing durability
    // handheld
    public static final RegistryHolder<Item, Item> UMBRELLA
            = register("umbrella", () -> Artifacts.CONFIG.items.umbrella, (builder, config) -> builder
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.OFFHAND).setSwappable(false).build())
            .durability(config.durability)
            .breakSound(SoundEvents.SHIELD_BREAK)
            .component(ModDataComponents.HANDHELD_GLIDER.get(), config.isGlider)
            .component(ModDataComponents.BLOCKS_ATTACKS.get(), config.isShield)
            .delayedComponent(DataComponents.BLOCKS_ATTACKS, config.isShield,
                    context -> new BlocksAttacks(
                            0.25F,
                            1,
                            List.of(new BlocksAttacks.DamageReduction(90, Optional.empty(), 0, 1)),
                            new BlocksAttacks.ItemDamageFunction(
                                    3,
                                    config.durability.damagePerBlockedAttackBase.get(),
                                    config.durability.damagePerBlockedAttackFactor.get().floatValue()
                            ),
                            Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                            Optional.of(SoundEvents.SHIELD_BLOCK),
                            Optional.of(SoundEvents.SHIELD_BREAK)
                    )
            )
            .component(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int) (0.75F * 20)))
            .component(
                    DataComponents.PIERCING_WEAPON,
                    new PiercingWeapon(
                            true,
                            false,
                            Optional.of(SoundEvents.SPEAR_WOOD_ATTACK),
                            Optional.of(SoundEvents.SPEAR_WOOD_HIT)
                    )
            )
            .properties(p -> p.delayedHolderComponent(DataComponents.DAMAGE_TYPE, DamageTypes.SPEAR))
            .component(
                    DataComponents.ATTACK_RANGE,
                    new AttackRange(0, 3.5F, 0, 5.5F, 0.25F, 0.5F))
            .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1F)
            .delayedComponent(DataComponents.WEAPON, _ -> new Weapon(config.durability.damagePerAttack.get()))
            .properties(p -> p.attributes(
                    ItemAttributeModifiers.builder()
                            .add(
                                    Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(
                                            Item.BASE_ATTACK_DAMAGE_ID,
                                            ToolMaterial.STONE.attackDamageBonus(),
                                            AttributeModifier.Operation.ADD_VALUE
                                    ),
                                    EquipmentSlotGroup.MAINHAND
                            ).add(
                                    Attributes.ATTACK_SPEED,
                                    new AttributeModifier(
                                            Item.BASE_ATTACK_SPEED_ID,
                                            (1 / 0.75F) - 4,
                                            AttributeModifier.Operation.ADD_VALUE
                                    ),
                                    EquipmentSlotGroup.MAINHAND
                            ).build()
                    )
            )
    );
    public static final RegistryHolder<Item, Item> EVERLASTING_BEEF
            = register("everlasting_beef", () -> Artifacts.CONFIG.items.everlastingBeef, (builder, config) -> builder
            .durability(config.durability)
            .damageWhenConsumed(config.enabled, config.durability.damageWhenConsumed)
            .useCooldown(config.cooldown)
            .component(DataComponents.FOOD, Foods.BEEF)
            .component(ModDataComponents.INFINITE_CONSUMABLE.get(), config.enabled)
    );
    public static final RegistryHolder<Item, Item> ETERNAL_STEAK
            = register("eternal_steak", () -> Artifacts.CONFIG.items.eternalSteak, (builder, config) -> builder
            .durability(config.durability)
            .damageWhenConsumed(config.enabled, config.durability.damageWhenConsumed)
            .useCooldown(config.cooldown)
            .component(DataComponents.FOOD, Foods.COOKED_BEEF)
            .component(ModDataComponents.INFINITE_CONSUMABLE.get(), config.enabled)
    );

    // head
    public static final RegistryHolder<Item, Item> PLASTIC_DRINKING_HAT
            = register("plastic_drinking_hat", () -> Artifacts.CONFIG.items.plasticDrinkingHat, (builder, config) -> builder
            .equipable(SoundEvents.BOTTLE_FILL)
            .durability(config.durability)
            .damageOnItemConsumed(config.durability.damagePerItemEaten, config.durability.damagePerItemDrunk)
            .modifiesAttributeBase(ModAttributes.DRINKING_SPEED, config.drinkingSpeedBonus)
            .modifiesAttributeBase(ModAttributes.EATING_SPEED, config.eatingSpeedBonus)
    );
    public static final RegistryHolder<Item, Item> NOVELTY_DRINKING_HAT
            = register("novelty_drinking_hat", () -> Artifacts.CONFIG.items.noveltyDrinkingHat, (builder, config) -> builder
            .equipable(SoundEvents.BOTTLE_FILL)
            .durability(config.durability)
            .damageOnItemConsumed(config.durability.damagePerItemEaten, config.durability.damagePerItemDrunk)
            .abilityLore(Component.translatable("artifacts.tooltip.item.novelty_drinking_hat"))
            .modifiesAttributeBase(ModAttributes.DRINKING_SPEED, config.drinkingSpeedBonus)
            .modifiesAttributeBase(ModAttributes.EATING_SPEED, config.eatingSpeedBonus)
    );
    public static final RegistryHolder<Item, Item> SNORKEL
            = register("snorkel", () -> Artifacts.CONFIG.items.snorkel, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.LOSING_AIR)
            .mobEffect(
                    MobEffects.WATER_BREATHING,
                    Value.of(1),
                    config.waterBreathingDuration,
                    () -> config.isInfinite.get() ? EntityCondition.ALWAYS : EntityCondition.REPLENISHING_AIR
            )
    );
    public static final RegistryHolder<Item, Item> NIGHT_VISION_GOGGLES
            = register("night_vision_goggles", () -> Artifacts.CONFIG.items.nightVisionGoggles, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.ALWAYS)
            .toggleKey(ToggleIdentifier.NIGHT_VISION_GOGGLES)
            .mobEffect(MobEffects.NIGHT_VISION, Value.of(1), Value.of(10), () -> EntityCondition.ALWAYS)
            .component(ModDataComponents.REDUCED_NIGHT_VISION.get(), config.strength)
    );
    public static final RegistryHolder<Item, Item> VILLAGER_HAT
            = register("villager_hat", () -> Artifacts.CONFIG.items.villagerHat, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .component(ModDataComponents.DAMAGE_ON_TRADE.get(), config.durability.damagePerTrade)
            .increasesAttribute(ModAttributes.VILLAGER_REPUTATION, config.reputationBonus)
    );
    public static final RegistryHolder<Item, Item> SUPERSTITIOUS_HAT
            = register("superstitious_hat", () -> Artifacts.CONFIG.items.superstitiousHat, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOnKill(config.durability.damagePerKill)
            .increasesEnchantment(Enchantments.LOOTING, config.lootingLevelBonus)
    );
    public static final RegistryHolder<Item, Item> COWBOY_HAT
            = register("cowboy_hat", () -> Artifacts.CONFIG.items.cowboyHat, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.RIDING_MOUNT)
            .modifiesAttributeBase(ModAttributes.MOUNT_SPEED, config.mountSpeedBonus)
    );
    public static final RegistryHolder<Item, Item> ANGLERS_HAT
            = register("anglers_hat", () -> Artifacts.CONFIG.items.anglersHat, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .durability(config.durability)
            .component(ModDataComponents.DAMAGE_ON_ITEM_FISHED.get(), config.durability.damagePerItemFished)
            .increasesEnchantment(Enchantments.LUCK_OF_THE_SEA, config.luckOfTheSeaLevelBonus)
            .increasesEnchantment(Enchantments.LURE, config.lureLevelBonus)
    );

    // necklace
    public static final RegistryHolder<Item, Item> LUCKY_SCARF
            = register("lucky_scarf", () -> Artifacts.CONFIG.items.luckyScarf, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOnOreMined(config.durability.damagePerOreMined)
            .increasesEnchantment(Enchantments.FORTUNE, config.fortuneLevelBonus)
    );
    public static final RegistryHolder<Item, Item> SCARF_OF_INVISIBILITY
            = register("scarf_of_invisibility", () -> Artifacts.CONFIG.items.scarfOfInvisibility, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.ALWAYS)
            .toggleKey(ToggleIdentifier.SCARF_OF_INVISIBILITY)
            .mobEffect(
                    MobEffects.INVISIBILITY,
                    Value.of(1),
                    Value.of(10),
                    () -> config.enabled.get() ? EntityCondition.ALWAYS : EntityCondition.NEVER
            )
            .component(ModDataComponents.HIDE_WHEN_INVISIBLE.get(), config.hideWhenInvisible)
            .component(ModDataComponents.HIDES_EFFECT_PARTICLES.get(), config.hideEffectParticles)
    );
    public static final RegistryHolder<Item, Item> CROSS_NECKLACE
            = register("cross_necklace", () -> Artifacts.CONFIG.items.crossNecklace, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .piglinLoved()
            .durability(config.durability)
            .damageOnHurt(config.durability.damagePerActivation)
            .cooldownOnHurt(config.cooldown)
            .addAttributeModifier(
                    ModAttributes.INVINCIBILITY_TICKS,
                    config.bonusInvincibilityTicks,
                    AttributeModifier.Operation.ADD_VALUE,
                    () -> true, false
            )
    );
    public static final RegistryHolder<Item, Item> PANIC_NECKLACE
            = register("panic_necklace", () -> Artifacts.CONFIG.items.panicNecklace, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .durability(config.durability)
            .damageOnHurt(config.durability.damagePerActivation)
            .cooldownOnHurt(config.cooldown)
            .compositeComponent(
                    ModDataComponents.POST_DAMAGE_EFFECTS.get(),
                    _ -> new PostDamageEffect(
                            new MobEffectProvider(
                                    MobEffects.SPEED,
                                    config.speedLevel,
                                    config.speedDuration,
                                    Value.of(true),
                                    Value.of(true),
                                    EntityCondition.ALWAYS
                            ),
                            Optional.empty()
                    )
            )
    );
    public static final RegistryHolder<Item, Item> SHOCK_PENDANT
            = register("shock_pendant", () -> Artifacts.CONFIG.items.shockPendant, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .component(
                    ModDataComponents.RETALIATION_EFFECTS.get(),
                    new RetaliationEffects(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of(new LightningEffect(config.activationParams()))
                    )
            )
            .component(ModDataComponents.DAMAGE_IMMUNITY.get(),
                    new DamageImmunity(
                            config.cancelLightningDamage,
                            DamageTypeTags.IS_LIGHTNING,
                            EntityCondition.ALWAYS
                    )
            )
    );
    public static final RegistryHolder<Item, Item> FLAME_PENDANT
            = register("flame_pendant", () -> Artifacts.CONFIG.items.flamePendant, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .component(
                    ModDataComponents.RETALIATION_EFFECTS.get(),
                    new RetaliationEffects(
                            Optional.empty(),
                            Optional.of(new FireEffect(config.activationParams(), config.fireDuration, config.grantFireResistance)),
                            Optional.empty()
                    )
            )
    );
    public static final RegistryHolder<Item, Item> THORN_PENDANT
            = register("thorn_pendant", () -> Artifacts.CONFIG.items.thornPendant, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .component(
                    ModDataComponents.RETALIATION_EFFECTS.get(),
                    new RetaliationEffects(
                            Optional.of(new ThornsEffect(config.activationParams(), config.minDamage, config.maxDamage)),
                            Optional.empty(),
                            Optional.empty()
                    )
            )
    );
    public static final RegistryHolder<Item, Item> CHARM_OF_SINKING
            = register("charm_of_sinking", () -> Artifacts.CONFIG.items.charmOfSinking, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.UNDER_WATER)
            .toggleKey(ToggleIdentifier.CHARM_OF_SINKING)
            .component(ModDataComponents.SINKING.get(), config.enabled)
            .delayedComponent(ModDataComponents.DAMAGE_IMMUNITY.get(), _ -> new DamageImmunity(
                    config.enabled,
                    DamageTypeTags.IS_FALL,
                    config.underwaterFallDamage.get() ? EntityCondition.NEVER : EntityCondition.IN_WATER
            ))
            .addAttributeModifier(
                    Attributes.OXYGEN_BONUS,
                    config.oxygenBonus,
                    AttributeModifier.Operation.ADD_VALUE,
                    config.enabled,
                    true
            )
    );
    public static final RegistryHolder<Item, Item> CHARM_OF_SHRINKING
            = register("charm_of_shrinking", () -> Artifacts.CONFIG.items.charmOfShrinking, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.ALWAYS)
            .toggleKey(ToggleIdentifier.CHARM_OF_SHRINKING)
            .modifiesAttributeTotal(Attributes.SCALE, config.scaleModifier)
    );

    // belt
    public static final RegistryHolder<Item, Item> CLOUD_IN_A_BOTTLE
            = register("cloud_in_a_bottle", () -> Artifacts.CONFIG.items.cloudInABottle, (builder, config) -> builder
            .equipable(SoundEvents.BOTTLE_FILL_DRAGONBREATH)
            .durability(config.durability)
            .component(ModDataComponents.DOUBLE_JUMP.get(), new DoubleJump(
                    config.enabled,
                    config.fallDamageMultiplier,
                    config.sprintJumpHorizontalVelocity,
                    config.sprintJumpVerticalVelocity,
                    config.durability.damagePerDoubleJump
            ))
            .increasesAttribute(Attributes.SAFE_FALL_DISTANCE, config.safeFallDistanceBonus)
    );
    public static final RegistryHolder<Item, Item> OBSIDIAN_SKULL
            = register("obsidian_skull", () -> Artifacts.CONFIG.items.obsidianSkull, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_IRON)
            .durability(config.durability)
            .damageOnHurt(config.durability.damagePerActivation, DamageTypeTags.IS_FIRE)
            .cooldownOnHurt(config.cooldown, DamageTypeTags.IS_FIRE)
            .compositeComponent(
                    ModDataComponents.POST_DAMAGE_EFFECTS.get(),
                    _ -> new PostDamageEffect(
                            new MobEffectProvider(
                                    MobEffects.FIRE_RESISTANCE,
                                    Value.of(1),
                                    config.fireResistanceDuration,
                                    Value.of(true),
                                    Value.of(true),
                                    EntityCondition.ALWAYS
                            ),
                            Optional.of(DamageTypeTags.IS_FIRE)
                    )
            )
    );
    public static final RegistryHolder<Item, Item> ANTIDOTE_VESSEL
            = register("antidote_vessel", () -> Artifacts.CONFIG.items.antidoteVessel, (builder, config) -> builder
            .equipable(SoundEvents.BOTTLE_FILL)
            .piglinLoved()
            .durability(config.durability)
            .component(ModDataComponents.CURE_EFFECTS.get(), new CureEffects(
                    config.enabled,
                    config.maxEffectDuration,
                    config.durability.damagePerActivation
            ))
    );
    public static final RegistryHolder<Item, Item> UNIVERSAL_ATTRACTOR
            = register("universal_attractor", () -> Artifacts.CONFIG.items.universalAttractor, (builder, config) -> builder
            .equipable()
            .piglinLoved()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.ALWAYS)
            .toggleKey(ToggleIdentifier.UNIVERSAL_ATTRACTOR)
            .mobEffect(ModMobEffects.MAGNETISM, config.magnetismLevel, Value.of(10), () -> EntityCondition.ALWAYS)
    );
    public static final RegistryHolder<Item, Item> CRYSTAL_HEART
            = register("crystal_heart", () -> Artifacts.CONFIG.items.crystalHeart, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .durability(config.durability)
            .damageOnHurt(config.durability.damageWhenHurt)
            .increasesAttribute(Attributes.MAX_HEALTH, config.healthBonus)
    );
    public static final RegistryHolder<Item, Item> HELIUM_FLAMINGO
            = register("helium_flamingo", () -> Artifacts.CONFIG.items.heliumFlamingo, (builder, config) -> builder
            .equipable(ModSoundEvents.POP.holder())
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.SWIM_FLYING)
            .component(ModDataComponents.SWIM_IN_AIR.get(), new SwimInAir(
                    config.flightDuration,
                    config.rechargeDuration,
                    config.cooldown
            ))
    );
    public static final RegistryHolder<Item, Item> CHORUS_TOTEM
            = register("chorus_totem", () -> Artifacts.CONFIG.items.chorusTotem, (builder, config) -> builder
            .equipable()
            .component(ModDataComponents.EQUIPABLE_TOTEM.get(), new EquipableTotem(
                    config.enabled
            ))
            .delayedComponent(
                    DataComponents.DEATH_PROTECTION,
                    config.enabled,
                    _ -> new DeathProtection(List.of(
                            new ClearAllStatusEffectsConsumeEffect(),
                            new TeleportRandomlyConsumeEffect(32),
                            new HealConsumeEffect(config.healthRestored))
                    )
            )
    );
    public static final RegistryHolder<Item, Item> WARP_DRIVE
            = register("warp_drive", () -> Artifacts.CONFIG.items.warpDrive, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .component(ModDataComponents.ENDER_PEARL_HUNGER_COST.get(), new EnderPearlHungerCost(
                    config.enabled,
                    config.hungerCost,
                    config.durability.damagePerActivation,
                    config.cooldown
            ))
            .component(ModDataComponents.ENDER_PEARL_DAMAGE_IMMUNITY.get(), config.nullifyEnderPearlDamage)
    );

    // hands
    public static final RegistryHolder<Item, Item> DIGGING_CLAWS
            = register("digging_claws", () -> Artifacts.CONFIG.items.diggingClaws, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_NETHERITE)
            .durability(config.durability)
            .damageOnOreMined(config.durability.damagePerOreMined)
            .damageOnBlockMined(config.durability.damagePerBlockMined)
            .modifiesAttributeBase(Attributes.BLOCK_BREAK_SPEED, config.blockBreakSpeedBonus)
            .component(ModDataComponents.TOOL_TIER_UPGRADE.get(), new ToolTierUpgrade(config.toolTier, config.durability.damagePerBlockHarvested))
    );
    public static final RegistryHolder<Item, Item> FERAL_CLAWS
            = register("feral_claws", () -> Artifacts.CONFIG.items.feralClaws, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_NETHERITE)
            .durability(config.durability)
            .damageOnMeleeAttack(config.durability.damagePerAttack)
            .modifiesAttributeBase(Attributes.ATTACK_SPEED, config.attackSpeedBonus)
    );
    public static final RegistryHolder<Item, Item> POWER_GLOVE
            = register("power_glove", () -> Artifacts.CONFIG.items.powerGlove, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOnMeleeAttack(config.durability.damagePerAttack)
            .increasesAttribute(Attributes.ATTACK_DAMAGE, config.attackDamageBonus)
    );
    public static final RegistryHolder<Item, Item> FIRE_GAUNTLET
            = register("fire_gauntlet", () -> Artifacts.CONFIG.items.fireGauntlet, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_IRON)
            .durability(config.durability)
            .damageOnMeleeAttack(config.durability.damagePerAttack)
            .increasesAttribute(ModAttributes.ATTACK_BURNING_DURATION, config.fireDuration)
    );
    public static final RegistryHolder<Item, Item> POCKET_PISTON
            = register("pocket_piston", () -> Artifacts.CONFIG.items.pocketPiston, (builder, config) -> builder
            .equipable(SoundEvents.PISTON_EXTEND)
            .durability(config.durability)
            .damageOnMeleeAttack(config.durability.damagePerAttack)
            .increasesAttribute(Attributes.ATTACK_KNOCKBACK, config.attackKnockbackBonus)
    );
    public static final RegistryHolder<Item, Item> VAMPIRIC_GLOVE
            = register("vampiric_glove", () -> Artifacts.CONFIG.items.vampiricGlove, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .component(
                    ModDataComponents.DAMAGE_ABSORPTION.get(),
                    new DamageAbsorption(
                            config.absorptionRatio,
                            config.absorptionChance,
                            config.maxHealingPerHit,
                            config.durability.damagePerActivation
                    )
            )
    );
    public static final RegistryHolder<Item, Item> GOLDEN_HOOK
            = register("golden_hook", () -> Artifacts.CONFIG.items.goldenHook, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOnKill(config.durability.damagePerKill)
            .piglinLoved()
            .modifiesAttributeBase(ModAttributes.ENTITY_EXPERIENCE, config.entityExperienceBonus)
    );
    public static final RegistryHolder<Item, Item> ONION_RING
            = register("onion_ring", () -> Artifacts.CONFIG.items.onionRing, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .properties(properties -> properties.food(new FoodProperties.Builder().nutrition(2).build()))
            .compositeComponent(
                    ModDataComponents.POST_EATING_EFFECTS.get(),
                    _ -> new PostEatingEffect(
                            new MobEffectProvider(
                                    MobEffects.HASTE,
                                    config.hasteLevel,
                                    config.hasteDurationPerFoodPoint,
                                    Value.of(true),
                                    Value.of(true),
                                    EntityCondition.ALWAYS
                            ),
                            config.durability.damagePerActivation
                    )
            )
    );
    public static final RegistryHolder<Item, Item> PICKAXE_HEATER
            = register("pickaxe_heater", () -> Artifacts.CONFIG.items.pickaxeHeater, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_IRON)
            .durability(config.durability)
            .damageOnOreMined(config.durability.damagePerOreMined)
            .component(ModDataComponents.AUTO_SMELT.get(), config.enabled)
    );
    public static final RegistryHolder<Item, Item> WITHERED_BRACELET
            = register("withered_bracelet", () -> Artifacts.CONFIG.items.witheredBracelet, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .compositeComponent(
                    ModDataComponents.ATTACK_EFFECTS.get(),
                    _ -> new AttackEffect(
                            new MobEffectProvider(
                                    MobEffects.WITHER,
                                    config.witherLevel,
                                    config.witherDuration,
                                    Value.of(true),
                                    Value.of(true),
                                    EntityCondition.ALWAYS
                            ),
                            config.witherChance,
                            config.cooldown,
                            config.durability.damagePerActivation
                    )
            )
    );

    // feet
    public static final RegistryHolder<Item, Item> AQUA_DASHERS
            = register("aqua_dashers", () -> Artifacts.CONFIG.items.aquaDashers, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.ON_WATER)
            .component(
                    ModDataComponents.FLUID_COLLISION.get(),
                    new FluidCollision(config.enabled, Optional.empty(), EntityCondition.SPRINTING)
            )
    );
    public static final RegistryHolder<Item, Item> BUNNY_HOPPERS
            = register("bunny_hoppers", () -> Artifacts.CONFIG.items.bunnyHoppers, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .component(ModDataComponents.DAMAGE_ON_JUMP.get(), config.durability.damagePerJump)
            .component(ModDataComponents.DAMAGE_ON_FALL.get(), config.durability.damagePerFallSurvived)
            .modifiesAttributeBase(Attributes.JUMP_STRENGTH, config.jumpStrengthBonus)
            .modifiesAttributeBase(Attributes.FALL_DAMAGE_MULTIPLIER, config.fallDamageMultiplier)
            .increasesAttribute(Attributes.SAFE_FALL_DISTANCE, config.safeFallDistanceBonus)
            .component(
                    ModDataComponents.HURT_SOUND.get(),
                    new HurtSound(
                            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.RABBIT_HURT),
                            config.modifyHurtSounds
                    )
            )
    );
    @SuppressWarnings("deprecation")
    public static final RegistryHolder<Item, Item> KITTY_SLIPPERS
            = register("kitty_slippers", () -> Artifacts.CONFIG.items.kittySlippers, (builder, config) -> builder
            .equipable(SoundEvents.CAT_SOUNDS.get(CatSoundVariants.SoundSet.CLASSIC).adultSounds().ambientSound())
            .durability(config.durability)
            .damageOnKill(config.durability.damagePerCreeperScared, registries -> registries.getOrThrow(ModTags.CREEPERS))
            .damageOnKill(config.durability.damagePerPhantomScared, _ -> HolderSet.direct(EntityTypes.PHANTOM.builtInRegistryHolder()))
            .component(ModDataComponents.CREEPER_REPELLENT.get(), config.repelCreepers)
            .component(ModDataComponents.PHANTOM_REPELLENT.get(), config.repelPhantoms)
            .component(
                    ModDataComponents.HURT_SOUND.get(),
                    new HurtSound(
                            SoundEvents.CAT_SOUNDS.get(CatSoundVariants.SoundSet.CLASSIC).adultSounds().hurtSound(),
                            config.modifyHurtSounds
                    )
            )
    );
    public static final RegistryHolder<Item, Item> RUNNING_SHOES
            = register("running_shoes", () -> Artifacts.CONFIG.items.runningShoes, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.SPRINTING)
            .modifiesAttributeBase(ModAttributes.SPRINTING_SPEED, config.sprintingSpeedBonus)
            .increasesAttribute(ModAttributes.SPRINTING_STEP_HEIGHT, config.sprintingStepHeightBonus)
    );
    public static final RegistryHolder<Item, Item> SNOWSHOES
            = register("snowshoes", () -> Artifacts.CONFIG.items.snowshoes, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.ON_SNOW)
            .component(ModDataComponents.WALK_ON_POWDER_SNOW.get(), config.allowWalkingOnPowderedSnow)
            .modifiesAttributeBase(ModAttributes.MOVEMENT_SPEED_ON_SNOW, config.movementSpeedOnSnowBonus)
    );
    public static final RegistryHolder<Item, Item> STEADFAST_SPIKES
            = register("steadfast_spikes", () -> Artifacts.CONFIG.items.steadfastSpikes, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .increasesAttribute(Attributes.KNOCKBACK_RESISTANCE, config.knockbackResistance)
            .increasesAttribute(ModAttributes.SLIP_RESISTANCE, config.slipperinessReduction)
            .damageOnHurt(config.durability.damageWhenAttacked, ModTags.IS_MELEE)
    );
    public static final RegistryHolder<Item, Item> FLIPPERS
            = register("flippers", () -> Artifacts.CONFIG.items.flippers, (builder, config) -> builder
            .equipable()
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.SWIMMING)
            .modifiesAttributeBase(ModAttributes.SWIM_SPEED, config.swimSpeedBonus)
    );
    public static final RegistryHolder<Item, Item> ROOTED_BOOTS
            = register("rooted_boots", () -> Artifacts.CONFIG.items.rootedBoots, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .durability(config.durability)
            .component(
                    ModDataComponents.REPLENISH_HUNGER_ON_GRASS.get(),
                    new ReplenishHungerOnGrass(
                            config.enabled,
                            config.hungerReplenishingDuration,
                            config.durability.damagePerFoodPoint
                    )
            )
            .component(ModDataComponents.POST_EATING_PLANT_GROWTH.get(), config.growPlantsAfterEating)
    );
    public static final RegistryHolder<Item, Item> STRIDER_SHOES
            = register("strider_shoes", () -> Artifacts.CONFIG.items.striderShoes, (builder, config) -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .durability(config.durability)
            .damageOverTime(config.durability.damagePerSecondActive, EntityCondition.ON_LAVA)
            .component(
                    ModDataComponents.FLUID_COLLISION.get(),
                    new FluidCollision(config.enabled, Optional.of(FluidTags.LAVA), EntityCondition.SNEAKING)
            ).component(
                    ModDataComponents.DAMAGE_IMMUNITY.get(),
                    new DamageImmunity(config.cancelHotFloorDamage, DamageTypeTags.BURN_FROM_STEPPING, EntityCondition.ALWAYS)
            )
    );

    // curio
    public static final RegistryHolder<Item, Item> WHOOPEE_CUSHION
            = register("whoopee_cushion", () -> Artifacts.CONFIG.items.whoopeeCushion, (builder, config) -> builder
            .equipable(ModSoundEvents.FART.holder())
            .durability(config.durability)
            .component(ModDataComponents.DAMAGE_ON_FART.get(), config.durability.damagePerActivation)
            .increasesAttribute(ModAttributes.FLATULENCE, config.fartChance)
    );

    private static <CONFIG> RegistryHolder<Item, Item> register(
            String name,
            Supplier<CONFIG> configSupplier,
            BiConsumer<ArtifactProperties, CONFIG> consumer
    ) {
        return register(name, Item::new, () -> {
            ArtifactProperties builder = new ArtifactProperties(name);
            consumer.accept(builder, configSupplier.get());
            return builder.build();
        });
    }

    private static RegistryHolder<Item, Item> register(String name, Function<Item.Properties, ? extends Item> factory, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> factory.apply(properties.get().setId(Artifacts.key(Registries.ITEM, name))));
    }
}
