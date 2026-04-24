package artifacts.registry;

import artifacts.component.HurtSound;
import artifacts.component.ToggleIdentifier;
import artifacts.component.ability.*;
import artifacts.component.ability.mobeffect.AttackEffects;
import artifacts.component.ability.mobeffect.EquipmentMobEffects;
import artifacts.component.ability.mobeffect.PostDamageEffects;
import artifacts.component.ability.mobeffect.PostEatingEffects;
import artifacts.component.ability.retaliation.RetaliationEffects;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Unit;
import net.minecraft.world.item.component.ItemLore;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final Register<DataComponentType<?>> DATA_COMPONENT_TYPES = Register.create(Registries.DATA_COMPONENT_TYPE);

    public static final Set<Supplier<? extends DataComponentType<? extends TickingAbility>>> TICKING_COMPONENTS = new LinkedHashSet<>();
    public static final List<Supplier<? extends DataComponentType<? extends EquipmentAbility>>> TOOLTIP_ORDER = new ArrayList<>();
    public static final Set<Supplier<? extends DataComponentType<?>>> APPLIES_COOLDOWN = new LinkedHashSet<>();

    // TODO (>1.21.1) remove
    @SuppressWarnings("unused")
    public static final Supplier<DataComponentType<Unit>> ABILITIES = DATA_COMPONENT_TYPES.register("abilities", () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).build());

    public static final Supplier<DataComponentType<ToggleIdentifier>> TOGGLE_KEY = registerSynced("toggle_key", ToggleIdentifier.CODEC, ToggleIdentifier.STREAM_CODEC);
    public static final Supplier<DataComponentType<Unit>> DISABLED_BY_TOGGLE = registerSynced("disabled_by_toggle", Codec.unit(Unit.INSTANCE), StreamCodec.unit(Unit.INSTANCE));
    public static final Supplier<DataComponentType<SoundEvent>> EQUIP_SOUND = registerSynced("equip_sound",
            ResourceLocation.CODEC.xmap(SoundEvent::createVariableRangeEvent, SoundEvent::getLocation),
            ResourceLocation.STREAM_CODEC.map(SoundEvent::createVariableRangeEvent, SoundEvent::getLocation)
    );
    public static final Supplier<DataComponentType<ItemLore>> ABILITY_LORE = registerCached("ability_lore", ItemLore.CODEC, ItemLore.STREAM_CODEC);
    public static final Supplier<DataComponentType<Unit>> PIGLIN_LOVED = registerSynced("piglin_loved", Unit.CODEC, StreamCodec.unit(Unit.INSTANCE));
    public static final Supplier<DataComponentType<HurtSound>> HURT_SOUND = registerSynced("hurt_sound", HurtSound.CODEC, HurtSound.STREAM_CODEC);
    public static final Supplier<DataComponentType<Value<Double>>> REDUCED_NIGHT_VISION = registerSynced("reduced_night_vision", ValueTypes.FRACTION.codec(), ValueTypes.FRACTION.streamCodec());
    public static final Supplier<DataComponentType<Value<Boolean>>> HIDE_WHEN_INVISIBLE = registerSynced("hide_when_invisible", ValueTypes.enabledField().codec(), ValueTypes.BOOLEAN.streamCodec());

    // abilities
    public static final Supplier<DataComponentType<PostDamageCooldown>> POST_DAMAGE_COOLDOWN =
            registerSynced("post_damage_cooldown", PostDamageCooldown.CODEC, PostDamageCooldown.STREAM_CODEC);
    public static final Supplier<DataComponentType<PostDamageEffects>> POST_DAMAGE_EFFECTS =
            registerSynced("post_damage_effects", PostDamageEffects.CODEC, PostDamageEffects.STREAM_CODEC);
    public static final Supplier<DataComponentType<PostEatingEffects>> POST_EATING_EFFECTS =
            registerSynced("post_eating_effects", PostEatingEffects.CODEC, PostEatingEffects.STREAM_CODEC);
    public static final Supplier<DataComponentType<DamageAbsorption>> DAMAGE_ABSORPTION =
            registerSynced("damage_absorption", DamageAbsorption.CODEC, DamageAbsorption.STREAM_CODEC);
    public static final Supplier<DataComponentType<AttackEffects>> ATTACK_EFFECTS =
            registerSynced("attack_effects", AttackEffects.CODEC, AttackEffects.STREAM_CODEC);
    public static final Supplier<DataComponentType<AttributeModifiers>> ATTRIBUTE_MODIFIERS =
             registerCached("attribute_modifiers", AttributeModifiers.CODEC, AttributeModifiers.STREAM_CODEC);
    public static final Supplier<DataComponentType<DamageImmunity>> DAMAGE_IMMUNITY =
            registerSynced("damage_immunity", DamageImmunity.CODEC, DamageImmunity.STREAM_CODEC);
    public static final Supplier<DataComponentType<DoubleJump>> DOUBLE_JUMP =
            registerSynced("double_jump", DoubleJump.CODEC, DoubleJump.STREAM_CODEC);
    public static final Supplier<DataComponentType<EnderPearlHungerCost>> ENDER_PEARL_HUNGER_COST =
            registerSynced("ender_pearl_hunger_cost", EnderPearlHungerCost.CODEC, EnderPearlHungerCost.STREAM_CODEC);
    public static final Supplier<DataComponentType<SimpleAbility>> POST_EATING_PLANT_GROWTH =
            registerSimpleAbility("post_eating_plant_growth");
    public static final Supplier<DataComponentType<EnchantmentLevelModifiers>> ENCHANTMENT_LEVEL_MODIFIERS =
            registerSynced("enchantment_level_modifiers", EnchantmentLevelModifiers.CODEC, EnchantmentLevelModifiers.STREAM_CODEC);
    public static final Supplier<DataComponentType<EquipmentMobEffects>> MOB_EFFECTS =
            registerSynced("mob_effects", EquipmentMobEffects.CODEC, EquipmentMobEffects.STREAM_CODEC);
    public static final Supplier<DataComponentType<SimpleAbility>> ENDER_PEARL_DAMAGE_IMMUNITY =
            registerSimpleAbility("ender_pearl_damage_immunity");
    public static final Supplier<DataComponentType<CureEffects>> CURE_EFFECTS =
            registerSynced("cure_effects", CureEffects.CODEC, CureEffects.STREAM_CODEC);
    public static final Supplier<DataComponentType<ReplenishHungerOnGrass>> REPLENISH_HUNGER_ON_GRASS =
            registerSynced("replenish_hunger_on_grass", ReplenishHungerOnGrass.CODEC, ReplenishHungerOnGrass.STREAM_CODEC);
    public static final Supplier<DataComponentType<SimpleAbility>> CREEPER_REPELLENT =
            registerSimpleAbility("creeper_repellent");
    public static final Supplier<DataComponentType<SimpleAbility>> PHANTOM_REPELLENT =
            registerSimpleAbility("phantom_repellent");
    public static final Supplier<DataComponentType<SimpleAbility>> SINKING =
            registerSimpleAbility("sinking");
    public static final Supplier<DataComponentType<SimpleAbility>> AUTO_SMELT =
            registerSimpleAbility("auto_smelt");
    public static final Supplier<DataComponentType<FluidCollision>> FLUID_COLLISION =
            registerSynced("fluid_collision", FluidCollision.CODEC, FluidCollision.STREAM_CODEC);
    public static final Supplier<DataComponentType<SwimInAir>> SWIM_IN_AIR =
            registerSynced("swim_in_air", SwimInAir.CODEC, SwimInAir.STREAM_CODEC);
    // TODO (>1.21.1) use vanilla death_protection component
    public static final Supplier<DataComponentType<DeathProtectionTeleport>> DEATH_PROTECTION_TELEPORT =
            registerSynced("death_protection_teleport", DeathProtectionTeleport.CODEC, DeathProtectionTeleport.STREAM_CODEC);
    public static final Supplier<DataComponentType<RetaliationEffects>> RETALIATION_EFFECTS =
            registerSynced("retaliation_effects", RetaliationEffects.CODEC, RetaliationEffects.STREAM_CODEC);
    public static final Supplier<DataComponentType<ToolTierUpgrade>> TOOL_TIER_UPGRADE =
            registerSynced("tool_tier_upgrade", ToolTierUpgrade.CODEC, ToolTierUpgrade.STREAM_CODEC);
    public static final Supplier<DataComponentType<SimpleAbility>> WALK_ON_POWDER_SNOW =
            registerSimpleAbility("walk_on_powder_snow");

    static {
        TICKING_COMPONENTS.addAll(List.of(
                ATTRIBUTE_MODIFIERS,
                REPLENISH_HUNGER_ON_GRASS,
                CURE_EFFECTS,
                MOB_EFFECTS,
                FLUID_COLLISION
        ));
        TOOLTIP_ORDER.addAll(List.of(
                TOOL_TIER_UPGRADE,
                DOUBLE_JUMP,
                MOB_EFFECTS,
                ENCHANTMENT_LEVEL_MODIFIERS,
                ATTRIBUTE_MODIFIERS,
                POST_DAMAGE_EFFECTS,
                POST_EATING_EFFECTS,
                ATTACK_EFFECTS,
                RETALIATION_EFFECTS,
                CURE_EFFECTS,
                DAMAGE_ABSORPTION,
                DEATH_PROTECTION_TELEPORT,
                ENDER_PEARL_HUNGER_COST,
                ENDER_PEARL_DAMAGE_IMMUNITY,
                REPLENISH_HUNGER_ON_GRASS,
                CREEPER_REPELLENT,
                PHANTOM_REPELLENT,
                SINKING,
                AUTO_SMELT,
                FLUID_COLLISION,
                SWIM_IN_AIR,
                WALK_ON_POWDER_SNOW,
                DAMAGE_IMMUNITY,
                POST_EATING_PLANT_GROWTH
        ));
        APPLIES_COOLDOWN.addAll(Set.of(
                POST_DAMAGE_COOLDOWN,
                RETALIATION_EFFECTS,
                DEATH_PROTECTION_TELEPORT,
                SWIM_IN_AIR,
                ATTACK_EFFECTS
        ));
    }

    private static Supplier<DataComponentType<SimpleAbility>> registerSimpleAbility(String name) {
        return registerSynced(name, SimpleAbility.CODEC, SimpleAbility.STREAM_CODEC);
    }

    private static <T> Supplier<DataComponentType<T>> registerSynced(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return register(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }

    private static <T> Supplier<DataComponentType<T>> registerCached(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return register(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec).cacheEncoding());
    }

    private static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}
