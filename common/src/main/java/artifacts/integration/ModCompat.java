package artifacts.integration;

import artifacts.platform.PlatformServices;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModCompat {

    // Required dependencies
    public static final ModInfo EXPANDABILITY = new ModInfo("expandability");

    // Optional dependencies
    public static final ModInfo CLOTH_CONFIG = new ModInfo("cloth-config", "cloth_config");
    public static final ModInfo CCLAYER = new ModInfo("cclayer");
    public static final ModInfo CURIOS = new ModInfo("curios");
    public static final ModInfo TCLAYER = new ModInfo("tclayer");
    public static final ModInfo TRINKETS = new ModInfo("trinkets");
    public static final ModInfo ACCESSORIES = new ModInfo("accessories");

    // Compat & integration
    public static final ModInfo CREEPER_OVERHAUL = new ModInfo("creeperoverhaul");
    public static final ModInfo LOOTR = new ModInfo("lootr");
    public static final ModInfo ORIGINS_LEGACY = new ModInfo("origins-legacy");
    public static final ModInfo ORIGINS = new ModInfo("origins", ORIGINS_LEGACY);
    public static final ModInfo QUARK = new ModInfo("quark");

    public static final class ModInfo {

        private final String modId;
        private final @Nullable String alias;
        private final @Nullable ModInfo supersededBy;

        private ModInfo(String modId) {
            this(modId, null, null);
        }

        private ModInfo(String modId, String alias) {
            this(modId, alias, null);
        }

        private ModInfo(String modId, ModInfo supersededBy) {
            this(modId, null, supersededBy);
        }

        private ModInfo(String modId, @Nullable String alias, @Nullable ModInfo supersededBy) {
            if (modId.equals(alias)) {
                throw new IllegalArgumentException("Alias '%s' is the same as mod id".formatted(alias));
            }
            this.modId = modId;
            this.alias = alias;
            this.supersededBy = supersededBy;
        }

        public String modId() {
            if (alias != null) {
                // Don't want this or id() to get called from data gen
                throw new UnsupportedOperationException("Mod '%s' has multiple mod id's".formatted(modId));
            }
            return modId;
        }

        public boolean isLoaded() {
            if (supersededBy != null && supersededBy.isLoaded()) {
                return false;
            }
            // Check both id's in case some idiot is running the fabric version of Cloth with Sinytra Connector
            return PlatformServices.getModList().isModLoaded(modId)
                    || alias != null && PlatformServices.getModList().isModLoaded(alias);
        }

        public Identifier id(String path) {
            return Identifier.fromNamespaceAndPath(modId(), path);
        }
    }
}
