package artifacts.registry;

import artifacts.Artifacts;
import artifacts.component.HurtSound;
import artifacts.component.ToggleIdentifier;
import artifacts.component.ability.*;
import artifacts.component.ability.mobeffect.AttackEffects;
import artifacts.component.ability.mobeffect.MobEffectProvider;
import artifacts.component.ability.mobeffect.PostDamageEffects;
import artifacts.component.ability.mobeffect.PostEatingEffects;
import artifacts.component.ability.retaliation.FireEffect;
import artifacts.component.ability.retaliation.LightningEffect;
import artifacts.component.ability.retaliation.RetaliationEffects;
import artifacts.component.ability.retaliation.ThornsEffect;
import artifacts.config.value.Value;
import artifacts.item.EverlastingFoodItem;
import artifacts.item.UmbrellaItem;
import artifacts.item.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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

    public static final Holder<Item> MIMIC_SPAWN_EGG = register("mimic_spawn_egg", () -> PlatformServices.getPlatformHelper().createMimicSpawnEgg(new Item.Properties()));
    public static final Holder<Item> UMBRELLA = register("umbrella", UmbrellaItem::new);
    public static final Holder<Item> EVERLASTING_BEEF = register("everlasting_beef", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build(), Artifacts.CONFIG.items.everlastingBeef.cooldown, Artifacts.CONFIG.items.everlastingBeef.enabled));
    public static final Holder<Item> ETERNAL_STEAK = register("eternal_steak", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build(), Artifacts.CONFIG.items.eternalSteak.cooldown, Artifacts.CONFIG.items.eternalSteak.enabled));

    // head
    public static final Holder<Item> PLASTIC_DRINKING_HAT = wearableItem("plastic_drinking_hat", builder -> builder
            .equipSound(SoundEvents.BOTTLE_FILL)
            .addAttributeModifier(ModAttributes.DRINKING_SPEED, Artifacts.CONFIG.items.plasticDrinkingHat.drinkingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(ModAttributes.EATING_SPEED, Artifacts.CONFIG.items.plasticDrinkingHat.eatingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> NOVELTY_DRINKING_HAT = wearableItem("novelty_drinking_hat", builder -> builder
            .equipSound(SoundEvents.BOTTLE_FILL)
            .component(ModDataComponents.ABILITY_LORE.get(),
                    new ItemLore(List.of(Component.translatable("artifacts.tooltip.item.novelty_drinking_hat").withStyle(ChatFormatting.GRAY)))
            )
            .addAttributeModifier(ModAttributes.DRINKING_SPEED, Artifacts.CONFIG.items.noveltyDrinkingHat.drinkingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(ModAttributes.EATING_SPEED, Artifacts.CONFIG.items.noveltyDrinkingHat.eatingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> SNORKEL = wearableItem("snorkel", builder -> builder
            .mobEffect(MobEffects.WATER_BREATHING, Value.of(1), Artifacts.CONFIG.items.snorkel.waterBreathingDuration,
                    Artifacts.CONFIG.items.snorkel.isInfinite.get() ? EntityCondition.ALWAYS : EntityCondition.ABOVE_WATER
            )
    );
    public static final Holder<Item> NIGHT_VISION_GOGGLES = wearableItem("night_vision_goggles", builder -> builder
            .mobEffect(MobEffects.NIGHT_VISION, Value.of(1), Value.of(10), EntityCondition.ALWAYS)
            .component(ModDataComponents.REDUCED_NIGHT_VISION.get(), Artifacts.CONFIG.items.nightVisionGoggles.strength)
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.NIGHT_VISION_GOGGLES)
    );
    public static final Holder<Item> VILLAGER_HAT = wearableItem("villager_hat", builder -> builder
            .addAttributeModifier(ModAttributes.VILLAGER_REPUTATION, Artifacts.CONFIG.items.villagerHat.reputationBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> SUPERSTITIOUS_HAT = wearableItem("superstitious_hat", builder -> builder
            .increasesEnchantment(Enchantments.LOOTING, Artifacts.CONFIG.items.superstitiousHat.lootingLevelBonus)
    );
    public static final Holder<Item> COWBOY_HAT = wearableItem("cowboy_hat", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
            .addAttributeModifier(ModAttributes.MOUNT_SPEED, Artifacts.CONFIG.items.cowboyHat.mountSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> ANGLERS_HAT = wearableItem("anglers_hat", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
            .increasesEnchantment(Enchantments.LUCK_OF_THE_SEA, Artifacts.CONFIG.items.anglersHat.luckOfTheSeaLevelBonus)
            .increasesEnchantment(Enchantments.LURE, Artifacts.CONFIG.items.anglersHat.lureLevelBonus)
    );

    // necklace
    public static final Holder<Item> LUCKY_SCARF = wearableItem("lucky_scarf", builder -> builder
            .increasesEnchantment(Enchantments.FORTUNE, Artifacts.CONFIG.items.luckyScarf.fortuneLevelBonus)
    );
    public static final Holder<Item> SCARF_OF_INVISIBILITY = wearableItem("scarf_of_invisibility", builder -> builder
            .mobEffect(MobEffects.INVISIBILITY, Value.of(1), Value.of(10),
                    Artifacts.CONFIG.items.scarfOfInvisibility.enabled.get() ? EntityCondition.ALWAYS : EntityCondition.NEVER
            )
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.SCARF_OF_INVISIBILITY)
            .component(ModDataComponents.HIDE_WHEN_INVISIBLE.get(), Artifacts.CONFIG.items.scarfOfInvisibility.hideWhenInvisible)
    );
    public static final Holder<Item> CROSS_NECKLACE = wearableItem("cross_necklace", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .component(ModDataComponents.PIGLIN_LOVED.get())
            .component(ModDataComponents.POST_DAMAGE_COOLDOWN.get(), new PostDamageCooldown(Artifacts.CONFIG.items.crossNecklace.cooldown, Optional.empty()))
            .addAttributeModifier(ModAttributes.INVINCIBILITY_TICKS, Artifacts.CONFIG.items.crossNecklace.bonusInvincibilityTicks, AttributeModifier.Operation.ADD_VALUE, false)
    );
    public static final Holder<Item> PANIC_NECKLACE = wearableItem("panic_necklace", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .component(ModDataComponents.POST_DAMAGE_EFFECTS.get(), new PostDamageEffects(List.of(new PostDamageEffects.Entry(
                    new MobEffectProvider(MobEffects.MOVEMENT_SPEED, Artifacts.CONFIG.items.panicNecklace.speedLevel, Artifacts.CONFIG.items.panicNecklace.speedDuration, Value.of(true), Value.of(true), EntityCondition.ALWAYS),
                    Optional.empty(),
                    Value.of(1D)
            ))))
            .component(ModDataComponents.POST_DAMAGE_COOLDOWN.get(), new PostDamageCooldown(
                    Artifacts.CONFIG.items.panicNecklace.cooldown,
                    Optional.empty()
            ))
    );
    public static final Holder<Item> SHOCK_PENDANT = wearableItem("shock_pendant", builder -> builder
            .component(ModDataComponents.RETALIATION_EFFECTS.get(), new RetaliationEffects(
                    Optional.empty(), Optional.empty(),
                    Optional.of(new LightningEffect(
                            Artifacts.CONFIG.items.shockPendant.strikeChance,
                            Artifacts.CONFIG.items.shockPendant.cooldown
                    ))
            )).component(ModDataComponents.DAMAGE_IMMUNITY.get(),
                    new DamageImmunity(Artifacts.CONFIG.items.shockPendant.cancelLightningDamage,
                            DamageTypeTags.IS_LIGHTNING, EntityCondition.ALWAYS)
            )
    );
    public static final Holder<Item> FLAME_PENDANT = wearableItem("flame_pendant", builder -> builder
            .component(ModDataComponents.RETALIATION_EFFECTS.get(), new RetaliationEffects(
                    Optional.empty(),
                    Optional.of(new FireEffect(
                            Artifacts.CONFIG.items.flamePendant.strikeChance,
                            Artifacts.CONFIG.items.flamePendant.cooldown,
                            Artifacts.CONFIG.items.flamePendant.fireDuration,
                            Artifacts.CONFIG.items.flamePendant.grantFireResistance
                    )),
                    Optional.empty()
            ))
    );
    public static final Holder<Item> THORN_PENDANT = wearableItem("thorn_pendant", builder -> builder
            .component(ModDataComponents.RETALIATION_EFFECTS.get(), new RetaliationEffects(Optional.of(new ThornsEffect(
                    Artifacts.CONFIG.items.thornPendant.strikeChance,
                    Artifacts.CONFIG.items.thornPendant.cooldown,
                    Artifacts.CONFIG.items.thornPendant.minDamage,
                    Artifacts.CONFIG.items.thornPendant.maxDamage
            )), Optional.empty(), Optional.empty()))
    );
    public static final Holder<Item> CHARM_OF_SINKING = wearableItem("charm_of_sinking", builder -> builder
            .component(ModDataComponents.SINKING.get(), Artifacts.CONFIG.items.charmOfSinking.enabled)
            .component(ModDataComponents.DAMAGE_IMMUNITY.get(), new DamageImmunity(
                    Artifacts.CONFIG.items.charmOfSinking.enabled,
                    DamageTypeTags.IS_FALL,
                    Artifacts.CONFIG.items.charmOfSinking.underwaterFallDamage.get() ? EntityCondition.NEVER : EntityCondition.IN_WATER
            ))
            .addAttributeModifier(Attributes.OXYGEN_BONUS, Artifacts.CONFIG.items.charmOfSinking.enabled.get() ?
                    Artifacts.CONFIG.items.charmOfSinking.oxygenBonus : Value.of(0D), AttributeModifier.Operation.ADD_VALUE
            )
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.CHARM_OF_SINKING)
    );
    public static final Holder<Item> CHARM_OF_SHRINKING = wearableItem("charm_of_shrinking", builder -> builder
            .addAttributeModifier(Attributes.SCALE, Artifacts.CONFIG.items.charmOfShrinking.scaleModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.CHARM_OF_SHRINKING)
    );

    // belt
    public static final Holder<Item> CLOUD_IN_A_BOTTLE = wearableItem("cloud_in_a_bottle", builder -> builder
            .equipSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH)
            .component(ModDataComponents.DOUBLE_JUMP.get(), new DoubleJump(
                    Artifacts.CONFIG.items.cloudInABottle.enabled,
                    Artifacts.CONFIG.items.cloudInABottle.fallDamageMultiplier,
                    Artifacts.CONFIG.items.cloudInABottle.sprintJumpHorizontalVelocity,
                    Artifacts.CONFIG.items.cloudInABottle.sprintJumpVerticalVelocity
            ))
            .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Artifacts.CONFIG.items.cloudInABottle.safeFallDistanceBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> OBSIDIAN_SKULL = wearableItem("obsidian_skull", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON)
            .component(ModDataComponents.POST_DAMAGE_EFFECTS.get(), new PostDamageEffects(List.of(new PostDamageEffects.Entry(
                    new MobEffectProvider(MobEffects.FIRE_RESISTANCE, Value.of(1), Artifacts.CONFIG.items.obsidianSkull.fireResistanceDuration, Value.of(true), Value.of(true), EntityCondition.ALWAYS),
                    Optional.of(DamageTypeTags.IS_FIRE),
                    Value.of(1D)
            ))))
            .component(ModDataComponents.POST_DAMAGE_COOLDOWN.get(), new PostDamageCooldown(
                    Artifacts.CONFIG.items.obsidianSkull.cooldown,
                    Optional.of(DamageTypeTags.IS_FIRE)
            ))
    );
    public static final Holder<Item> ANTIDOTE_VESSEL = wearableItem("antidote_vessel", builder -> builder
            .equipSound(SoundEvents.BOTTLE_FILL)
            .component(ModDataComponents.PIGLIN_LOVED.get())
            .component(ModDataComponents.CURE_EFFECTS.get(), new CureEffects(
                    Artifacts.CONFIG.items.antidoteVessel.enabled,
                    Artifacts.CONFIG.items.antidoteVessel.maxEffectDuration
            ))
    );
    public static final Holder<Item> UNIVERSAL_ATTRACTOR = wearableItem("universal_attractor", builder -> builder
            .component(ModDataComponents.PIGLIN_LOVED.get())
            .mobEffect(ModMobEffects.MAGNETISM, Artifacts.CONFIG.items.universalAttractor.magnetismLevel, Value.of(10), EntityCondition.ALWAYS)
            .component(ModDataComponents.TOGGLE_KEY.get(), ToggleIdentifier.UNIVERSAL_ATTRACTOR)
    );
    public static final Holder<Item> CRYSTAL_HEART = wearableItem("crystal_heart", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .addAttributeModifier(Attributes.MAX_HEALTH, Artifacts.CONFIG.items.crystalHeart.healthBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> HELIUM_FLAMINGO = wearableItem("helium_flamingo", builder -> builder
            .equipSound(ModSoundEvents.POP)
            .component(ModDataComponents.SWIM_IN_AIR.get(), new SwimInAir(
                    Artifacts.CONFIG.items.heliumFlamingo.flightDuration,
                    Artifacts.CONFIG.items.heliumFlamingo.rechargeDuration,
                    Artifacts.CONFIG.items.heliumFlamingo.cooldown
            ))
    );
    public static final Holder<Item> CHORUS_TOTEM = wearableItem("chorus_totem", builder -> builder
            .component(ModDataComponents.DEATH_PROTECTION_TELEPORT.get(), new DeathProtectionTeleport(
                    Artifacts.CONFIG.items.chorusTotem.teleportationChance,
                    Artifacts.CONFIG.items.chorusTotem.healthRestored,
                    Artifacts.CONFIG.items.chorusTotem.cooldown,
                    Artifacts.CONFIG.items.chorusTotem.consumeOnUse
            ))
    );
    public static final Holder<Item> WARP_DRIVE = wearableItem("warp_drive", builder -> builder
            .component(ModDataComponents.ENDER_PEARL_HUNGER_COST.get(), new EnderPearlHungerCost(
                    Artifacts.CONFIG.items.warpDrive.enabled,
                    Artifacts.CONFIG.items.warpDrive.hungerCost,
                    Artifacts.CONFIG.items.warpDrive.cooldown
            ))
            .component(ModDataComponents.ENDER_PEARL_DAMAGE_IMMUNITY.get(), Artifacts.CONFIG.items.warpDrive.nullifyEnderPearlDamage)
    );

    // hands
    public static final Holder<Item> DIGGING_CLAWS = wearableItem("digging_claws", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
            .addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, Artifacts.CONFIG.items.diggingClaws.blockBreakSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .component(ModDataComponents.TOOL_TIER_UPGRADE.get(), new ToolTierUpgrade(Artifacts.CONFIG.items.diggingClaws.toolTier))
    );
    public static final Holder<Item> FERAL_CLAWS = wearableItem("feral_claws", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
            .addAttributeModifier(Attributes.ATTACK_SPEED, Artifacts.CONFIG.items.feralClaws.attackSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> POWER_GLOVE = wearableItem("power_glove", builder -> builder
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, Artifacts.CONFIG.items.powerGlove.attackDamageBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> FIRE_GAUNTLET = wearableItem("fire_gauntlet", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON)
            .addAttributeModifier(ModAttributes.ATTACK_BURNING_DURATION, Artifacts.CONFIG.items.fireGauntlet.fireDuration, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> POCKET_PISTON = wearableItem("pocket_piston", builder -> builder
            .equipSound(SoundEvents.PISTON_EXTEND)
            .addAttributeModifier(Attributes.ATTACK_KNOCKBACK, Artifacts.CONFIG.items.pocketPiston.attackKnockbackBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> VAMPIRIC_GLOVE = wearableItem("vampiric_glove", builder -> builder
            .component(ModDataComponents.DAMAGE_ABSORPTION.get(), new DamageAbsorption(
                    Artifacts.CONFIG.items.vampiricGlove.absorptionRatio,
                    Artifacts.CONFIG.items.vampiricGlove.absorptionChance,
                    Artifacts.CONFIG.items.vampiricGlove.maxHealingPerHit
            ))
    );
    public static final Holder<Item> GOLDEN_HOOK = wearableItem("golden_hook", builder -> builder
            .addAttributeModifier(ModAttributes.ENTITY_EXPERIENCE, Artifacts.CONFIG.items.goldenHook.entityExperienceBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .component(ModDataComponents.PIGLIN_LOVED.get())
    );
    public static final Holder<Item> ONION_RING = wearableItem("onion_ring", builder -> builder
            .properties(properties -> properties.food(new FoodProperties.Builder().nutrition(2).build()))
            .component(ModDataComponents.POST_EATING_EFFECTS.get(), new PostEatingEffects(
                    List.of(new PostEatingEffects.Entry(new MobEffectProvider(
                            MobEffects.DIG_SPEED,
                            Artifacts.CONFIG.items.onionRing.hasteLevel,
                            Artifacts.CONFIG.items.onionRing.hasteDurationPerFoodPoint,
                            Value.of(true), Value.of(true), EntityCondition.ALWAYS
                    )))
            ))
    );
    public static final Holder<Item> PICKAXE_HEATER = wearableItem("pickaxe_heater", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON)
            .component(ModDataComponents.AUTO_SMELT.get(), Artifacts.CONFIG.items.pickaxeHeater.enabled)
    );
    public static final Holder<Item> WITHERED_BRACELET = wearableItem("withered_bracelet", builder -> builder
            .component(ModDataComponents.ATTACK_EFFECTS.get(), new AttackEffects(List.of(
                    new AttackEffects.Entry(
                            new MobEffectProvider(
                                    MobEffects.WITHER,
                                    Artifacts.CONFIG.items.witheredBracelet.witherLevel,
                                    Artifacts.CONFIG.items.witheredBracelet.witherDuration,
                                    Value.of(true), Value.of(true), EntityCondition.ALWAYS
                            ),
                            Artifacts.CONFIG.items.witheredBracelet.witherChance,
                            Artifacts.CONFIG.items.witheredBracelet.cooldown
                    )
            )))
    );

    // feet
    public static final Holder<Item> AQUA_DASHERS = wearableItem("aqua_dashers", builder -> builder
            .component(ModDataComponents.FLUID_COLLISION.get(), new FluidCollision(Artifacts.CONFIG.items.aquaDashers.enabled,
                    Optional.empty(), EntityCondition.SPRINTING)
            )
    );
    public static final Holder<Item> BUNNY_HOPPERS = wearableItem("bunny_hoppers", builder -> builder
            .addAttributeModifier(Attributes.JUMP_STRENGTH, Artifacts.CONFIG.items.bunnyHoppers.jumpStrengthBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, Artifacts.CONFIG.items.bunnyHoppers.fallDamageMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Artifacts.CONFIG.items.bunnyHoppers.safeFallDistanceBonus, AttributeModifier.Operation.ADD_VALUE)
            .component(ModDataComponents.HURT_SOUND.get(), new HurtSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.RABBIT_HURT), Artifacts.CONFIG.items.bunnyHoppers.modifyHurtSounds))
    );
    public static final Holder<Item> KITTY_SLIPPERS = wearableItem("kitty_slippers", builder -> builder
            .equipSound(SoundEvents.CAT_AMBIENT)
            .component(ModDataComponents.CREEPER_REPELLENT.get(), Artifacts.CONFIG.items.kittySlippers.repelCreepers)
            .component(ModDataComponents.PHANTOM_REPELLENT.get(), Artifacts.CONFIG.items.kittySlippers.repelPhantoms)
            .component(ModDataComponents.HURT_SOUND.get(), new HurtSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.CAT_HURT), Artifacts.CONFIG.items.kittySlippers.modifyHurtSounds))
    );
    public static final Holder<Item> RUNNING_SHOES = wearableItem("running_shoes", builder -> builder
            .addAttributeModifier(ModAttributes.SPRINTING_SPEED, Artifacts.CONFIG.items.runningShoes.sprintingSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(ModAttributes.SPRINTING_STEP_HEIGHT, Artifacts.CONFIG.items.runningShoes.sprintingStepHeightBonus, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> SNOWSHOES = wearableItem("snowshoes", builder -> builder
            .component(ModDataComponents.WALK_ON_POWDER_SNOW.get(), Artifacts.CONFIG.items.snowshoes.allowWalkingOnPowderedSnow)
            .addAttributeModifier(ModAttributes.MOVEMENT_SPEED_ON_SNOW, Artifacts.CONFIG.items.snowshoes.movementSpeedOnSnowBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> STEADFAST_SPIKES = wearableItem("steadfast_spikes", builder -> builder
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, Artifacts.CONFIG.items.steadfastSpikes.knockbackResistance, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(ModAttributes.SLIP_RESISTANCE, Artifacts.CONFIG.items.steadfastSpikes.slipperinessReduction, AttributeModifier.Operation.ADD_VALUE)
    );
    public static final Holder<Item> FLIPPERS = wearableItem("flippers", builder -> builder
            .addAttributeModifier(ModAttributes.SWIM_SPEED, Artifacts.CONFIG.items.flippers.swimSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final Holder<Item> ROOTED_BOOTS = wearableItem("rooted_boots", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
            .component(ModDataComponents.REPLENISH_HUNGER_ON_GRASS.get(), new ReplenishHungerOnGrass(
                    Artifacts.CONFIG.items.rootedBoots.enabled,
                    Artifacts.CONFIG.items.rootedBoots.hungerReplenishingDuration
            ))
            .component(ModDataComponents.POST_EATING_PLANT_GROWTH.get(), Artifacts.CONFIG.items.rootedBoots.growPlantsAfterEating)
    );
    public static final Holder<Item> STRIDER_SHOES = wearableItem("strider_shoes", builder -> builder
            .equipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
            .component(ModDataComponents.FLUID_COLLISION.get(), new FluidCollision(Artifacts.CONFIG.items.striderShoes.enabled,
                    Optional.of(FluidTags.LAVA), EntityCondition.SNEAKING)
            ).component(ModDataComponents.DAMAGE_IMMUNITY.get(),
                    new DamageImmunity(Artifacts.CONFIG.items.striderShoes.cancelHotFloorDamage, ModTags.IS_HOT_FLOOR, EntityCondition.ALWAYS)
            )
    );

    // curio
    public static final Holder<Item> WHOOPEE_CUSHION = wearableItem("whoopee_cushion", builder -> builder
            .equipSound(ModSoundEvents.FART)
            .addAttributeModifier(ModAttributes.FLATULENCE, Artifacts.CONFIG.items.whoopeeCushion.fartChance, AttributeModifier.Operation.ADD_VALUE)
    );

    private static Holder<Item> wearableItem(String name, Consumer<WearableArtifactItem.Builder> consumer) {
        return register(name, () -> {
            WearableArtifactItem.Builder builder = new WearableArtifactItem.Builder(name);
            consumer.accept(builder);
            return builder.build();
        });
    }

    private static Holder<Item> register(String name, Supplier<? extends Item> supplier) {
        return ITEMS.register(name, supplier);
    }
}
