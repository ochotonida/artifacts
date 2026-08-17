package artifacts.component.ability;

import artifacts.integration.ModCompat;
import artifacts.integration.origins.OriginsCompat;
import artifacts.registry.ModTags;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Predicate;

public enum EntityCondition implements StringRepresentable {
    ALWAYS("always", entity -> true),
    NEVER("never", entity -> false),
    ABOVE_WATER("above_water", entity -> !isSubmerged(entity)),
    IN_WATER("in_water", Entity::isInWater),
    ON_GRASS("on_grass", entity -> entity.onGround() && entity.getBlockStateOn().is(ModTags.ROOTED_BOOTS_GRASS)),
    SNEAKING("while_sneaking", Entity::isCrouching),
    SPRINTING("while_sprinting", entity -> entity.isSprinting() && !entity.isUsingItem() && !entity.isCrouching());

    public static final Codec<EntityCondition> CODEC = StringRepresentable.fromValues(EntityCondition::values);
    public static final StreamCodec<ByteBuf, EntityCondition> STREAM_CODEC = ByteBufCodecs.idMapper(i -> EntityCondition.values()[i], EntityCondition::ordinal);

    private final String name;
    private final Predicate<LivingEntity> predicate;

    EntityCondition(String name, Predicate<LivingEntity> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    public boolean test(LivingEntity entity) {
        return predicate.test(entity);
    }

    @Override
    public String getSerializedName() {
        return toString();
    }

    @Override
    public String toString() {
        return name;
    }

    private static boolean isSubmerged(LivingEntity entity) {
        return entity.isEyeInFluid(FluidTags.WATER)
                ^ (ModCompat.ORIGINS.isLoaded() && OriginsCompat.hasWaterBreathing(entity));
    }
}
