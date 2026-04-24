package artifacts.config;

import artifacts.Artifacts;
import artifacts.config.value.ConfigValue;
import artifacts.config.value.ValueTypes;
import artifacts.config.value.type.EnumValueType;
import artifacts.config.value.type.NumberValueType;
import artifacts.config.value.type.ValueType;
import artifacts.platform.PlatformServices;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileWatcher;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// see Neoforge ConfigFileTypeHandler
public abstract class ConfigManager {

    protected CommentedFileConfig config;
    protected final ConfigSpec spec = new ConfigSpec();
    private final Path configPath;
    private final String name;

    private final Map<String, ConfigValue<?>> values = new HashMap<>();
    private final Map<String, List<String>> tooltips = new HashMap<>();

    private final Map<ValueType<?, ?>, ValueMap<?>> typeToValues = new HashMap<>();

    protected ConfigManager(String fileName) {
        this.name = fileName;
        this.configPath = Path.of(Artifacts.MOD_ID, "%s.toml".formatted(fileName));
    }

    protected void setup() {
        config = CommentedFileConfig.builder(PlatformServices.getPlatformHelper().getConfigDir().resolve(configPath))
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .onFileNotFound(this::createNewConfigFile)
                .writingMode(WritingMode.REPLACE)
                .build();

        Path path = PlatformServices.getPlatformHelper().getConfigDir().resolve(configPath);

        try {
            config.load();
        } catch (ParsingException exception) {
            Artifacts.LOGGER.warn("Failed to load config file {}, attempting to recreate", configPath);
            try {
                createBackup(config.getNioPath(), 5);
                Files.delete(config.getNioPath());
                config.load();
            } catch (Throwable t) {
                exception.addSuppressed(t);
                throw new RuntimeException("Failed to load config file " + configPath, exception);
            }
        }

        if (!spec.isCorrect(config)) {
            correctConfigAndSave();
        }

        Artifacts.LOGGER.debug("Loaded config file {}", configPath);
        FileWatcher.defaultInstance().addWatch(path, new ConfigWatcher());
        Artifacts.LOGGER.debug("Watching config file {} for changes", configPath);
    }

    private boolean createNewConfigFile(Path file, ConfigFormat<?> conf) throws IOException {
        Files.createDirectories(file.getParent());
        Files.createFile(file);
        conf.initEmptyFile(file);
        return true;
    }

    private void correctConfigAndSave() {
        addMissingKeys();
        spec.correct(config);
        config.save();
    }

    public String getName() {
        return name;
    }

    public Map<String, ConfigValue<?>> getValues() {
        return values;
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String, ConfigValue<T>> getValues(ValueType<T, ?> type) {
        ValueMap<T> valueMap = (ValueMap<T>) typeToValues.get(type);
        return valueMap.getMap();
    }

    public List<String> getDescription(String key) {
        return tooltips.get(key);
    }

    public <T, C> T read(ValueType<T, C> type, String key) {
        return type.read(config.get(key));
    }

    public <T, C> void write(ValueType<T, C> type, String key, T value) {
        config.set(key, type.write(value));
    }

    private <T> void reset(String key, ConfigValue<T> value) {
        config.add(key, value.type().write(value.getDefaultValue()));
    }

    protected <T> void readValueFromConfig(String key, ConfigValue<T> value) {
        value.set(read(value.type(), key));
    }

    public void readValuesFromConfig() {
        getValues().forEach(this::readValueFromConfig);
    }

    protected void addMissingKeys() {
        Map<String, ConfigValue<?>> values = getValues();

        List<String> keys = new ArrayList<>(values.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            if (!config.contains(key)) {
                ConfigValue<?> value = values.get(key);
                reset(key, value);
                StringBuilder builder = new StringBuilder();
                for (String tooltip : getDescription(key)) {
                    builder.append(tooltip).append('\n');
                }
                builder.append(value.type().getAllowedValuesComment());
                config.setComment(key, builder.toString());
            }
        }
    }

    public void onConfigChanged() {
        readValuesFromConfig();
    }

    public static void createBackup(final Path commentedFileConfig, final int maxBackups) {
        Path bakFileLocation = commentedFileConfig.getParent();
        String bakFileName = FilenameUtils.removeExtension(commentedFileConfig.getFileName().toString());
        String bakFileExtension = FilenameUtils.getExtension(commentedFileConfig.getFileName().toString()) + ".bak";
        Path bakFile = bakFileLocation.resolve(bakFileName + "-1" + "." + bakFileExtension);
        try {
            for (int i = maxBackups; i > 0; i--) {
                Path oldBak = bakFileLocation.resolve(bakFileName + "-" + i + "." + bakFileExtension);
                if (Files.exists(oldBak)) {
                    if (i >= maxBackups)
                        Files.delete(oldBak);
                    else
                        Files.move(oldBak, bakFileLocation.resolve(bakFileName + "-" + (i + 1) + "." + bakFileExtension));
                }
            }
            Files.copy(commentedFileConfig, bakFile);
        } catch (IOException exception) {
            Artifacts.LOGGER.warn("Failed to back up config file {}", commentedFileConfig, exception);
        }
    }

    protected ConfigValueBuilder<Boolean> define(String key, boolean defaultValue) {
        return new ConfigValueBuilder<>(key, ValueTypes.BOOLEAN, defaultValue) {
            @Override
            protected void defineInSpec() {
                spec.define(key, defaultValue);
            }
        };
    }

    protected <T extends Number & Comparable<T>> ConfigValueBuilder<T> define(String key, NumberValueType<T> type, T defaultValue) {
        return new ConfigValueBuilder<>(key, type, defaultValue) {
            @Override
            protected void defineInSpec() {
                spec.defineInRange(key, defaultValue, type.getMin(), type.getMax());
            }
        };
    }

    protected <T extends Enum<T> & StringRepresentable> ConfigValueBuilder<T> define(String key, EnumValueType<T> type, T defaultValue) {
        return new ConfigValueBuilder<>(key, type, defaultValue) {
            @Override
            protected void defineInSpec() {
                List<String> allowedValues = new ArrayList<>();
                allowedValues.addAll(type.getValues().stream().map(StringRepresentable::getSerializedName).toList());
                allowedValues.addAll(type.getValues().stream().map(StringRepresentable::getSerializedName).map(String::toUpperCase).toList());
                spec.defineInList(key, defaultValue.getSerializedName(), allowedValues);
            }
        };
    }

    private static class ValueMap<T> {
        private final Map<String, ConfigValue<T>> map = new HashMap<>();

        public Map<String, ConfigValue<T>> getMap() {
            return map;
        }
    }

    public abstract class Category {

        private final String name;

        protected Category(String name) {
            this.name = name;
        }

        protected ConfigValueBuilder<Boolean> define(String key, boolean defaultValue) {
            return ConfigManager.this.define(addPrefix(key), defaultValue);
        }

        protected <T extends Number & Comparable<T>> ConfigValueBuilder<T> define(String key, NumberValueType<T> type, T defaultValue) {
            return ConfigManager.this.define(addPrefix(key), type, defaultValue);
        }

        protected <T extends Enum<T> & StringRepresentable> ConfigValueBuilder<T> define(String key, EnumValueType<T> type, T defaultValue) {
            return ConfigManager.this.define(addPrefix(key), type, defaultValue);
        }

        private String addPrefix(String key) {
            return name + '.' + key;
        }
    }

    public abstract class ConfigValueBuilder<T> {

        private final String key;
        private final ValueType<T, ?> type;
        private final T defaultValue;

        private final List<String> tooltip = new ArrayList<>();
        private boolean requiresRestart = false;

        public ConfigValueBuilder(String key, ValueType<T, ?> type, T defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.type = type;
        }

        protected abstract void defineInSpec();

        protected ConfigValue<T> createValue(String key, ValueType<T, ?> type, T defaultValue, boolean requiresRestart, String... tooltips) {
            ConfigValue<T> value = new ConfigValue<>(type, key, defaultValue, requiresRestart);
            values.put(key, value);
            ConfigManager.this.tooltips.put(key, List.of(tooltips));
            if (!ConfigManager.this.typeToValues.containsKey(type)) {
                typeToValues.put(type, new ValueMap<>());
            }
            // noinspection unchecked
            ((ValueMap<T>) typeToValues.get(type)).getMap().put(key, value);
            return value;
        }

        public ConfigValue<T> build() {
            defineInSpec();
            return createValue(
                    key, type, defaultValue, requiresRestart,
                    tooltip.toArray(new String[0])
            );
        }

        public ConfigValueBuilder<T> requiresRestart() {
            requiresRestart = true;
            return this;
        }

        public ConfigValueBuilder<T> tooltipLine(String line) {
            tooltip.add(line);
            return this;
        }
    }

    private class ConfigWatcher implements Runnable {

        @Override
        public void run() {
            try {
                config.load();
                if (!spec.isCorrect(config)) {
                    Artifacts.LOGGER.warn("Configuration file {} is not correct. Correcting", configPath);
                    correctConfigAndSave();
                }
            } catch (ParsingException exception) {
                throw new RuntimeException("Failed to load config file " + configPath, exception);
            }
            Artifacts.LOGGER.info("Config file {} changed", configPath);
            onConfigChanged();
        }
    }
}
