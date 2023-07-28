package artifacts.registry;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class ModKeyMappings {

    private static final KeyMapping ACTIVATE_HELIUM_FLAMINGO = createUnboundKeyMapping("artifacts.key.helium_flamingo.activate");
    public static final KeyMapping TOGGLE_NIGHT_VISION_GOGGLES = createUnboundKeyMapping("artifacts.key.night_vision_goggles.toggle");
    public static final KeyMapping TOGGLE_UNIVERSAL_ATTRACTOR = createUnboundKeyMapping("artifacts.key.universal_attractor.toggle");

    private static KeyMapping createUnboundKeyMapping(String name) {
        return new KeyMapping(name, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "artifacts.key_category");
    }

    public static KeyMapping getHeliumFlamingoKey() {
        if (!ACTIVATE_HELIUM_FLAMINGO.isUnbound()) {
            return ACTIVATE_HELIUM_FLAMINGO;
        }
        return Minecraft.getInstance().options.keySprint;
    }

    public static void register() {
        KeyMappingRegistry.register(ACTIVATE_HELIUM_FLAMINGO);
        KeyMappingRegistry.register(TOGGLE_NIGHT_VISION_GOGGLES);
        KeyMappingRegistry.register(TOGGLE_UNIVERSAL_ATTRACTOR);
    }
}
