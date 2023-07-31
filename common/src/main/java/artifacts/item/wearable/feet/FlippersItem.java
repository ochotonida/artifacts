package artifacts.item.wearable.feet;

import artifacts.Artifacts;
import artifacts.item.wearable.AttributeModifyingItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;

import java.util.UUID;

public class FlippersItem extends AttributeModifyingItem {

    public FlippersItem() {
        super(PlatformServices.platformHelper.getSwimSpeedAttribute(), UUID.fromString("83f4e257-cd5c-4a36-ba4b-c052422ce7cf"), Artifacts.id("flippers_swim_speed_bonus").toString());
    }

    @Override
    protected double getAmount() {
        return Math.max(0, ModGameRules.FLIPPERS_SWIM_SPEED_BONUS.get() / 100D);
    }
}
