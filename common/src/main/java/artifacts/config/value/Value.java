package artifacts.config.value;

import java.util.function.Supplier;

public interface Value<T> extends Supplier<T> {

    static <T> Value<T> of(T v) {
        return new Value.Constant<>(v);
    }

    record Constant<T>(T get) implements Value<T> { }

}
