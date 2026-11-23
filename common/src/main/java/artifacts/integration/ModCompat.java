package artifacts.integration;

import artifacts.platform.PlatformServices;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ModCompat {

    // Required dependencies
    public static final ModInfo NEOFORGE = new ModInfo("neoforge");

    // Optional dependencies
    public static final ModInfo CLOTH_CONFIG = new ModInfo("cloth-config", "cloth_config");
    public static final ModInfo CCLAYER = new ModInfo("cclayer");
    public static final ModInfo CURIOS = new ModInfo("curios");
    public static final ModInfo TCLAYER = new ModInfo("tclayer");
    public static final ModInfo TRINKETS = new ModInfo("trinkets");
    public static final ModInfo ACCESSORIES = new ModInfo("accessories");

    // Compat & integration
    public static final ModInfo CREEPER_OVERHAUL = new ModInfo("creeperoverhaul");
    public static final ModInfo HARDCORE_REVIVAL = new ModInfo("hardcorerevival");
    public static final ModInfo LOOTR = new ModInfo("lootr");
    // `origins-legacy` is the actual mod id defined in the fabric.mod.json,
    // with `origins` and `origins_legacy` defined as aliases (`origins` still registers as loaded when checked).
    // Origins Legacy is (currently) incompatible with Sinytra Connector
    public static final ModInfo ORIGINS_LEGACY = new ModInfo("origins-legacy");
    // The compatibility code specifically requires the modern Fabric version of Origins.
    // Origins Legacy and Origins (Neoforge) are based on an older version and cause it to crash.
    // Origins is currently incompatible with Sinytra, but might not be in the future
    // TODO:
    //  Compatibility mixins use the raw package name for mod loaded checks, and as a result ignore aliases and supersededBy checks
    //  For Origins (NeoForge) this isn't an issue, since Apoli's code is included in Origins rather than bundled as a separate mod
    //  With Origins Legacy, an error is thrown in the logs for the umbrella mixin
    public static final ModInfo ORIGINS = new ModInfo("origins", ORIGINS_LEGACY, NEOFORGE);
    public static final ModInfo QUARK = new ModInfo("quark");

    public static final class ModInfo {

        private final String modId;
        private final @Nullable String alias;
        private final Set<ModInfo> supersededBy;

        private ModInfo(String modId, ModInfo... supersededBy) {
            this(modId, null, supersededBy);
        }

        private ModInfo(String modId, @Nullable String alias, ModInfo... supersededBy) {
            if (modId.equals(alias)) {
                throw new IllegalArgumentException("Alias '%s' is the same as mod id".formatted(alias));
            }
            this.modId = modId;
            this.alias = alias;
            this.supersededBy = Set.of(supersededBy);
        }

        public String modId() {
            if (alias != null) {
                // Don't want this or id() to get called from data gen
                throw new UnsupportedOperationException("Mod '%s' has multiple mod id's".formatted(modId));
            }
            return modId;
        }

        public boolean isLoaded() {
            for (ModInfo modInfo : supersededBy) {
                if (modInfo.isLoaded()) {
                    return false;
                }
            }

            // Check both id's in case some idiot is running the fabric version of Cloth with Sinytra Connector
            return PlatformServices.getModList().isModLoaded(modId)
                    || alias != null && PlatformServices.getModList().isModLoaded(alias);
        }

        public ResourceLocation id(String path) {
            return ResourceLocation.fromNamespaceAndPath(modId(), path);
        }
    }
}
