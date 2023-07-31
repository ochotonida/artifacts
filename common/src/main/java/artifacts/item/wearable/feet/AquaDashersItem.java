package artifacts.item.wearable.feet;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;

public class AquaDashersItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.AQUA_DASHERS_ENABLED.get();
    }
}
