package artifacts.config;

import artifacts.config.display.SharedConfigLang;
import artifacts.config.value.ConfigValue;
import artifacts.config.value.ValueTypes;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.function.Supplier;

public final class GeneralConfig extends ConfigManager {

    public final Supplier<Double> artifactRarity
            = define("artifactRarity", ValueTypes.NON_NEGATIVE_DOUBLE, 1.0)
            .descriptionLine("Affects how common artifacts are in chests")
            .descriptionLine("Values above 1 will make artifacts rarer, values between 0 and 1 will make artifacts more common")
            .descriptionLine("Doubling this value will make artifacts approximately twice as hard to find, and vice versa")
            .descriptionLine("To prevent artifacts from appearing as chest loot, set this to 10000.")
            .build();

    public final Supplier<Double> entityEquipmentChance
            = define("entityEquipmentChance", ValueTypes.FRACTION, 0.0015D)
            .descriptionLine("The chance that a skeleton, zombie or piglin spawns with an artifact equipped")
            .build();

    public final Supplier<Double> archaeologyChance
            = define("archaeologyChance", ValueTypes.FRACTION, 1 / 16D)
            .descriptionLine("The chance that an artifact generates in suspicious sand or gravel")
            .build();

    public final Campsite campsite = new Campsite();
    public final Durability durability = new Durability();
    public final Slots slots = new Slots();

    GeneralConfig() {
        super("general");
    }

    @SuppressWarnings("unchecked")
    public Codec<ConfigValue<Boolean>> codec() {
        return StringRepresentable.fromValues(() -> new ConfigValue[]{
                slots.enableCuriosCompat,
                slots.enableTrinketsCompat,
                slots.addFaceSlot,
                slots.removeSlotRestrictions,
                durability.allowDurabilityEnchantments
        });
    }

    public final class Campsite extends SubCategory {

        public final ConfigValue<Integer> count
                = define("campsiteCount", ValueTypes.NON_NEGATIVE_INT, 40)
                .descriptionLine("How many times a campsite will attempt to generate per chunk")
                .descriptionLine("Set this to 0 to prevent campsites from generating")
                .build();

        public final ConfigValue<Integer> minY
                = define("minY", ValueTypes.INT, -60)
                .descriptionLine("The minimum height campsites can spawn at")
                .build();

        public final ConfigValue<Integer> maxY
                = define("maxY", ValueTypes.INT, 40)
                .descriptionLine("The maximum height campsites can spawn at")
                .build();

        public final ConfigValue<Double> mimicChance
                = define("mimicChance", ValueTypes.FRACTION, 0.3)
                .descriptionLine("The probability that a campsite has a mimic instead of a chest")
                .build();

        public final ConfigValue<Boolean> useModdedChests
                = define("useModdedChests", true)
                .descriptionLine("Whether to use wooden chests from other mods when generating campsites")
                .build();

        public final ConfigValue<Boolean> allowLightSources
                = define("allowLightSources", true)
                .descriptionLine("Whether campsites can contain blocks that emit light")
                .build();

        public final ConfigValue<Boolean> minimalistCampsites
                = define("minimalistCampsites", false)
                .descriptionLine("Replaces campsites with a single chest/mimic")
                .build();

        private Campsite() {
            super("campsite");
            setTitle("Campsite");
        }

        @SuppressWarnings("unchecked")
        public Codec<ConfigValue<Boolean>> codec() {
            return StringRepresentable.fromValues(() -> new ConfigValue[]{
                    minimalistCampsites
            });
        }
    }

    // TODO: Add config options to spawn items in a damaged state from chests/mimics/etc.
    // TODO: Add config options to spawn items with curse of vanishing & curse of binding
    public final class Durability extends SubCategory {

        public final ConfigValue<Boolean> allowDurabilityEnchantments
                = define("allowDurabilityEnchantments", false)
                .descriptionLine("Allows artifacts to be enchanted with mending and unbreaking when they have durability enabled")
                .descriptionLine("For more fine-grained control, the vanilla minecraft:enchantable/durability item tag should be used")
                .requiresRestart()
                .build();

        private Durability() {
            super("durability");
            setTitle(SharedConfigLang.DURABILITY);
        }
    }

    public final class Slots extends SubCategory {

        public final ConfigValue<Boolean> enableCuriosCompat
                = define("enableCuriosCompat", true)
                .descriptionLine("Whether Artifacts should add slots to the Curios menu,")
                .descriptionLine("and allow artifacts to be equipped in them")
                .requiresRestart().build();

        public final ConfigValue<Boolean> enableTrinketsCompat
                = define("enableTrinketsCompat", true)
                .descriptionLine("Whether Artifacts should add slots to the Trinket menu,")
                .descriptionLine("and allow artifacts to be equipped in them")
                .requiresRestart().build();

        public final ConfigValue<Boolean> addFaceSlot
                = define("addFaceSlot", false)
                .descriptionLine("When enabled, adds a separate slot for the Snorkel and Night Vision Goggles")
                .descriptionLine("(Trinkets only, currently not compatible with Curios)")
                .requiresRestart().build();

        public final ConfigValue<Boolean> removeSlotRestrictions
                = define("removeSlotRestrictions", false)
                .descriptionLine("When enabled, allows any artifact to be equipped in any slot")
                .requiresRestart().build();

        private Slots() {
            super("slots");
            setTitle("Slots");
        }
    }
}
