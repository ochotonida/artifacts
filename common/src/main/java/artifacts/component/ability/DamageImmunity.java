package artifacts.component.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.util.ModCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.Set;

public record DamageImmunity(Value<Boolean> enabled, TagKey<DamageType> tag, EntityCondition condition)
        implements EquipmentAbility {

    private static final Set<TagKey<DamageType>> CUSTOM_TOOLTIP_TAGS = Set.of(
            DamageTypeTags.IS_LIGHTNING,
            DamageTypeTags.BURN_FROM_STEPPING
    );

    public static final Codec<DamageImmunity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ValueTypes.enabledField().forGetter(DamageImmunity::enabled),
            TagKey.codec(Registries.DAMAGE_TYPE).fieldOf("tag").forGetter(DamageImmunity::tag),
            EntityCondition.CODEC.optionalFieldOf("condition", EntityCondition.ALWAYS).forGetter(DamageImmunity::condition)
    ).apply(instance, DamageImmunity::new));

    public static final StreamCodec<ByteBuf, DamageImmunity> STREAM_CODEC = StreamCodec.composite(
            ValueTypes.BOOLEAN.streamCodec(),
            DamageImmunity::enabled,
            ModCodecs.tagKeyStreamCodec(Registries.DAMAGE_TYPE),
            DamageImmunity::tag,
            EntityCondition.STREAM_CODEC,
            DamageImmunity::condition,
            DamageImmunity::new
    );

    @Override
    public boolean isNonCosmetic() {
        return enabled().get();
    }

    @Override
    public void addToTooltip(TooltipWriter writer) {
        if (CUSTOM_TOOLTIP_TAGS.contains(tag()) && condition() == EntityCondition.ALWAYS) {
            writer.add(tag().location().getPath());
        }
    }
}
