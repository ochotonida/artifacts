package artifacts.common.item.curio.head;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.function.Supplier;

public class DrinkingHatItem extends CurioItem {

    private final Supplier<Integer> drinkingDurationMultiplier;
    private final Supplier<Integer> eatingDurationMultiplier;

    public DrinkingHatItem(Supplier<Integer> drinkingDurationMultiplier, Supplier<Integer> eatingDurationMultiplier) {
        addListener(LivingEntityUseItemEvent.Start.class, this::onItemUseStart);
        this.drinkingDurationMultiplier = drinkingDurationMultiplier;
        this.eatingDurationMultiplier = eatingDurationMultiplier;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        if (ModConfig.client.showTooltips.get()) {
            if (this != ModItems.PLASTIC_DRINKING_HAT.get()) {
                tooltip.add(Component.translatable(ModItems.PLASTIC_DRINKING_HAT.get().getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, world, tooltip, flags);
    }

    private void onItemUseStart(LivingEntityUseItemEvent.Start event, LivingEntity wearer) {
        UseAnim action = event.getItem().getUseAnimation();
        if (action != UseAnim.EAT && action != UseAnim.DRINK) {
            return;
        }
        event.setDuration((int) (event.getDuration() * Math.max(0, getDurationMultiplier(this, action))));
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
