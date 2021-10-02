package artifacts.common.item.curio.head;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
                tooltip.add(new TranslatableComponent(ModItems.PLASTIC_DRINKING_HAT.get().getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, world, tooltip, flags);
    }

    private void onItemUseStart(LivingEntityUseItemEvent.Start event, LivingEntity wearer) {
        if (canApplyEffect(event)) {
            double drinkingDurationMultiplier = ModConfig.server.plasticDrinkingHat.drinkingDurationMultiplier.get();
            event.setDuration((int) (event.getDuration() * drinkingDurationMultiplier));
        }
    }

    private void onItemUseFinish(LivingEntityUseItemEvent.Finish event, LivingEntity wearer) {
        if (canApplyEffect(event)) {
            damageEquippedStacks(wearer);
        }
    }

    private boolean canApplyEffect(LivingEntityUseItemEvent event) {
        UseAnim action = event.getItem().getUseAnimation();
        return action == UseAnim.DRINK || action == UseAnim.EAT && ModConfig.server.plasticDrinkingHat.enableFastEating.get();
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }
}
