package artifacts.client;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.network.NetworkHandler;
import artifacts.network.ToggleArtifactPacket;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.KeyMapping;

import java.util.HashMap;
import java.util.Map;

public class ToggleKeyHandler {

    private static final Map<WearableArtifactItem, KeyMapping> TOGGLE_KEY_MAPPINGS = new HashMap<>();

    public static void register() {
        addToggleInputHandler(ModItems.NIGHT_VISION_GOGGLES.get(), ModKeyMappings.TOGGLE_NIGHT_VISION_GOGGLES);
        addToggleInputHandler(ModItems.UNIVERSAL_ATTRACTOR.get(), ModKeyMappings.TOGGLE_UNIVERSAL_ATTRACTOR);
    }

    public static KeyMapping getToggleKey(WearableArtifactItem item) {
        return TOGGLE_KEY_MAPPINGS.get(item);
    }

    private static void addToggleInputHandler(WearableArtifactItem item, KeyMapping toggleKey) {
        TOGGLE_KEY_MAPPINGS.put(item, toggleKey);
        ToggleInputHandler handler = new ToggleInputHandler(item);
        ClientTickEvent.CLIENT_PRE.register(instance -> handler.onClientTick());
    }

    private static class ToggleInputHandler {

        private boolean wasToggleKeyDown;
        private final WearableArtifactItem item;

        public ToggleInputHandler(WearableArtifactItem item) {
            this.item = item;
        }

        public void onClientTick() {
            boolean isToggleKeyDown = getToggleKey(item).isDown();
            if (isToggleKeyDown && !wasToggleKeyDown) {
                NetworkHandler.CHANNEL.sendToServer(new ToggleArtifactPacket(item));
            }
            wasToggleKeyDown = isToggleKeyDown;
        }
    }
}
