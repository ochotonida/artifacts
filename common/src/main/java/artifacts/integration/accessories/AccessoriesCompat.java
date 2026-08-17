package artifacts.integration.accessories;

import artifacts.equipment.EquipmentSlotManager;
import artifacts.event.ArtifactHooks;
import artifacts.integration.ModCompat;
import artifacts.item.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.events.AccessoryChangeCallback;

public class AccessoriesCompat {

    public static void setup() {
        if (ModCompat.ACCESSORIES.isLoaded()) {
            EquipmentSlotManager.register(new AccessoriesSlotProvider());
        }
        PlatformServices.getPlatformHelper().addItemRegistryCallback(item -> {
            if (item instanceof WearableArtifactItem wearableArtifactItem) {
                AccessoriesAPI.registerAccessory(item, new WearableArtifactAccessory(wearableArtifactItem));
            }
        });

        AccessoryChangeCallback.EVENT.register(
                (prevStack, currentStack, slotReference, slotStateChange) -> ArtifactHooks.onItemChanged(slotReference.entity(), prevStack, currentStack)
        );
    }
}
