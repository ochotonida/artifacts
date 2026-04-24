package artifacts.config;

import artifacts.Artifacts;
import artifacts.config.value.ConfigValue;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.function.Supplier;

public class GeneralConfig extends ConfigManager {

    public final Supplier<Double> artifactRarity = defineNonNegativeDouble("artifactRarity", 1.0,
            "Affects how common artifacts are in chests",
            "Values above 1 will make artifacts rarer, values between 0 and 1 will make artifacts more common",
            "Doubling this value will make artifacts approximately twice as hard to find, and vice versa",
            "To prevent artifacts from appearing as chest loot, set this to 10000.");

    public final Supplier<Double> entityEquipmentChance = defineFraction("entityEquipmentChance", 0.0015D,
            "The chance that a skeleton, zombie or piglin spawns with an artifact equipped");
    public final Supplier<Double> archaeologyChance = defineFraction("archaeologyChance", 1 / 16D,
            "The chance that an artifact generates in suspicious sand or gravel");

    public final Campsite campsite = new Campsite();

    public class Campsite {

        public final Supplier<Integer> count = defineNonNegativeInt("campsite.campsiteCount", 40,
                "How many times a campsite will attempt to generate per chunk",
                "Set this to 0 to prevent campsites from generating");
        public final Supplier<Integer> minY = defineInt("campsite.minY", -60,
                "The minimum height campsites can spawn at");
        public final Supplier<Integer> maxY = defineInt("campsite.maxY", 40,
                "The maximum height campsites can spawn at");
        public final Supplier<Double> mimicChance = defineFraction("campsite.mimicChance", 0.3,
                "The probability that a campsite has a mimic instead of a chest");
        public final Supplier<Boolean> useModdedChests = defineBool("campsite.useModdedChests", true, false,
                "Whether to use wooden chests from other mods when generating campsites");
        public final Supplier<Boolean> allowLightSources = defineBool("campsite.allowLightSources", true, false,
                "Whether campsites can contain blocks that emit light");
        public final ConfigValue<Boolean> minimalistCampsites = defineBool("campsite.minimalistCampsites", false, false,
                "Replaces campsites with a single chest/mimic"); // TODO implement

        @SuppressWarnings("unchecked")
        public Codec<ConfigValue<Boolean>> codec() {
            return StringRepresentable.fromValues(() -> new ConfigValue[]{
                    Artifacts.CONFIG.general.campsite.minimalistCampsites
            });
        }
    }

    public final Slots slots = new Slots();

    // FIXME: These don't work in dev
    public class Slots {

        public final ConfigValue<Boolean> enableAccessoriesCompat = defineBool("slots.enableAccessoriesCompat", true, true,
                "Whether Artifacts should add slots to the Accessories menu,",
                "and allow artifacts to be equipped in them");
        public final ConfigValue<Boolean> enableCuriosCompat = defineBool("slots.enableCuriosCompat", true, true,
                "Whether Artifacts should add slots to the Curios menu,",
                "and allow artifacts to be equipped in them");
        public final ConfigValue<Boolean> enableTrinketsCompat = defineBool("slots.enableTrinketsCompat", true, true,
                "Whether Artifacts should add slots to the Trinket menu,",
                "and allow artifacts to be equipped in them");
        public final ConfigValue<Boolean> addFaceSlot = defineBool("slots.addFaceSlot", false, true,
                "When enabled, adds a separate slot for the Snorkel and Night Vision Goggles",
                "(Trinkets only, currently not compatible with Curios or Accessories)");
        public final ConfigValue<Boolean> removeSlotRestrictions = defineBool("slots.removeSlotRestrictions", false, true,
                "When enabled, allows any artifact to be equipped in any slot",
                "(Requires Curios or Trinkets, currently not compatible with Accessories)");

        @SuppressWarnings("unchecked")
        public Codec<ConfigValue<Boolean>> codec() {
            return StringRepresentable.fromValues(() -> new ConfigValue[]{
                    Artifacts.CONFIG.general.slots.enableAccessoriesCompat,
                    Artifacts.CONFIG.general.slots.enableCuriosCompat,
                    Artifacts.CONFIG.general.slots.enableTrinketsCompat,
                    Artifacts.CONFIG.general.slots.addFaceSlot,
                    Artifacts.CONFIG.general.slots.removeSlotRestrictions
            });
        }
    }

    protected GeneralConfig() {
        super("general");
    }
}
