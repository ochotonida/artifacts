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
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {

    public static final Register<Item> ITEMS = Register.create(Registries.ITEM);
    public static final Register<CreativeModeTab> CREATIVE_MODE_TABS = Register.create(Registries.CREATIVE_MODE_TAB);

    @SuppressWarnings({"unused", "ConstantConditions"})
    public static final RegistryHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("main", () -> new CreativeModeTab.Builder(null, 0)
            .title(Component.translatable("%s.creative_tab".formatted(Artifacts.MOD_ID)))
            .icon(() -> new ItemStack(ModItems.BUNNY_HOPPERS.value()))
            .displayItems((parameters, output) -> ITEMS.forEach(output::accept))
            .build()
    );

    public static final Holder<Item> MIMIC_SPAWN_EGG = register("mimic_spawn_egg", SpawnEggItem::new,
            () -> new Item.Properties().spawnEgg(ModEntityTypes.MIMIC.get())
    );

    // handheld
    public static final Holder<Item> UMBRELLA = register("umbrella", properties -> properties
            .component(ModDataComponents.HANDHELD_GLIDER.get(), Artifacts.CONFIG.items.umbrellaIsGlider)
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.OFFHAND).setSwappable(false).build())
            .component(ModDataComponents.BLOCKS_ATTACKS.get(), Artifacts.CONFIG.items.umbrellaIsShield)
            .component(DataComponents.BLOCKS_ATTACKS, Artifacts.CONFIG.items.umbrellaIsShield,
                    new BlocksAttacks(
                            0.25F,
                            1,
                            List.of(new BlocksAttacks.DamageReduction(90, Optional.empty(), 0, 1)),
                            new BlocksAttacks.ItemDamageFunction(3, 1, 1),
                            Optional.of(DamageTypeTags.BYPASSES_SHIELD),
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
            .component(DataComponents.DAMAGE_TYPE, new EitherHolder<>(DamageTypes.SPEAR))
            .component(DataComponents.ATTACK_RANGE, new AttackRange(0, 3.5F, 0, 5.5F, 0.25F, 0.5F))
            .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1F)
            .component(DataComponents.WEAPON, new Weapon(1))
            .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK)
            .properties(p -> p.attributes(ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, ToolMaterial.STONE.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (1 / 0.75F) - 4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .build()
            ))
    );
    public static final Holder<Item> EVERLASTING_BEEF = register("everlasting_beef", builder -> builder
            .component(DataComponents.FOOD, Artifacts.CONFIG.items.everlastingBeefEnabled, Foods.BEEF)
            .component(DataComponents.CONSUMABLE, Artifacts.CONFIG.items.everlastingBeefEnabled, Consumables.DEFAULT_FOOD)
            .component(
                    DataComponents.USE_COOLDOWN,
                    Artifacts.CONFIG.items.everlastingBeefEnabled,
                    new UseCooldown(Artifacts.CONFIG.items.everlastingBeefCooldown.get())
            )
            .component(ModDataComponents.INFINITE_CONSUMABLE.get(), Artifacts.CONFIG.items.everlastingBeefEnabled)
    );
    public static final Holder<Item> ETERNAL_STEAK = register("eternal_steak", builder -> builder
            .component(DataComponents.FOOD, Artifacts.CONFIG.items.eternalSteakEnabled, Foods.COOKED_BEEF)
            .component(DataComponents.CONSUMABLE, Artifacts.CONFIG.items.eternalSteakEnabled, Consumables.DEFAULT_FOOD)
            .component(
                    DataComponents.USE_COOLDOWN,
                    Artifacts.CONFIG.items.eternalSteakEnabled,
                    new UseCooldown(Artifacts.CONFIG.items.eternalSteakCooldown.get())
            )
            .component(ModDataComponents.INFINITE_CONSUMABLE.get(), Artifacts.CONFIG.items.eternalSteakEnabled)
    );

    // head
    public static final Holder<Item> PLASTIC_DRINKING_HAT = register("plastic_drinking_hat", builder -> builder
            .equipable(SoundEvents.BOTTLE_FILL)
            .addAttributeModifier(ModAttributes.DRINKING_SPEED, Artifacts.CONFIG.items.plasticDrinkingHatDrinkingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(ModAttributes.EATING_SPEED, Artifacts.CONFIG.items.plasticDrinkingHatEatingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> NOVELTY_DRINKING_HAT = register("novelty_drinking_hat", builder -> builder
            .equipable(SoundEvents.BOTTLE_FILL)
            .component(ModDataComponents.ABILITY_LORE.get(),
                    new ItemLore(List.of(Component.translatable("artifacts.tooltip.item.novelty_drinking_hat").withStyle(ChatFormatting.GRAY)))
            )
            .addAttributeModifier(ModAttributes.DRINKING_SPEED, Artifacts.CONFIG.items.noveltyDrinkingHatDrinkingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(ModAttributes.EATING_SPEED, Artifacts.CONFIG.items.noveltyDrinkingHatEatingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> SNORKEL = register("snorkel", builder -> builder
            .equipable()
            .mobEffect(MobEffects.WATER_BREATHING, Value.of(1), Artifacts.CONFIG.items.snorkelWaterBreathingDuration,
                    Artifacts.CONFIG.items.snorkelIsInfinite.get() ? EntityCondition.ALWAYS : EntityCondition.ABOVE_WATER
            )
    );
    public static final Holder<Item> NIGHT_VISION_GOGGLES = register("night_vision_goggles", builder -> builder
            .equipable()
            .mobEffect(MobEffects.NIGHT_VISION, Value.of(1), Value.of(10), EntityCondition.ALWAYS)
            .component(ModDataComponents.REDUCED_NIGHT_VISION.get(), Artifacts.CONFIG.items.nightVisionGogglesStrength)
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.NIGHT_VISION_GOGGLES)
    );
    public static final Holder<Item> VILLAGER_HAT = register("villager_hat", builder -> builder
            .equipable()
            .addAttributeModifier(ModAttributes.VILLAGER_REPUTATION, Artifacts.CONFIG.items.villagerHatReputationBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> SUPERSTITIOUS_HAT = register("superstitious_hat", builder -> builder
            .equipable()
            .increasesEnchantment(Enchantments.LOOTING, Artifacts.CONFIG.items.superstitiousHatLootingLevelBonus)
    );
    public static final Holder<Item> COWBOY_HAT = register("cowboy_hat", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .addAttributeModifier(ModAttributes.MOUNT_SPEED, Artifacts.CONFIG.items.cowboyHatMountSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> ANGLERS_HAT = register("anglers_hat", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .increasesEnchantment(Enchantments.LUCK_OF_THE_SEA, Artifacts.CONFIG.items.anglersHatLuckOfTheSeaLevelBonus)
            .increasesEnchantment(Enchantments.LURE, Artifacts.CONFIG.items.anglersHatLureLevelBonus)
    );

    // necklace
    public static final Holder<Item> LUCKY_SCARF = register("lucky_scarf", builder -> builder
            .equipable()
            .increasesEnchantment(Enchantments.FORTUNE, Artifacts.CONFIG.items.luckScarfFortuneBonus)
    );
    public static final Holder<Item> SCARF_OF_INVISIBILITY = register("scarf_of_invisibility", builder -> builder
            .equipable()
            .mobEffect(MobEffects.INVISIBILITY, Value.of(1), Value.of(10),
                    Artifacts.CONFIG.items.scarfOfInvisibilityEnabled.get() ? EntityCondition.ALWAYS : EntityCondition.NEVER
            )
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.SCARF_OF_INVISIBILITY)
            .component(ModDataComponents.HIDE_WHEN_INVISIBLE.get(), Artifacts.CONFIG.items.scarfOfInvisibilityHideWhenInvisible)
    );
    public static final Holder<Item> CROSS_NECKLACE = register("cross_necklace", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .component(ModDataComponents.PIGLIN_LOVED.get())
            .component(ModDataComponents.POST_DAMAGE_COOLDOWN.get(), new PostDamageCooldown(Artifacts.CONFIG.items.crossNecklaceCooldown, Optional.empty()))
            .addAttributeModifier(ModAttributes.INVINCIBILITY_TICKS, Artifacts.CONFIG.items.crossNecklaceBonusInvincibilityTicks, AttributeModifier.Operation.ADD_VALUE, false)
    );
    public static final Holder<Item> PANIC_NECKLACE = register("panic_necklace", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .component(ModDataComponents.POST_DAMAGE_EFFECTS.get(), new CompositeAbility<>(List.of(new PostDamageEffect(
                    new MobEffectProvider(MobEffects.SPEED, Artifacts.CONFIG.items.panicNecklaceSpeedLevel, Artifacts.CONFIG.items.panicNecklaceSpeedDuration, Value.of(true), Value.of(true), EntityCondition.ALWAYS),
                    Optional.empty(),
                    Value.of(1D)
            ))))
            .component(ModDataComponents.POST_DAMAGE_COOLDOWN.get(), new PostDamageCooldown(
                    Artifacts.CONFIG.items.panicNecklaceCooldown,
                    Optional.empty()
            ))
    );
    public static final Holder<Item> SHOCK_PENDANT = register("shock_pendant", builder -> builder
            .equipable()
            .component(ModDataComponents.RETALIATION_EFFECTS.get(), new RetaliationEffects(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(new LightningEffect(
                            Artifacts.CONFIG.items.shockPendantStrikeChance,
                            Artifacts.CONFIG.items.shockPendantCooldown
                    ))
            ))
            .component(ModDataComponents.DAMAGE_IMMUNITY.get(),
                    new DamageImmunity(
                            Artifacts.CONFIG.items.shockPendantCancelLightningDamage,
                            DamageTypeTags.IS_LIGHTNING,
                            EntityCondition.ALWAYS
                    )
            )
    );
    public static final Holder<Item> FLAME_PENDANT = register("flame_pendant", builder -> builder
            .equipable()
            .component(ModDataComponents.RETALIATION_EFFECTS.get(), new RetaliationEffects(
                    Optional.empty(),
                    Optional.of(new FireEffect(
                            Artifacts.CONFIG.items.flamePendantStrikeChance,
                            Artifacts.CONFIG.items.flamePendantCooldown,
                            Artifacts.CONFIG.items.flamePendantFireDuration,
                            Artifacts.CONFIG.items.flamePendantGrantFireResistance
                    )),
                    Optional.empty()
            ))
    );
    public static final Holder<Item> THORN_PENDANT = register("thorn_pendant", builder -> builder
            .equipable()
            .component(ModDataComponents.RETALIATION_EFFECTS.get(), new RetaliationEffects(
                    Optional.of(new ThornsEffect(
                            Artifacts.CONFIG.items.thornPendantStrikeChance,
                            Artifacts.CONFIG.items.thornPendantCooldown,
                            Artifacts.CONFIG.items.thornPendantMinDamage,
                            Artifacts.CONFIG.items.thornPendantMaxDamage
                    )),
                    Optional.empty(),
                    Optional.empty()
            ))
    );
    public static final Holder<Item> CHARM_OF_SINKING = register("charm_of_sinking", builder -> builder
            .equipable()
            .component(ModDataComponents.SINKING.get(), Artifacts.CONFIG.items.charmOfSinkingEnabled)
            .component(ModDataComponents.DAMAGE_IMMUNITY.get(), new DamageImmunity(
                    Artifacts.CONFIG.items.charmOfSinkingEnabled,
                    DamageTypeTags.IS_FALL,
                    Artifacts.CONFIG.items.charmOfSinkingUnderwaterFallDamage.get() ? EntityCondition.NEVER : EntityCondition.IN_WATER
            ))
            .addAttributeModifier(Attributes.OXYGEN_BONUS, Artifacts.CONFIG.items.charmOfSinkingEnabled.get() ?
                    Artifacts.CONFIG.items.charmOfSinkingOxygenBonus : Value.of(0D), AttributeModifier.Operation.ADD_VALUE
            )
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.CHARM_OF_SINKING)
    );
    public static final Holder<Item> CHARM_OF_SHRINKING = register("charm_of_shrinking", builder -> builder
            .equipable()
            .addAttributeModifier(Attributes.SCALE, Artifacts.CONFIG.items.charmOfShrinkingScaleModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.CHARM_OF_SHRINKING)
    );

    // belt
    public static final Holder<Item> CLOUD_IN_A_BOTTLE = register("cloud_in_a_bottle", builder -> builder
            .equipable(SoundEvents.BOTTLE_FILL_DRAGONBREATH)
            .component(ModDataComponents.DOUBLE_JUMP.get(), new DoubleJump(
                    Artifacts.CONFIG.items.cloudInABottleEnabled,
                    Artifacts.CONFIG.items.cloudInABottleFallDamageMultiplier,
                    Artifacts.CONFIG.items.cloudInABottleSprintJumpHorizontalVelocity,
                    Artifacts.CONFIG.items.cloudInABottleSprintJumpVerticalVelocity
            ))
            .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Artifacts.CONFIG.items.cloudInABottleSafeFallDistanceBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> OBSIDIAN_SKULL = register("obsidian_skull", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_IRON)
            .component(ModDataComponents.POST_DAMAGE_EFFECTS.get(), new CompositeAbility<>(List.of(new PostDamageEffect(
                    new MobEffectProvider(MobEffects.FIRE_RESISTANCE, Value.of(1), Artifacts.CONFIG.items.obsidianSkullFireResistanceDuration, Value.of(true), Value.of(true), EntityCondition.ALWAYS),
                    Optional.of(DamageTypeTags.IS_FIRE),
                    Value.of(1D)
            ))))
            .component(ModDataComponents.POST_DAMAGE_COOLDOWN.get(), new PostDamageCooldown(
                    Artifacts.CONFIG.items.obsidianSkullCooldown,
                    Optional.of(DamageTypeTags.IS_FIRE)
            ))
    );
    public static final Holder<Item> ANTIDOTE_VESSEL = register("antidote_vessel", builder -> builder
            .equipable(SoundEvents.BOTTLE_FILL)
            .component(ModDataComponents.PIGLIN_LOVED.get())
            .component(ModDataComponents.CURE_EFFECTS.get(), new CureEffects(
                    Artifacts.CONFIG.items.antidoteVesselEnabled,
                    Artifacts.CONFIG.items.antidoteVesselMaxEffectDuration
            ))
    );
    public static final Holder<Item> UNIVERSAL_ATTRACTOR = register("universal_attractor", builder -> builder
            .equipable()
            .component(ModDataComponents.PIGLIN_LOVED.get())
            .mobEffect(ModMobEffects.MAGNETISM, Artifacts.CONFIG.items.universalAttractorMagnetismLevel, Value.of(10), EntityCondition.ALWAYS)
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.UNIVERSAL_ATTRACTOR)
    );
    public static final Holder<Item> CRYSTAL_HEART = register("crystal_heart", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .addAttributeModifier(Attributes.MAX_HEALTH, Artifacts.CONFIG.items.crystalHeartHealthBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> HELIUM_FLAMINGO = register("helium_flamingo", builder -> builder
            .equipable(ModSoundEvents.POP)
            .component(ModDataComponents.SWIM_IN_AIR.get(), new SwimInAir(
                    Artifacts.CONFIG.items.heliumFlamingoFlightDuration,
                    Artifacts.CONFIG.items.heliumFlamingoRechargeDuration,
                    Artifacts.CONFIG.items.heliumFlamingoCooldown
            ))
    );
    public static final Holder<Item> CHORUS_TOTEM = register("chorus_totem", builder -> builder
            .equipable()
            .component(ModDataComponents.EQUIPABLE_TOTEM.get(), new EquipableTotem(
                    Artifacts.CONFIG.items.chorusTotemEnabled
            ))
            .component(
                    DataComponents.DEATH_PROTECTION,
                    Artifacts.CONFIG.items.chorusTotemEnabled,
                    new DeathProtection(List.of(
                            new ClearAllStatusEffectsConsumeEffect(),
                            new TeleportRandomlyConsumeEffect(32),
                            new HealConsumeEffect(Artifacts.CONFIG.items.chorusTotemHealthRestored))
                    )
            )
    );
    public static final Holder<Item> WARP_DRIVE = register("warp_drive", builder -> builder
            .equipable()
            .component(ModDataComponents.ENDER_PEARL_HUNGER_COST.get(), new EnderPearlHungerCost(
                    Artifacts.CONFIG.items.warpDriveEnabled,
                    Artifacts.CONFIG.items.warpDriveHungerCost,
                    Artifacts.CONFIG.items.warpDriveCooldown
            ))
            .component(ModDataComponents.ENDER_PEARL_DAMAGE_IMMUNITY.get(), Artifacts.CONFIG.items.warpDriveNullifyEnderPearlDamage)
    );

    // hands
    public static final Holder<Item> DIGGING_CLAWS = register("digging_claws", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_NETHERITE)
            .addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, Artifacts.CONFIG.items.diggingClawsBlockBreakSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .component(ModDataComponents.TOOL_TIER_UPGRADE.get(), new ToolTierUpgrade(Artifacts.CONFIG.items.diggingClawsToolTier))
    );
    public static final Holder<Item> FERAL_CLAWS = register("feral_claws", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_NETHERITE)
            .addAttributeModifier(Attributes.ATTACK_SPEED, Artifacts.CONFIG.items.feralClawsAttackSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> POWER_GLOVE = register("power_glove", builder -> builder
            .equipable()
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, Artifacts.CONFIG.items.powerGloveAttackDamageBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> FIRE_GAUNTLET = register("fire_gauntlet", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_IRON)
            .addAttributeModifier(ModAttributes.ATTACK_BURNING_DURATION, Artifacts.CONFIG.items.fireGauntletFireDuration, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> POCKET_PISTON = register("pocket_piston", builder -> builder
            .equipable(SoundEvents.PISTON_EXTEND)
            .addAttributeModifier(Attributes.ATTACK_KNOCKBACK, Artifacts.CONFIG.items.pocketPistonAttackKnockbackBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> VAMPIRIC_GLOVE = register("vampiric_glove", builder -> builder
            .equipable()
            .component(ModDataComponents.DAMAGE_ABSORPTION.get(), new DamageAbsorption(
                    Artifacts.CONFIG.items.vampiricGloveAbsorptionRatio,
                    Artifacts.CONFIG.items.vampiricGloveAbsorptionChance,
                    Artifacts.CONFIG.items.vampiricGloveMaxHealingPerHit
            ))
    );
    public static final Holder<Item> GOLDEN_HOOK = register("golden_hook", builder -> builder
            .equipable()
            .addAttributeModifier(ModAttributes.ENTITY_EXPERIENCE, Artifacts.CONFIG.items.goldenHookEntityExperienceBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .component(ModDataComponents.PIGLIN_LOVED.get())
    );
    public static final Holder<Item> ONION_RING = register("onion_ring", builder -> builder
            .equipable()
            .properties(properties -> properties.food(new FoodProperties.Builder().nutrition(2).build()))
            .component(ModDataComponents.POST_EATING_EFFECTS.get(), new CompositeAbility<>(
                    List.of(new PostEatingEffect(new MobEffectProvider(
                            MobEffects.HASTE,
                            Artifacts.CONFIG.items.onionRingHasteLevel,
                            Artifacts.CONFIG.items.onionRingHasteDurationPerFoodPoint,
                            Value.of(true), Value.of(true), EntityCondition.ALWAYS
                    )))
            ))
    );
    public static final Holder<Item> PICKAXE_HEATER = register("pickaxe_heater", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_IRON)
            .component(ModDataComponents.AUTO_SMELT.get(), Artifacts.CONFIG.items.pickaxeHeaterEnabled)
    );
    public static final Holder<Item> WITHERED_BRACELET = register("withered_bracelet", builder -> builder
            .equipable()
            .component(ModDataComponents.ATTACK_EFFECTS.get(), new CompositeAbility<>(List.of(
                    new AttackEffect(
                            new MobEffectProvider(
                                    MobEffects.WITHER,
                                    Artifacts.CONFIG.items.witheredBraceletWitherLevel,
                                    Artifacts.CONFIG.items.witheredBraceletWitherDuration,
                                    Value.of(true), Value.of(true), EntityCondition.ALWAYS
                            ),
                            Artifacts.CONFIG.items.witheredBraceletWitherChance,
                            Artifacts.CONFIG.items.witheredBraceletCooldown
                    )
            )))
    );

    // feet
    public static final Holder<Item> AQUA_DASHERS = register("aqua_dashers", builder -> builder
            .equipable()
            .component(ModDataComponents.FLUID_COLLISION.get(), new FluidCollision(Artifacts.CONFIG.items.aquaDashersEnabled,
                    Optional.empty(), EntityCondition.SPRINTING)
            )
    );
    public static final Holder<Item> BUNNY_HOPPERS = register("bunny_hoppers", builder -> builder
            .equipable()
            .addAttributeModifier(Attributes.JUMP_STRENGTH, Artifacts.CONFIG.items.bunnyHoppersJumpStrengthBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, Artifacts.CONFIG.items.bunnyHoppersFallDamageMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Artifacts.CONFIG.items.bunnyHoppersSafeFallDistanceBonus, AttributeModifier.Operation.ADD_VALUE)
            .component(ModDataComponents.HURT_SOUND.get(), new HurtSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.RABBIT_HURT), Artifacts.CONFIG.items.bunnyHoppersModifyHurtSounds))
    );
    public static final Holder<Item> KITTY_SLIPPERS = register("kitty_slippers", builder -> builder
            .equipable(SoundEvents.CAT_AMBIENT)
            .component(ModDataComponents.CREEPER_REPELLENT.get(), Artifacts.CONFIG.items.kittySlippersRepelCreepers)
            .component(ModDataComponents.PHANTOM_REPELLENT.get(), Artifacts.CONFIG.items.kittySlippersRepelPhantoms)
            .component(ModDataComponents.HURT_SOUND.get(), new HurtSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.CAT_HURT), Artifacts.CONFIG.items.kittySlippersModifyHurtSounds))
    );
    public static final Holder<Item> RUNNING_SHOES = register("running_shoes", builder -> builder
            .equipable()
            .addAttributeModifier(ModAttributes.SPRINTING_SPEED, Artifacts.CONFIG.items.runningShoesSprintingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(ModAttributes.SPRINTING_STEP_HEIGHT, Artifacts.CONFIG.items.runningShoesSprintingStepHeightBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> SNOWSHOES = register("snowshoes", builder -> builder
            .equipable()
            .component(ModDataComponents.WALK_ON_POWDER_SNOW.get(), Artifacts.CONFIG.items.snowshoesAllowWalkingOnPowderedSnow)
            .addAttributeModifier(ModAttributes.MOVEMENT_SPEED_ON_SNOW, Artifacts.CONFIG.items.snowshoesMovementSpeedOnSnowBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> STEADFAST_SPIKES = register("steadfast_spikes", builder -> builder
            .equipable()
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, Artifacts.CONFIG.items.steadfastSpikesKnockbackResistance, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(ModAttributes.SLIP_RESISTANCE, Artifacts.CONFIG.items.steadfastSpikesSlipperinessReduction, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> FLIPPERS = register("flippers", builder -> builder
            .equipable()
            .addAttributeModifier(ModAttributes.SWIM_SPEED, Artifacts.CONFIG.items.flippersSwimSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> ROOTED_BOOTS = register("rooted_boots", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .component(ModDataComponents.REPLENISH_HUNGER_ON_GRASS.get(), new ReplenishHungerOnGrass(
                    Artifacts.CONFIG.items.rootedBootsEnabled,
                    Artifacts.CONFIG.items.rootedBootsHungerReplenishingDuration
            ))
            .component(ModDataComponents.POST_EATING_PLANT_GROWTH.get(), Artifacts.CONFIG.items.rootedBootsGrowPlantsAfterEating)
    );
    public static final Holder<Item> STRIDER_SHOES = register("strider_shoes", builder -> builder
            .equipable(SoundEvents.ARMOR_EQUIP_LEATHER)
            .component(ModDataComponents.FLUID_COLLISION.get(), new FluidCollision(Artifacts.CONFIG.items.striderShoesEnabled,
                    Optional.of(FluidTags.LAVA), EntityCondition.SNEAKING)
            ).component(ModDataComponents.DAMAGE_IMMUNITY.get(),
                    new DamageImmunity(Artifacts.CONFIG.items.striderShoesCancelHotFloorDamage, ModTags.IS_HOT_FLOOR, EntityCondition.ALWAYS)
            )
    );

    // curio
    public static final Holder<Item> WHOOPEE_CUSHION = register("whoopee_cushion", builder -> builder
            .equipable(ModSoundEvents.FART)
            .addAttributeModifier(ModAttributes.FLATULENCE, Artifacts.CONFIG.items.whoopeeCushionFartChance, AttributeModifier.Operation.ADD_VALUE)
    );

    private static Holder<Item> register(String name, Consumer<ArtifactProperties> consumer) {
        return register(name, Item::new, () -> {
            ArtifactProperties builder = new ArtifactProperties(name);
            consumer.accept(builder);
            return builder.build();
        });
    }

    private static Holder<Item> register(String name, Function<Item.Properties, ? extends Item> factory, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> factory.apply(properties.get().setId(Artifacts.key(Registries.ITEM, name))));
    }
}
