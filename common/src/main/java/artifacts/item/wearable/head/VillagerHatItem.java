package artifacts.item.wearable.head;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;

public class VillagerHatItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.VILLAGER_HAT_REPUTATION_BONUS.get() <= 0;
    }
}
