package artifacts.item.wearable.hands;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;

public class GoldenHookItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.GOLDEN_HOOK_EXPERIENCE_BONUS.get() <= 0;
    }
}
