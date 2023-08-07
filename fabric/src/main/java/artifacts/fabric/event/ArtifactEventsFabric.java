package artifacts.fabric.event;

import artifacts.item.wearable.hands.DiggingClawsItem;
import io.github.fabricators_of_create.porting_lib.event.common.PlayerEvents;

public class ArtifactEventsFabric {

    public static void register() {
        PlayerEvents.BREAK_SPEED.register(ArtifactEventsFabric::onDiggingClawsBreakSpeed);
    }

    private static void onDiggingClawsBreakSpeed(PlayerEvents.BreakSpeed breakSpeed) {
        breakSpeed.setNewSpeed(breakSpeed.getNewSpeed() + DiggingClawsItem.getSpeedBonus(breakSpeed.getPlayer(), breakSpeed.getState()));
    }
}
