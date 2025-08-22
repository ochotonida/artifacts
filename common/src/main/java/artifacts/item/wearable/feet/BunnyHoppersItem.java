package artifacts.item.wearable.feet;

import artifacts.item.wearable.MobEffectItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BunnyHoppersItem extends MobEffectItem {

    public BunnyHoppersItem() {
        super(MobEffects.JUMP, ModGameRules.BUNNY_HOPPERS_JUMP_BOOST_LEVEL, 40);
    }

    @Override
    public boolean hasNonCosmeticEffects() {
        return super.hasNonCosmeticEffects() || ModGameRules.BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE.get();
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (ModGameRules.BUNNY_HOPPERS_JUMP_BOOST_LEVEL.get() != 0) {
            tooltip.add(tooltipLine("jump_height"));
        }
        if (ModGameRules.BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE.get()) {
            tooltip.add(tooltipLine("fall_damage"));
        }
    }

    public static boolean shouldCancelFallDamage(LivingEntity entity) {
        return ModGameRules.BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE.get() && ModItems.BUNNY_HOPPERS.get().isEquippedBy(entity);
    }
}
