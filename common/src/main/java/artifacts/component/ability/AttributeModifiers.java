package artifacts.component.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.registry.ModAttributes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record AttributeModifiers(List<Entry> entries) implements TickingCompositeAbility<AttributeModifiers.Entry> {

    private static final Set<Holder<Attribute>> POSITIVE_ATTRIBUTES_WITH_TOOLTIP;
    private static final Set<Holder<Attribute>> NEGATIVE_ATTRIBUTES_WITH_TOOLTIP = Set.of(
            Attributes.SCALE,
            Attributes.FALL_DAMAGE_MULTIPLIER
    );

    static {
        POSITIVE_ATTRIBUTES_WITH_TOOLTIP = new HashSet<>();
        POSITIVE_ATTRIBUTES_WITH_TOOLTIP.addAll(ModAttributes.PLAYER_ATTRIBUTES);
        POSITIVE_ATTRIBUTES_WITH_TOOLTIP.addAll(ModAttributes.GENERIC_ATTRIBUTES);
        POSITIVE_ATTRIBUTES_WITH_TOOLTIP.add(ModAttributes.SWIM_SPEED);
        POSITIVE_ATTRIBUTES_WITH_TOOLTIP.addAll(List.of(
                Attributes.ATTACK_DAMAGE,
                Attributes.ATTACK_KNOCKBACK,
                Attributes.ATTACK_SPEED,
                Attributes.BLOCK_BREAK_SPEED,
                Attributes.JUMP_STRENGTH,
                Attributes.KNOCKBACK_RESISTANCE,
                Attributes.MAX_HEALTH,
                Attributes.SAFE_FALL_DISTANCE,
                Attributes.OXYGEN_BONUS
        ));
    }

    public static final Codec<AttributeModifiers> CODEC =
            CompositeAbility.codec(Entry.CODEC, AttributeModifiers::new, AttributeModifiers::entries);
    public static final StreamCodec<RegistryFriendlyByteBuf, AttributeModifiers> STREAM_CODEC =
            CompositeAbility.streamCodec(Entry.STREAM_CODEC, AttributeModifiers::new, AttributeModifiers::entries);

    public record Entry(Holder<Attribute> attribute, Value<Double> amount, AttributeModifier.Operation operation,
                        ResourceLocation id, boolean ignoreCooldown) implements TickingAbility {

        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(Entry::attribute),
                ValueTypes.ATTRIBUTE_MODIFIER.codec().fieldOf("amount").forGetter(Entry::amount),
                AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(Entry::operation),
                ResourceLocation.CODEC.fieldOf("id").forGetter(Entry::id),
                Codec.BOOL.optionalFieldOf("ignore_cooldown", true).forGetter(Entry::ignoreCooldown)
        ).apply(instance, Entry::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Entry> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.holderRegistry(Registries.ATTRIBUTE),
                Entry::attribute,
                ValueTypes.ATTRIBUTE_MODIFIER.streamCodec(),
                Entry::amount,
                AttributeModifier.Operation.STREAM_CODEC,
                Entry::operation,
                ResourceLocation.STREAM_CODEC,
                Entry::id,
                ByteBufCodecs.BOOL,
                Entry::ignoreCooldown,
                Entry::new
        );

        public AttributeModifier createModifier() {
            return new AttributeModifier(id(), amount().get(), operation());
        }

        private void onAttributeUpdated(LivingEntity entity) {
            if (attribute() == Attributes.MAX_HEALTH && entity.getHealth() > entity.getMaxHealth()) {
                entity.setHealth(entity.getMaxHealth());
            }
        }

        @Override
        public void onUnequip(LivingEntity entity) {
            AttributeInstance attributeInstance = entity.getAttribute(attribute());
            if (attributeInstance != null && attributeInstance.hasModifier(id())) {
                attributeInstance.removeModifier(id());
                onAttributeUpdated(entity);
            }
        }

        @Override
        public void wornTick(LivingEntity entity, boolean isOnCooldown, boolean isDisabled) {
            AttributeInstance attributeInstance = entity.getAttribute(attribute());
            if (attributeInstance == null) {
                return;
            }
            AttributeModifier existingModifier = attributeInstance.getModifier(id());
            if (!ignoreCooldown() && isOnCooldown) {
                if (!isDisabled && isNonCosmetic()) {
                    onUnequip(entity);
                }
            } else if (!isDisabled) {
                if (existingModifier == null || !Mth.equal(amount().get(), existingModifier.amount())) {
                    attributeInstance.removeModifier(id());
                    attributeInstance.addPermanentModifier(createModifier());
                    onAttributeUpdated(entity);
                }
            }
        }


        @Override
        public boolean isNonCosmetic() {
            return !Mth.equal(amount().get(), 0);
        }

        @Override
        public void addToTooltip(TooltipWriter writer) {
            String attributeName = attribute().unwrapKey().orElseThrow().location().getPath();
            if (attributeName.equals("swim_speed")) { // neoforge swim speed
                attributeName = "generic.swim_speed";
            }

            if (amount().get() > 0) {
                for (Holder<Attribute> attribute : POSITIVE_ATTRIBUTES_WITH_TOOLTIP) {
                    if (attribute.isBound() && attribute.value() == attribute().value()) {
                        writer.add(attributeName);
                    }
                }
            } else {
                for (Holder<Attribute> attribute : NEGATIVE_ATTRIBUTES_WITH_TOOLTIP) {
                    if (attribute.isBound() && attribute.value() == attribute().value()) {
                        writer.add(attributeName);
                    }
                }
            }
        }
    }
}
