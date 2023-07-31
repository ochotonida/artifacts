package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;

public class CharmOfSinkingItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.CHARM_OF_SINKING_ENABLED.get();
    }
}
