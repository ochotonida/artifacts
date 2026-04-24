package artifacts.config.screen;

import artifacts.config.ConfigManager;
import artifacts.config.value.ConfigValue;
import artifacts.config.value.type.EnumValueType;
import artifacts.config.value.type.NumberValueType;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.FieldBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

// cloth config classes don't exist on dedicated servers
public class ConfigEntries {

    public static ConfigEntryFactory<Boolean> booleanConfigEntryFactory() {
        return (config, entryBuilder, title, value) -> entryBuilder.startBooleanToggle(title, config.read(value.type(), value.getId()))
                .setDefaultValue(value.getDefaultValue())
                .setSaveConsumer(v -> config.write(value.type(), value.getId(), v));
    }

    public static ConfigEntryFactory<Integer> integerConfigEntryFactory(NumberValueType<Integer> type) {
        return (config, entryBuilder, title, value) -> entryBuilder.startIntField(title, config.read(value.type(), value.getId()))
                .setDefaultValue(value.getDefaultValue())
                .setSaveConsumer(v -> config.write(value.type(), value.getId(), v))
                .setMin(type.getMin())
                .setMax(type.getMax());
    }

    public static ConfigEntryFactory<Double> doubleConfigEntryFactory(NumberValueType<Double> type) {
        return (config, entryBuilder, title, value) -> entryBuilder
                .startDoubleField(title, config.read(value.type(), value.getId()))
                .setDefaultValue(value.getDefaultValue())
                .setSaveConsumer(v -> config.write(value.type(), value.getId(), v))
                .setMin(type.getMin())
                .setMax(type.getMax());
    }

    public static <T extends Enum<T> & StringRepresentable> ConfigEntryFactory<T> enumConfigEntryFactory(EnumValueType<T> type) {
        // noinspection unchecked
        return (config, entryBuilder, title, value) -> entryBuilder
                .startEnumSelector(title, (Class<T>) value.getDefaultValue().getClass(), config.read(value.type(), value.getId()))
                .setEnumNameProvider(e -> type.getAsComponent((T) e))
                .setDefaultValue(value.getDefaultValue())
                .setSaveConsumer(v -> config.write(value.type(), value.getId(), v));
    }

    public interface ConfigEntryFactory<T> {

        FieldBuilder<?, ?, ?> createConfigEntry(ConfigManager config, ConfigEntryBuilder entryBuilder, Component title, ConfigValue<T> value);

    }
}
