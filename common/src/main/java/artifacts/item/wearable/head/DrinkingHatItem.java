package artifacts.item.wearable.head;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
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
        double multiplier;
        if (action == UseAnim.DRINK) {
            multiplier = drinkingDurationMultiplier.get() / 100D;
        } else {
            multiplier = eatingDurationMultiplier.get() / 100D;
        }
        return Math.min(1, Math.max(0, multiplier));
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BOTTLE_FILL;
    }

    public static int getDrinkingHatUseDuration(LivingEntity entity, UseAnim action, int duration) {
        int newDuration = Math.min(
                getDrinkingHatUseDuration(entity, action, duration, ModItems.PLASTIC_DRINKING_HAT.get()),
                getDrinkingHatUseDuration(entity, action, duration, ModItems.NOVELTY_DRINKING_HAT.get())
        );
        return Math.max(1, newDuration);
    }

    private static int getDrinkingHatUseDuration(LivingEntity entity, UseAnim action, int duration, DrinkingHatItem drinkingHat) {
        if (!drinkingHat.isEquippedBy(entity) || action != UseAnim.EAT && action != UseAnim.DRINK) {
            return duration;
        }
        return (int) (duration * drinkingHat.getDurationMultiplier(action));
    }
}
