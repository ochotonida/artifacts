package artifacts.common.item.curio.head;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
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
        addListener(LivingEntityUseItemEvent.Finish.class, this::onItemUseFinish);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        if (ModConfig.client.showTooltips.get() && ModConfig.server != null && !ModConfig.server.isCosmetic(this)) {
            if (this != ModItems.PLASTIC_DRINKING_HAT.get()) {
                tooltip.add(Component.translatable(ModItems.PLASTIC_DRINKING_HAT.get().getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, world, tooltip, flags);
    }

    private void onItemUseStart(LivingEntityUseItemEvent.Start event, LivingEntity wearer) {
        UseAnim action = event.getItem().getUseAnimation();
        double drinkingMultiplier = ModConfig.server.drinkingHats.get(this).drinkingDurationMultiplier.get();
        double eatingMultiplier = ModConfig.server.drinkingHats.get(this).eatingDurationMultiplier.get();
        if (action == UseAnim.DRINK) {
            event.setDuration((int) (event.getDuration() * drinkingMultiplier));
        } else if (action == UseAnim.EAT) {
            event.setDuration((int) (event.getDuration() * eatingMultiplier));
        }
    }

    private void onItemUseFinish(LivingEntityUseItemEvent.Finish event, LivingEntity wearer) {
        UseAnim action = event.getItem().getUseAnimation();
        double drinkingMultiplier = ModConfig.server.drinkingHats.get(this).drinkingDurationMultiplier.get();
        double eatingMultiplier = ModConfig.server.drinkingHats.get(this).eatingDurationMultiplier.get();
        if (action == UseAnim.DRINK && drinkingMultiplier != 1 || action == UseAnim.EAT && eatingMultiplier != 1) {
            damageEquippedStacks(wearer);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }
}
