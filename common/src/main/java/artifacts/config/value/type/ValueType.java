package artifacts.config.value.type;

import artifacts.Artifacts;
import artifacts.config.screen.ConfigEntries;
import artifacts.config.value.ConfigValue;
import artifacts.config.value.Value;
import artifacts.util.ModCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.Util;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ValueType<T, C> {

    public abstract boolean isCorrect(T value);

    public abstract String makeError(T value);

    public abstract String getAllowedValuesComment();

    public abstract T read(C c);

    public abstract C write(T c);

    public final Codec<Value<T>> codec() {
        return ModCodecs.xorAlternative(
                configCodec().flatXmap(
                        DataResult::success,
                        value -> {
                            if (value instanceof ConfigValue<T> configValue
                                    && Artifacts.CONFIG.items.getValues(this).containsKey(configValue.getId())
                            ) {
                                return DataResult.success(configValue);
                            }
                            return DataResult.error(() -> "Not a valid config value: %s".formatted(value));
                        }
                ),
                constantCodec()
        );
    }

    protected abstract Codec<T> valueCodec();

    public final Codec<Value<T>> constantCodec() {
        return valueCodec().comapFlatMap(
                value -> isCorrect(value) ? DataResult.success(value) : DataResult.error(() -> makeError(value)),
                Function.identity()
        ).xmap(Value.Constant::new, Supplier::get);
    }

    public final Codec<ConfigValue<T>> configCodec() {
        return StringRepresentable.fromValues(this::getConfigValues);
    }

    public final StreamCodec<ByteBuf, Value<T>> streamCodec() {
        return ByteBufCodecs.BOOL.dispatch(
                value -> value instanceof ConfigValue<?>,
                b -> b ? configReferenceStreamCodec() : valueStreamCodec().map(Value.Constant::new, Supplier::get)
        );
    }

    public abstract StreamCodec<ByteBuf, T> valueStreamCodec();

    public final StreamCodec<ByteBuf, ConfigValue<T>> configReferenceStreamCodec() {
        ConfigValue<T>[] values = getConfigValues();
        return ByteBufCodecs.idMapper(i -> values[i], Util.createIndexLookup(Arrays.asList(values)));
    }

    public final StreamCodec<ByteBuf, ConfigValue<T>> directConfigStreamCodec(String id) {
        return valueStreamCodec().map(v -> new ConfigValue<>(this, id, v), ConfigValue::get);
    }

    @SuppressWarnings("unchecked")
    private ConfigValue<T>[] getConfigValues() {
        List<ConfigValue<T>> values = List.copyOf(Artifacts.CONFIG.items.getValues(this).values());
        // yikes
        ConfigValue<T>[] result = (ConfigValue<T>[]) java.lang.reflect.Array.newInstance(values.getFirst().getClass(), values.size());
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    public abstract ConfigEntries.ConfigEntryFactory<T> getConfigEntryFactory();
}
