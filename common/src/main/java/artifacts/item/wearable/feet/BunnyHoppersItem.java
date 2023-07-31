package artifacts.item.wearable.feet;

import artifacts.item.wearable.MobEffectItem;
import artifacts.registry.ModGameRules;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class BunnyHoppersItem extends MobEffectItem {

    public BunnyHoppersItem() {
        super(MobEffects.JUMP, ModGameRules.BUNNY_HOPPERS_JUMP_BOOST_LEVEL, 40);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE.get() && super.isCosmetic();
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.BUNNY_HOPPERS_JUMP_BOOST_LEVEL.get() >= 0) {
            tooltip.add(tooltipLine("jump_height"));
        }
        if (ModGameRules.BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE.get()) {
            tooltip.add(tooltipLine("fall_damage"));
        }
    }
}
