package artifacts.component.itemdamage;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;

import java.util.Optional;

public record DamageOnAttack(Value<Integer> itemDamage, boolean requireMelee, boolean requireKill, Optional<HolderSet<EntityType<?>>> entity) {

    public static final Codec<DamageOnAttack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ValueTypes.itemDamageField(1).forGetter(DamageOnAttack::itemDamage),
            Codec.BOOL.optionalFieldOf("require_melee", false).forGetter(DamageOnAttack::requireMelee),
            Codec.BOOL.optionalFieldOf("require_kill", false).forGetter(DamageOnAttack::requireKill),
            RegistryCodecs.holderSet(Registries.ENTITY_TYPE, false).optionalFieldOf("entity").forGetter(DamageOnAttack::entity)
    ).apply(instance, DamageOnAttack::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DamageOnAttack> STREAM_CODEC = StreamCodec.composite(
            ValueTypes.DURATION.streamCodec(),
            DamageOnAttack::itemDamage,
            ByteBufCodecs.BOOL,
            DamageOnAttack::requireMelee,
            ByteBufCodecs.BOOL,
            DamageOnAttack::requireKill,
            ByteBufCodecs.optional(ByteBufCodecs.holderSet(Registries.ENTITY_TYPE)),
            DamageOnAttack::entity,
            DamageOnAttack::new
    );
}
