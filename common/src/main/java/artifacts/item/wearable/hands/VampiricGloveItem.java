package artifacts.item.wearable.hands;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;

public class VampiricGloveItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.VAMPIRIC_GLOVE_ABSORPTION_RATIO.get() <= 0 || ModGameRules.VAMPIRIC_GLOVE_MAX_HEALING_PER_HIT.get() <= 0;
    }
}
