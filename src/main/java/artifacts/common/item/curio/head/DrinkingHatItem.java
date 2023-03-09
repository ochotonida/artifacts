package artifacts.common.item.curio.head;

import artifacts.common.item.curio.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DrinkingHatItem extends CurioItem {

    private final Supplier<Integer> drinkingDurationMultiplier;
    private final Supplier<Integer> eatingDurationMultiplier;
    private final boolean hasSpecialTooltip;

    public DrinkingHatItem(Supplier<Integer> drinkingDurationMultiplier, Supplier<Integer> eatingDurationMultiplier, boolean hasSpecialTooltip) {
        addListener(LivingEntityUseItemEvent.Start.class, this::onItemUseStart);
        this.drinkingDurationMultiplier = drinkingDurationMultiplier;
        this.eatingDurationMultiplier = eatingDurationMultiplier;
        this.hasSpecialTooltip = hasSpecialTooltip;
    }

    @Override
    protected boolean isCosmetic() {
        return drinkingDurationMultiplier.get() == 100 && eatingDurationMultiplier.get() == 100;
    }

    @Override
    protected void addTooltip(Consumer<MutableComponent> tooltip) {
        if (hasSpecialTooltip) {
            tooltip.accept(tooltipLine("special").withStyle(ChatFormatting.ITALIC));
            addEffectsTooltip(tooltip);
        } else {
            super.addTooltip(tooltip);
        }
    }

    @Override
    protected void addEffectsTooltip(Consumer<MutableComponent> tooltip) {
        if (drinkingDurationMultiplier.get() != 100) {
            tooltip.accept(tooltipLine("drinking"));
        }
        if (eatingDurationMultiplier.get() != 100) {
            tooltip.accept(tooltipLine("eating"));
        }
    }

    @Override
    protected String getTooltipItemName() {
        return "drinking_hat";
    }

    private void onItemUseStart(LivingEntityUseItemEvent.Start event, LivingEntity wearer) {
        UseAnim action = event.getItem().getUseAnimation();
        if (action != UseAnim.EAT && action != UseAnim.DRINK) {
            return;
        }
        int newDuration = (int) (event.getDuration() * Math.max(0, getDurationMultiplier(this, action)));
        event.setDuration(Math.max(1, newDuration));
    }

    private double getDurationMultiplier(Item drinkingHat, UseAnim action) {
        if (action == UseAnim.DRINK) {
            return drinkingDurationMultiplier.get() / 100D;
        } else {
            return eatingDurationMultiplier.get() / 100D;
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }
}
