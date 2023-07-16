package artifacts.forge.item.wearable.head;

import artifacts.forge.item.wearable.WearableArtifactItem;
import artifacts.forge.registry.ModGameRules;

public class VillagerHatItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.VILLAGER_HAT_REPUTATION_BONUS.get() <= 0;
    }
}
