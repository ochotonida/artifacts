package artifacts.common.item.curio.head;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModGameRules;
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

public class DrinkingHatItem extends CurioItem {

    public DrinkingHatItem() {
        addListener(LivingEntityUseItemEvent.Start.class, this::onItemUseStart);
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
        event.setDuration((int) (event.getDuration() * getDurationMultiplier(this, action)));
    }

    private double getDurationMultiplier(Item drinkingHat, UseAnim action) {
        if (action == UseAnim.DRINK) {
            if (drinkingHat == ModItems.PLASTIC_DRINKING_HAT.get()) {
                return ModGameRules.PLASTIC_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER.get() / 100D;
            }
            return ModGameRules.NOVELTY_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER.get() / 100D;
        } else {
            if (drinkingHat == ModItems.PLASTIC_DRINKING_HAT.get()) {
                return ModGameRules.PLASTIC_DRINKING_HAT_EATING_DURATION_MULTIPLIER.get() / 100D;
            }
            return ModGameRules.NOVELTY_DRINKING_HAT_EATING_DURATION_MULTIPLIER.get() / 100D;
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }
}
