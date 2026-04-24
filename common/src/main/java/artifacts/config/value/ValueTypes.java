package artifacts.config.value;

import artifacts.component.ability.ToolTierUpgrade;
import artifacts.config.value.type.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.codec.ByteBufCodecs;

public class ValueTypes {

    public static final BooleanValueType BOOLEAN = new BooleanValueType();
    public static final NumberValueType<Double> NON_NEGATIVE_DOUBLE = new DoubleValueType(0D, Double.POSITIVE_INFINITY, Codec.DOUBLE, ByteBufCodecs.DOUBLE);
    public static final NumberValueType<Double> ATTRIBUTE_MODIFIER = new DoubleValueType(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Codec.DOUBLE, ByteBufCodecs.DOUBLE);
    public static final NumberValueType<Double> FRACTION = new DoubleValueType(0D, 1D, Codec.DOUBLE, ByteBufCodecs.DOUBLE);
    public static final NumberValueType<Integer> INT = new IntegerValueType(Integer.MIN_VALUE, Integer.MAX_VALUE, Codec.INT, ByteBufCodecs.INT);
    public static final NumberValueType<Integer> NON_NEGATIVE_INT = new IntegerValueType(0, Integer.MAX_VALUE, Codec.INT, ByteBufCodecs.INT);
    public static final NumberValueType<Integer> DURATION = new IntegerValueType(0, 60 * 60 * 20, Codec.INT, ByteBufCodecs.INT);
    public static final NumberValueType<Integer> ENCHANTMENT_LEVEL = new IntegerValueType(0, 100, Codec.INT, ByteBufCodecs.INT);
    public static final NumberValueType<Integer> MOB_EFFECT_LEVEL = new IntegerValueType(0, 256, Codec.INT, ByteBufCodecs.INT);
    public static final EnumValueType<ToolTierUpgrade.Tier> TOOL_TIER = new EnumValueType<>(ToolTierUpgrade.Tier.class, ToolTierUpgrade::getTierName);

    public static MapCodec<Value<Boolean>> enabledField() {
        return BOOLEAN.codec().optionalFieldOf("enabled", Value.of(true));
    }

    public static MapCodec<Value<Integer>> cooldownField() {
        return DURATION.codec().optionalFieldOf("cooldown", Value.of(0));
    }
}
