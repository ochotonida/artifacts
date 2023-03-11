package artifacts.item.wearable.head;

import artifacts.item.wearable.MobEffectItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModKeyMappings;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.effect.MobEffects;

public class NightVisionGogglesItem extends MobEffectItem {

    public NightVisionGogglesItem() {
        super(MobEffects.NIGHT_VISION, 320, ModGameRules.NIGHT_VISION_GOGGLES_ENABLED);
    }

    @Override
    protected KeyMapping getToggleKey() {
        return ModKeyMappings.TOGGLE_NIGHT_VISION_GOGGLES;
    }

    @Override
    public boolean isToggleable() {
        return true;
    }
}
