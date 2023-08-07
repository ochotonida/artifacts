package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;

public class CharmOfSinkingItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.CHARM_OF_SINKING_ENABLED.get();
    }

    public static boolean shouldSink(LivingEntity entity) {
        return ModGameRules.CHARM_OF_SINKING_ENABLED.get() && ModItems.CHARM_OF_SINKING.get().isEquippedBy(entity);
    }
}
