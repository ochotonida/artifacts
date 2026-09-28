package artifacts.registry;

import artifacts.component.*;
import artifacts.component.ability.*;
import artifacts.component.ability.mobeffect.AttackEffect;
import artifacts.component.ability.mobeffect.EquipmentMobEffect;
import artifacts.component.ability.mobeffect.PostDamageEffect;
import artifacts.component.ability.mobeffect.PostEatingEffect;
import artifacts.component.ability.retaliation.RetaliationEffects;
import artifacts.component.itemdamage.*;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final Register<DataComponentType<?>> DATA_COMPONENT_TYPES = Register.create(Registries.DATA_COMPONENT_TYPE);

    public static final Set<TickingAbility<?, ?>> TICKING_ABILITIES = new LinkedHashSet<>();
    private static final Set<ComponentType<?, ?>> APPLIES_COOLDOWN = new LinkedHashSet<>();

    // Miscellaneous components
    /** Allows the items abilities to be toggled on and off */
    public static final ComponentType.Singleton<ToggleIdentifier> TOGGLE_KEY =
            registerSynced("toggle_key", ToggleIdentifier.CODEC, ToggleIdentifier.STREAM_CODEC);
    /** Marker used by the toggle_key component */
    public static final ComponentType.Singleton<Unit> DISABLED_BY_TOGGLE =
            registerSynced("disabled_by_toggle", Unit.CODEC, Unit.STREAM_CODEC);
    /** Adds the missing dependency tooltip when Curios or Trinkets isn't installed */
    public static final ComponentType.Singleton<Unit> DEPENDENCY_CHECK_TOOLTIP =
            registerSynced("dependency_check_tooltip", Unit.CODEC, Unit.STREAM_CODEC);
    /** Adds the *Cosmetic* tooltip when the item has no (enabled) abilities */
    public static final ComponentType.Singleton<Unit> COSMETIC_TOOLTIP =
            registerSynced("cosmetic_tooltip", Unit.CODEC, Unit.STREAM_CODEC);
    // TODO: probably should rename this to `equippable` to match vanilla
    /** Allows the item to be equipped from use */
    public static final ComponentType.Singleton<Equipable> EQUIPABLE =
            registerSynced("equipable", Equipable.CODEC, Equipable.STREAM_CODEC);
    /** Item lore tooltip that displays above other ability tooltips */
    public static final ComponentType.Singleton<ItemLore> ABILITY_LORE =
            registerCached("ability_lore", ItemLore.CODEC, ItemLore.STREAM_CODEC);
    // TODO: do I still need this?
    /** Pacifies piglins when the item is worn (a tag might be better for this) */
    public static final ComponentType.Singleton<Unit> PIGLIN_LOVED =
            registerSynced("piglin_loved", Unit.CODEC, StreamCodec.unit(Unit.INSTANCE));
    /** Sound that plays when hurt while wearing the item */
    public static final ComponentType.Singleton<HurtSound> HURT_SOUND =
            registerSynced("hurt_sound", HurtSound.CODEC, HurtSound.STREAM_CODEC);
    /** Decreases the brightness of the night vision effect while worn */
    public static final ComponentType.Singleton<Value<Double>> REDUCED_NIGHT_VISION =
            registerSynced("reduced_night_vision", ValueTypes.FRACTION.codec(), ValueTypes.FRACTION.streamCodec());
    /** Hides the item's model when the entity wearing it is invisible */
    public static final ComponentType.Singleton<Value<Boolean>> HIDE_WHEN_INVISIBLE =
            registerSynced("hide_when_invisible", ValueTypes.enabledField().codec(), ValueTypes.BOOLEAN.streamCodec());
    /** Prevents the item from being destroyed when durability reaches 0 */
    public static final ComponentType.Singleton<Value<Boolean>> INDESTRUCTIBLE =
            registerSynced("indestructible", ValueTypes.enabledField().codec(), ValueTypes.BOOLEAN.streamCodec());
    /** Stores components that have been disabled when the item was broken, such that they can be reinstated when the item is repaired */
    public static final ComponentType.Singleton<StoredComponents> BROKEN_COMPONENTS =
            registerSynced("broken_components", StoredComponents.CODEC, StoredComponents.STREAM_CODEC);
    /** Prevents status effects from spawning particles while worn */
    public static final ComponentType.Singleton<Value<Boolean>> HIDES_EFFECT_PARTICLES =
            registerSynced("hides_effect_particles", ValueTypes.enabledField().codec(), ValueTypes.BOOLEAN.streamCodec());

    // Equipment damage rules
    /** Damage applied to the item when hurt, while worn and not on cooldown */
    public static final ComponentType.Singleton<DamageOnHurt> DAMAGE_ON_HURT =
            registerSynced("damage_on_hurt", DamageOnHurt.CODEC, DamageOnHurt.STREAM_CODEC);
    /** Damage applied to the item when attacking an entity, while worn and not on cooldown */
    public static final ComponentType.Composite<DamageOnAttack> DAMAGE_ON_ATTACK =
            registerComposite("damage_on_attack", DamageOnAttack.CODEC, DamageOnAttack.STREAM_CODEC);
    /** Damage applied to the item when farting, while worn and not on cooldown */
    public static final ComponentType.Singleton<Value<Integer>> DAMAGE_ON_FART =
            registerSynced("damage_on_fart", ValueTypes.NON_NEGATIVE_INT.codec(), ValueTypes.NON_NEGATIVE_INT.streamCodec());
    /** Damage applied to the item after mining a block, while worn and not on cooldown */
    public static final ComponentType.Singleton<DamageOnBlockMined> DAMAGE_ON_BLOCK_MINED =
            registerSynced("damage_on_block_mined", DamageOnBlockMined.CODEC, DamageOnBlockMined.STREAM_CODEC);
    /** Damage applied to the item after completing a trade with a villager, while worn and not on cooldown */
    public static final ComponentType.Singleton<Value<Integer>> DAMAGE_ON_TRADE =
            registerSynced("damage_on_trade", ValueTypes.NON_NEGATIVE_INT.codec(), ValueTypes.NON_NEGATIVE_INT.streamCodec());
    /** Damage applied to the item after consuming an item, while worn and not on cooldown */
    public static final ComponentType.Singleton<DamageOnItemConsumed> DAMAGE_ON_ITEM_CONSUMED =
            registerSynced("damage_on_item_consumed", DamageOnItemConsumed.CODEC, DamageOnItemConsumed.STREAM_CODEC);
    // TODO: allow multiple entries
    /** Damage applied to the item every second, while worn and not on cooldown */
    public static final ComponentType.Singleton<DamageOverTime> DAMAGE_OVER_TIME =
            registerSynced("damage_over_time", DamageOverTime.CODEC, DamageOverTime.STREAM_CODEC);
    /** Damage applied to the item after fishing an item, while worn and not on cooldown */
    public static final ComponentType.Singleton<Value<Integer>> DAMAGE_ON_ITEM_FISHED =
            registerSynced("damage_on_item_fished", ValueTypes.NON_NEGATIVE_INT.codec(), ValueTypes.NON_NEGATIVE_INT.streamCodec());
    /** Damage applied to the item after jumping, while worn and not on cooldown */
    public static final ComponentType.Singleton<Value<Integer>> DAMAGE_ON_JUMP =
            registerSynced("damage_on_jump", ValueTypes.NON_NEGATIVE_INT.codec(), ValueTypes.NON_NEGATIVE_INT.streamCodec());
    /** Damage applied to the item after falling more than 3 blocks without taking damage, while worn and not on cooldown */
    public static final ComponentType.Singleton<Value<Integer>> DAMAGE_ON_FALL =
            registerSynced("damage_on_fall", ValueTypes.NON_NEGATIVE_INT.codec(), ValueTypes.NON_NEGATIVE_INT.streamCodec());

    // Abilities (= components that prevent the 'cosmetic' tooltip from showing up when EquipmentAbility#isNonCosmetic returns true)
    /** Cooldown applied to the item after receiving damage, possibly filtered by damage type tag */
    public static final ComponentType.Singleton<PostDamageCooldown> POST_DAMAGE_COOLDOWN =
            registerSynced("post_damage_cooldown", PostDamageCooldown.CODEC, PostDamageCooldown.STREAM_CODEC);
    /** Effects applied to the entity with the ability after receiving damage, possibly filtered by damage type tag */
    public static final ComponentType.Composite<PostDamageEffect> POST_DAMAGE_EFFECTS =
            registerComposite("post_damage_effects", PostDamageEffect.CODEC, PostDamageEffect.STREAM_CODEC);
    /** Effects applied to the player with the ability after restoring food points */
    public static final ComponentType.Composite<PostEatingEffect> POST_EATING_EFFECTS =
            registerComposite("post_eating_effects", PostEatingEffect.CODEC, PostEatingEffect.STREAM_CODEC);
    /** Damage absorbed from melee attacks */
    public static final ComponentType.Singleton<DamageAbsorption> DAMAGE_ABSORPTION =
            registerSynced("damage_absorption", DamageAbsorption.CODEC, DamageAbsorption.STREAM_CODEC);
    /** Effects applied to the attacked entity after an entity with this ability performs a melee attack */
    public static final ComponentType.Composite<AttackEffect> ATTACK_EFFECTS =
            registerComposite("attack_effects", AttackEffect.CODEC, AttackEffect.STREAM_CODEC);
    /** Attribute modifiers applied to the entity with the ability */
    public static final ComponentType.Composite<EquipmentAttributeModifier> ATTRIBUTE_MODIFIERS =
            registerComposite("attribute_modifiers", EquipmentAttributeModifier.CODEC, EquipmentAttributeModifier.STREAM_CODEC);
    /** Damage types the entity with the ability is immune to */
    public static final ComponentType.Singleton<DamageImmunity> DAMAGE_IMMUNITY =
            registerSynced("damage_immunity", DamageImmunity.CODEC, DamageImmunity.STREAM_CODEC);
    /** Allows the entity with the ability to double jump */
    public static final ComponentType.Singleton<DoubleJump> DOUBLE_JUMP =
            registerSynced("double_jump", DoubleJump.CODEC, DoubleJump.STREAM_CODEC);
    /** Allows the entity with the ability to throw ender pearls for free, consuming hunger and/or durability instead of the pearl */
    public static final ComponentType.Singleton<EnderPearlHungerCost> ENDER_PEARL_HUNGER_COST =
            registerSynced("ender_pearl_hunger_cost", EnderPearlHungerCost.CODEC, EnderPearlHungerCost.STREAM_CODEC);
    /** Grow plants under the entity with the ability after eating while standing on a block in the #rooted_boots_grass tag */
    public static final ComponentType.Singleton<SimpleAbility> POST_EATING_PLANT_GROWTH =
            registerSimpleAbility("post_eating_plant_growth");
    /** Increase the level of specific enchantments on the entity with the ability */
    public static final ComponentType.Composite<EnchantmentLevelModifier> ENCHANTMENT_LEVEL_MODIFIERS =
            registerComposite("enchantment_level_modifiers", EnchantmentLevelModifier.CODEC, EnchantmentLevelModifier.STREAM_CODEC);
    /** Apply mob effects to the entity with the ability, optionally under specific conditions */
    public static final ComponentType.Composite<EquipmentMobEffect> MOB_EFFECTS =
            registerComposite("mob_effects", EquipmentMobEffect.CODEC, EquipmentMobEffect.STREAM_CODEC);
    /** Prevents the entity with the ability from taking damage from ender pearls */
    public static final ComponentType.Singleton<SimpleAbility> ENDER_PEARL_DAMAGE_IMMUNITY =
            registerSimpleAbility("ender_pearl_damage_immunity");
    /** Sets a maximum duration on negative effects applied to the entity with the ability */
    public static final ComponentType.Singleton<CureEffects> CURE_EFFECTS =
            registerSynced("cure_effects", CureEffects.CODEC, CureEffects.STREAM_CODEC);
    /** Restores the entity's food points while standing on blocks in the #rooted_boots_grass tag */
    public static final ComponentType.Singleton<ReplenishHungerOnGrass> REPLENISH_HUNGER_ON_GRASS =
            registerSynced("replenish_hunger_on_grass", ReplenishHungerOnGrass.CODEC, ReplenishHungerOnGrass.STREAM_CODEC);
    /** Prevents creepers from targeting and attacking the entity with the ability */
    public static final ComponentType.Singleton<SimpleAbility> CREEPER_REPELLENT =
            registerSimpleAbility("creeper_repellent");
    /** Repels phantoms targeting the entity with the ability when they get to close */
    public static final ComponentType.Singleton<SimpleAbility> PHANTOM_REPELLENT =
            registerSimpleAbility("phantom_repellent");
    /** Removes collisions with fluids for the entity with the ability */
    public static final ComponentType.Singleton<SimpleAbility> SINKING =
            registerSimpleAbility("sinking");
    /** Smelt ores mined by the entity with the ability */
    public static final ComponentType.Singleton<SimpleAbility> AUTO_SMELT =
            registerSimpleAbility("auto_smelt");
    /** Enables collisions with fluids for the entity with the ability, possibly filtered by condition or fluid type tag */
    public static final ComponentType.Singleton<FluidCollision> FLUID_COLLISION =
            registerSynced("fluid_collision", FluidCollision.CODEC, FluidCollision.STREAM_CODEC);
    /** Enables swim-flying for the entity with the ability */
    public static final ComponentType.Singleton<SwimInAir> SWIM_IN_AIR =
            registerSynced("swim_in_air", SwimInAir.CODEC, SwimInAir.STREAM_CODEC);
    // TODO: probably should rename this to `equippable_totem` to match vanilla
    /**
     * Adds the chorus totem tooltip when not marked as cosmetic, and allows the death_protection component to apply when in an equipment slot.
     * The vanilla death_protection should be used for the actual protection effects.
     */
    public static final ComponentType.Singleton<EquipableTotem> EQUIPABLE_TOTEM =
            registerSynced("equipable_totem", EquipableTotem.CODEC, EquipableTotem.STREAM_CODEC);
    /** Effects applied to attacking entities when the entity with the ability is attacked */
    public static final ComponentType.Singleton<RetaliationEffects> RETALIATION_EFFECTS =
            registerSynced("retaliation_effects", RetaliationEffects.CODEC, RetaliationEffects.STREAM_CODEC);
    /** Allows the entity with the ability to mine blocks without a tool up to a specific tool tier */
    public static final ComponentType.Singleton<ToolTierUpgrade> TOOL_TIER_UPGRADE =
            registerSynced("tool_tier_upgrade", ToolTierUpgrade.CODEC, ToolTierUpgrade.STREAM_CODEC);
    /** Allows the entity with the ability to walk on powder snow */
    public static final ComponentType.Singleton<SimpleAbility> WALK_ON_POWDER_SNOW =
            registerSimpleAbility("walk_on_powder_snow");
    /**
     * Adds the 'Can be used as a shield' tooltip, and prevents the 'cosmetic' tooltip from showing up when enabled.
     * The vanilla blocks_attacks component should be used for the actual blocking logic.
     */
    public static final ComponentType.Singleton<SimpleAbility> BLOCKS_ATTACKS =
            registerSimpleAbility("blocks_attacks");
    /**
     * Prevents fall damage and slows the fall of entities holding the item.
     * When this component is present but not enabled, the item still behaves like an umbrella visually.
     */
    public static final ComponentType.Singleton<SimpleAbility> HANDHELD_GLIDER =
            registerSimpleAbility("handheld_glider");
    /**
     * Prevents the item from being consumed when eaten. Indestructible items with this ability will store their consumable
     * in the disabled_consumable component until repaired
     */
    public static final ComponentType.Singleton<SimpleAbility> INFINITE_CONSUMABLE =
            registerSimpleAbility("infinite_consumable");

    static {
        TICKING_ABILITIES.addAll(List.of(
                new TickingAbility<>(ATTRIBUTE_MODIFIERS, new EquipmentAttributeModifier.Ticker()),
                new TickingAbility<>(REPLENISH_HUNGER_ON_GRASS, new ReplenishHungerOnGrass.Ticker()),
                new TickingAbility<>(CURE_EFFECTS, new CureEffects.Ticker()),
                new TickingAbility<>(MOB_EFFECTS, new EquipmentMobEffect.Ticker()),
                new TickingAbility<>(FLUID_COLLISION, new FluidCollision.Ticker())
        ));
        APPLIES_COOLDOWN.addAll(Set.of(
                POST_DAMAGE_COOLDOWN,
                RETALIATION_EFFECTS,
                SWIM_IN_AIR,
                ATTACK_EFFECTS,
                CURE_EFFECTS
        ));
    }

    // TODO: use a component or tag to mark items with cooldowns
    public static boolean hasAbilityWithCooldown(ItemStack stack) {
        for (Supplier<? extends DataComponentType<?>> componentType : APPLIES_COOLDOWN) {
            if (stack.has(componentType.get())) {
                return true;
            }
        }
        return false;
    }

    private static ComponentType.Singleton<SimpleAbility> registerSimpleAbility(String name) {
        return new ComponentType.Singleton<>(synced(name, SimpleAbility.CODEC, SimpleAbility.STREAM_CODEC));
    }

    private static <T> ComponentType.Singleton<T> registerSynced(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return new ComponentType.Singleton<>(synced(name, codec, streamCodec));
    }

    private static <T> ComponentType.Composite<T> registerComposite(String name, Codec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        return new ComponentType.Composite<>(synced(name, CompositeComponent.codec(codec), CompositeComponent.streamCodec(streamCodec)));
    }

    private static <T> ComponentType.Singleton<T> registerCached(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return new ComponentType.Singleton<>(register(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec).cacheEncoding()));
    }

    private static <T> Supplier<DataComponentType<T>> synced(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return register(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }

    private static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }

    public record TickingAbility<C, T extends EquipmentAbility>(
            ComponentType<C, T> type,
            AbilityTicker<T> ticker
    ) { }
}
