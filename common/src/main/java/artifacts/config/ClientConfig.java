package artifacts.config;

import artifacts.config.value.ValueTypes;

import java.util.function.Supplier;

public class ClientConfig extends ConfigManager {

    public final Supplier<Boolean> showFirstPersonGloves
            = define("showFirstPersonGloves", true)
            .tooltipLine("Whether models for gloves are shown in first person")
            .build();

    public final Supplier<Boolean> showTooltips
            = define("showTooltips", true)
            .tooltipLine("Whether artifacts have tooltips explaining their effects")
            .build();

    public final Supplier<Boolean> useModdedMimicTextures
            = define("useModdedMimicTextures", true)
            .tooltipLine("Whether mimics can use textures from Lootr or Quark")
            .build();

    public final Supplier<Boolean> enableCooldownOverlay
            = define("enableCooldownOverlay", true)
            .tooltipLine("Whether artifacts on cooldown should be displayed next to the hotbar")
            .build();

    public final Supplier<Integer> cooldownOverlayOffset
            = define("cooldownOverlayOffset", ValueTypes.INT, 10)
            .tooltipLine("Location of the artifact cooldown gui element")
            .tooltipLine("Distance from the hotbar measured in pixels")
            .tooltipLine("Negative values place the element left of the hotbar")
            .build();

    public final Supplier<Integer> heliumFlamingoOverlayOffset
            = define("heliumFlamingoOverlayOffset", ValueTypes.INT, 0)
            .tooltipLine("Controls the vertical position of the Helium Flamingo's charge meter")
            .build();

    protected ClientConfig() {
        super("client");
    }
}
