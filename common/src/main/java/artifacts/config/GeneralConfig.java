package artifacts.config;

import artifacts.config.value.ConfigValue;
import artifacts.config.value.ValueTypes;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.function.Supplier;

public final class GeneralConfig extends ConfigManager {

    public final Supplier<Double> artifactRarity
            = define("artifactRarity", ValueTypes.NON_NEGATIVE_DOUBLE, 1.0)
            .tooltipLine("Affects how common artifacts are in chests")
            .tooltipLine("Values above 1 will make artifacts rarer, values between 0 and 1 will make artifacts more common")
            .tooltipLine("Doubling this value will make artifacts approximately twice as hard to find, and vice versa")
            .tooltipLine("To prevent artifacts from appearing as chest loot, set this to 10000.")
            .build();

    public final Supplier<Double> entityEquipmentChance
            = define("entityEquipmentChance", ValueTypes.FRACTION, 0.0015D)
            .tooltipLine("The chance that a skeleton, zombie or piglin spawns with an artifact equipped")
            .build();

    public final Supplier<Double> archaeologyChance
            = define("archaeologyChance", ValueTypes.FRACTION, 1 / 16D)
            .tooltipLine("The chance that an artifact generates in suspicious sand or gravel")
            .build();

    public final Campsite campsite = new Campsite();
    public final Slots slots = new Slots();

    GeneralConfig() {
        super("general");
    }

    public final class Campsite extends Category {

        public final ConfigValue<Integer> count
                = define("campsiteCount", ValueTypes.NON_NEGATIVE_INT, 40)
                .tooltipLine("How many times a campsite will attempt to generate per chunk")
                .tooltipLine("Set this to 0 to prevent campsites from generating")
                .build();

        public final ConfigValue<Integer> minY
                = define("minY", ValueTypes.INT, -60)
                .tooltipLine("The minimum height campsites can spawn at")
                .build();

        public final ConfigValue<Integer> maxY
                = define("maxY", ValueTypes.INT, 40)
                .tooltipLine("The maximum height campsites can spawn at")
                .build();

        public final ConfigValue<Double> mimicChance
                = define("mimicChance", ValueTypes.FRACTION, 0.3)
                .tooltipLine("The probability that a campsite has a mimic instead of a chest")
                .build();

        public final ConfigValue<Boolean> useModdedChests
                = define("useModdedChests", true)
                .tooltipLine("Whether to use wooden chests from other mods when generating campsites")
                .build();

        public final ConfigValue<Boolean> allowLightSources
                = define("allowLightSources", true)
                .tooltipLine("Whether campsites can contain blocks that emit light")
                .build();

        public final ConfigValue<Boolean> minimalistCampsites
                = define("minimalistCampsites", false)
                .tooltipLine("Replaces campsites with a single chest/mimic")
                .build();

        private Campsite() {
            super("campsite");
        }

        @SuppressWarnings("unchecked")
        public Codec<ConfigValue<Boolean>> codec() {
            return StringRepresentable.fromValues(() -> new ConfigValue[]{
                    minimalistCampsites
            });
        }
    }

    // FIXME: Data pack overlays don't work in dev
    public final class Slots extends Category {

        public final ConfigValue<Boolean> enableAccessoriesCompat
                = define("enableAccessoriesCompat", true)
                .tooltipLine("Whether Artifacts should add slots to the Accessories menu,")
                .tooltipLine("and allow artifacts to be equipped in them")
                .requiresRestart().build();

        public final ConfigValue<Boolean> enableCuriosCompat
                = define("enableCuriosCompat", true)
                .tooltipLine("Whether Artifacts should add slots to the Curios menu,")
                .tooltipLine("and allow artifacts to be equipped in them")
                .requiresRestart().build();

        public final ConfigValue<Boolean> enableTrinketsCompat
                = define("enableTrinketsCompat", true)
                .tooltipLine("Whether Artifacts should add slots to the Trinket menu,")
                .tooltipLine("and allow artifacts to be equipped in them")
                .requiresRestart().build();

        public final ConfigValue<Boolean> addFaceSlot
                = define("addFaceSlot", false)
                .tooltipLine("When enabled, adds a separate slot for the Snorkel and Night Vision Goggles")
                .tooltipLine("(Trinkets only, currently not compatible with Curios or Accessories)")
                .requiresRestart().build();

        public final ConfigValue<Boolean> removeSlotRestrictions
                = define("removeSlotRestrictions", false)
                .tooltipLine("When enabled, allows any artifact to be equipped in any slot")
                .tooltipLine("(Requires Curios or Trinkets, currently not compatible with Accessories)")
                .requiresRestart().build();

        private Slots() {
            super("slots");
        }

        @SuppressWarnings("unchecked")
        public Codec<ConfigValue<Boolean>> codec() {
            return StringRepresentable.fromValues(() -> new ConfigValue[]{
                    enableAccessoriesCompat,
                    enableCuriosCompat,
                    enableTrinketsCompat,
                    addFaceSlot,
                    removeSlotRestrictions
            });
        }
    }
}
