package artifacts.common.item.curio.head;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.CurioItem;

public class VillagerHatItem extends CurioItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.VILLAGER_HAT_REPUTATION_BONUS.get() <= 0;
    }
}
