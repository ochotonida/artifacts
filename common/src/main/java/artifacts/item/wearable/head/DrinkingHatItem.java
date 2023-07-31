package artifacts.item.wearable.head;

import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.UseAnim;

import java.util.List;
import java.util.function.Supplier;

public class DrinkingHatItem extends WearableArtifactItem {

    private final Supplier<Integer> drinkingDurationMultiplier;
    private final Supplier<Integer> eatingDurationMultiplier;
    private final boolean hasSpecialTooltip;

    public DrinkingHatItem(Supplier<Integer> drinkingDurationMultiplier, Supplier<Integer> eatingDurationMultiplier, boolean hasSpecialTooltip) {
        this.drinkingDurationMultiplier = drinkingDurationMultiplier;
        this.eatingDurationMultiplier = eatingDurationMultiplier;
        this.hasSpecialTooltip = hasSpecialTooltip;
    }

    @Override
    protected boolean isCosmetic() {
        return drinkingDurationMultiplier.get() >= 100 && eatingDurationMultiplier.get() >= 100;
    }

    @Override
    protected void addTooltip(List<MutableComponent> tooltip) {
        if (hasSpecialTooltip) {
            tooltip.add(tooltipLine("special").withStyle(ChatFormatting.ITALIC));
            addEffectsTooltip(tooltip);
        } else {
            super.addTooltip(tooltip);
        }
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (drinkingDurationMultiplier.get() < 100) {
            tooltip.add(tooltipLine("drinking"));
        }
        if (eatingDurationMultiplier.get() < 100) {
            tooltip.add(tooltipLine("eating"));
        }
    }

    @Override
    protected String getTooltipItemName() {
        return "drinking_hat";
    }

    public double getDurationMultiplier(UseAnim action) {
        if (action == UseAnim.DRINK) {
            return drinkingDurationMultiplier.get() / 100D;
        } else {
            return eatingDurationMultiplier.get() / 100D;
        }
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BOTTLE_FILL;
    }
}
