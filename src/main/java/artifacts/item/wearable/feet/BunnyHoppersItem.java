package artifacts.item.wearable.feet;

import artifacts.item.wearable.MobEffectItem;
import artifacts.registry.ModGameRules;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;

import java.util.List;

public class BunnyHoppersItem extends MobEffectItem {

    public BunnyHoppersItem() {
        super(MobEffects.JUMP, ModGameRules.BUNNY_HOPPERS_JUMP_BOOST_LEVEL, 40);
        addListener(EventPriority.HIGH, LivingFallEvent.class, this::onLivingFall);
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

    private void onLivingFall(LivingFallEvent event, LivingEntity wearer) {
        if (ModGameRules.BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE.get()) {
            event.setDamageMultiplier(0);
        }
    }
}
