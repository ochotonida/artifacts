package artifacts.common.item.wearable.head;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.wearable.WearableArtifactItem;

public class VillagerHatItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.VILLAGER_HAT_REPUTATION_BONUS.get() <= 0;
    }
}
