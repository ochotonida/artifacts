package artifacts.common.item.curio.head;

import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModKeyMappings;
import artifacts.common.item.curio.MobEffectItem;
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
