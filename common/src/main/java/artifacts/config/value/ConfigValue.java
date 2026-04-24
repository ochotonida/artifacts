package artifacts.config.value;

import artifacts.config.value.type.ValueType;
import net.minecraft.util.StringRepresentable;

public final class ConfigValue<T> implements Value<T>, StringRepresentable {

    private final ValueType<T, ?> type;
    private final String id;
    private final T defaultValue;
    private final boolean requiresRestart;

    private T value;

    public ConfigValue(ValueType<T, ?> type, String id, T defaultValue) {
        this(type, id, defaultValue, false);
    }

    public ConfigValue(ValueType<T, ?> type, String id, T defaultValue, boolean requiresRestart) {
        this.type = type;
        this.id = id;
        this.requiresRestart = requiresRestart;
        this.defaultValue = this.value = defaultValue;
    }

    public String getId() {
        return id;
    }

    public String getSerializedName() {
        return getId();
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public ValueType<T, ?> type() {
        return type;
    }

    public boolean requiresRestart() {
        return requiresRestart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof artifacts.config.value.ConfigValue<?> that)) return false;

        return type.equals(that.type) && getId().equals(that.getId()) && getDefaultValue().equals(that.getDefaultValue());
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getDefaultValue().hashCode();
        return result;
    }
}
