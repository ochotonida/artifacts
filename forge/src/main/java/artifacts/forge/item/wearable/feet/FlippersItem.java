package artifacts.forge.item.wearable.feet;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.item.wearable.AttributeModifyingItem;
import artifacts.forge.registry.ModGameRules;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class FlippersItem extends AttributeModifyingItem {

    public FlippersItem() {
        super(ForgeMod.SWIM_SPEED.get(), UUID.fromString("83f4e257-cd5c-4a36-ba4b-c052422ce7cf"), ArtifactsForge.id("flippers_swim_speed_bonus").toString());
    }

    @Override
    protected double getAmount() {
        return Math.max(0, ModGameRules.FLIPPERS_SWIM_SPEED_BONUS.get() / 100D);
    }
}
