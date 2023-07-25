package artifacts.config;

import artifacts.Artifacts;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Artifacts.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/mossy_cobblestone.png")
public class ModConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject()
    public Common common = new Common();

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject()
    public Client client = new Client();

    @Config(name = "common")
    public static final class Common implements ConfigData {

        @ConfigEntry.Gui.Tooltip(count = 4)
        @Comment("""
                Affects how common artifacts are in chests
                Values above 1 will make artifacts rarer
                Values between 0 and 1 will make artifacts more common
                Set this to 10000 to remove all artifacts from chest loot
                """)
        double artifactRarity = 1;

        public double getArtifactRarity() {
            return Math.max(0, artifactRarity);
        }

        @ConfigEntry.Gui.Tooltip(count = 2)
        @Comment("""
                The chance everlasting beef drops when a cow
                or mooshroom is killed by a player
                """)
        double everlastingBeefChance = 1 / 500D;

        public double getEverlastingBeefChance() {
            return Math.max(0, everlastingBeefChance);
        }

        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        public Campsite campsite = new Campsite();

        public static final class Campsite implements ConfigData {

            @ConfigEntry.Gui.Tooltip(count = 2)
            @Comment("""
                    How many times a campsite will attempt to generate per chunk
                    Set this to 0 to prevent campsites from generating
                    """)
            public int count = 4;

            public int getCount() {
                return Math.max(0, count);
            }

            @ConfigEntry.Gui.Tooltip()
            @Comment("The minimum height campsites can spawn at")
            public int minY = -60;

            @ConfigEntry.Gui.Tooltip()
            @Comment("The maximum height campsites can spawn at")
            public int maxY = 40;

            @ConfigEntry.Gui.Tooltip
            @Comment("Probability that a campsite has a mimic instead of a chest")
            public double mimicChance = 0.3;

            public double getMimicChance() {
                return Math.max(0, Math.min(1, mimicChance));
            }

            @ConfigEntry.Gui.Tooltip
            @Comment("Whether to use wooden chests from other mods when generating campsites")
            public boolean useModdedChests = true;
        }
    }

    @Config(name = "client")
    public static final class Client implements ConfigData {

        @ConfigEntry.Gui.Tooltip(count = 2)
        @Comment("Whether the Kitty Slippers and Bunny Hoppers change the player's hurt sounds")
        public boolean modifyHurtSounds = true;

        @ConfigEntry.Gui.Tooltip
        @Comment("Whether models for gloves should be shown in first person")
        public boolean showFirstPersonGloves = true;

        @ConfigEntry.Gui.Tooltip
        @Comment("Whether artifacts should have tooltips explaining their effects")
        public boolean showTooltips = true;

        @ConfigEntry.Gui.Tooltip
        @Comment("Whether mimics can use textures from Lootr or Quark")
        public boolean useModdedMimicTextures = true;
    }
}
