package artifacts.component.ability.mobeffect;

import artifacts.component.ability.CompositeAbility;
import artifacts.component.ability.EquipmentAbility;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import artifacts.util.ModCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Optional;

public record PostDamageEffects(List<Entry> entries) implements CompositeAbility<PostDamageEffects.Entry> {

    public static final Codec<PostDamageEffects> CODEC = Entry.CODEC.listOf(0, 16).xmap(
            PostDamageEffects::new, PostDamageEffects::entries
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PostDamageEffects> STREAM_CODEC = ByteBufCodecs.<RegistryFriendlyByteBuf, Entry>list()
            .apply(Entry.STREAM_CODEC).map(PostDamageEffects::new, PostDamageEffects::entries);

    public static void onLivingDamaged(LivingEntity entity, DamageSource damageSource) {
        if (!entity.level().isClientSide()) {
            EquipmentHelper.iterateAbilities(ModDataComponents.POST_DAMAGE_EFFECTS.get(), entity, true, true, (ability, stack) -> {
                for (Entry entry : ability.entries) {
                    if (entry.shouldApply(damageSource.type(), entity)) {
                        entity.addEffect(entry.provider.createEffect());
                    }
                }
            });
        }
    }

    public record Entry(MobEffectProvider provider, Optional<TagKey<DamageType>> tag, Value<Double> chance) implements EquipmentAbility {

        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                MobEffectProvider.codec(true).fieldOf("effect").forGetter(Entry::provider),
                TagKey.codec(Registries.DAMAGE_TYPE).optionalFieldOf("tag").forGetter(Entry::tag),
                ValueTypes.FRACTION.codec().optionalFieldOf("chance", Value.of(1D)).forGetter(Entry::chance)
        ).apply(instance, Entry::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Entry> STREAM_CODEC = StreamCodec.composite(
                MobEffectProvider.STREAM_CODEC,
                Entry::provider,
                ByteBufCodecs.optional(ModCodecs.tagKeyStreamCodec(Registries.DAMAGE_TYPE)),
                Entry::tag,
                ValueTypes.FRACTION.streamCodec(),
                Entry::chance,
                Entry::new
        );

        public boolean shouldApply(DamageType type, LivingEntity entity) {
            return provider.canApply(entity)
                    && entity.getRandom().nextDouble() < chance.get()
                    && (tag.isEmpty() || entity.level().registryAccess().registry(Registries.DAMAGE_TYPE)
                    .flatMap(registry -> registry.getTag(tag.get()).map(tag -> tag.contains(registry.wrapAsHolder(type))))
                    .orElse(false));
        }

        @Override
        public boolean isNonCosmetic() {
            return provider.isNonCosmetic() && chance.get() > 0;
        }

        @Override
        public void addToTooltip(TooltipWriter writer) {
            if (provider.mobEffect().equals(MobEffects.FIRE_RESISTANCE) && tag.isPresent() && tag.get().equals(DamageTypeTags.IS_FIRE) && chance.get() == 1) {
                writer.add("fire_resistance");
            } else if (provider.mobEffect().equals(MobEffects.MOVEMENT_SPEED) && tag.isEmpty() && chance.get() == 1) {
                writer.add("speed");
            }
        }
    }
}
