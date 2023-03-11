package artifacts.common.item.wearable.head;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.wearable.WearableArtifactItem;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class SuperstitiousHatItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get() == 1) {
            tooltip.add(tooltipLine("single_level"));
        } else {
            tooltip.add(tooltipLine("multiple_levels", ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get()));
        }
    }

    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {
        return Math.max(0, ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get());
    }
}
