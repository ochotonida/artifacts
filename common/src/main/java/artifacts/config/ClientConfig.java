package artifacts.config;

import artifacts.config.value.ValueTypes;

import java.util.function.Supplier;

public class ClientConfig extends ConfigManager {

    public final Supplier<Boolean> showArtifactsOnPlayers
            = define("showArtifactsOnPlayers", true)
            .descriptionLine("Whether worn artifacts should be displayed on players")
            .descriptionLine("This config option is purely client-side, it does not change what other players see")
            .build();

    public final Supplier<Boolean> showFirstPersonGloves
            = define("showFirstPersonGloves", true)
            .descriptionLine("Whether models for gloves are shown in first person")
            .build();

    public final Supplier<Boolean> showTooltips
            = define("showTooltips", true)
            .descriptionLine("Whether artifacts have tooltips explaining their effects")
            .build();

    public final Supplier<Boolean> useModdedMimicTextures
            = define("useModdedMimicTextures", true)
            .descriptionLine("Whether mimics can use textures from Quark")
            .build();

    public final Supplier<Boolean> enableCooldownOverlay
            = define("enableCooldownOverlay", true)
            .descriptionLine("Whether artifacts on cooldown should be displayed next to the hotbar")
            .build();

    public final Supplier<Integer> cooldownOverlayOffset
            = define("cooldownOverlayOffset", ValueTypes.INT, 10)
            .descriptionLine("Location of the artifact cooldown gui element")
            .descriptionLine("Distance from the hotbar measured in pixels")
            .descriptionLine("Negative values place the element left of the hotbar")
            .build();

    public final Supplier<Integer> heliumFlamingoOverlayOffset
            = define("heliumFlamingoOverlayOffset", ValueTypes.INT, 0)
            .descriptionLine("Controls the vertical position of the Helium Flamingo's charge meter")
            .build();

    protected ClientConfig() {
        super("client");
    }
}
