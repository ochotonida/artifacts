package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CrossNecklaceItem extends CurioItem {

    private boolean canApplyBonus(ItemStack stack) {
        return !ModConfig.server.isCosmetic(this) && stack.getOrCreateTag().getBoolean("CanApplyBonus");
    }

    private static void setCanApplyBonus(ItemStack stack, boolean canApplyBonus) {
        stack.getOrCreateTag().putBoolean("CanApplyBonus", canApplyBonus);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().invulnerableTime <= 10) {
            setCanApplyBonus(stack, true);
        } else if (canApplyBonus(stack)) {
            slotContext.entity().invulnerableTime += ModConfig.server.crossNecklace.invincibilityBonus.get();
            setCanApplyBonus(stack, false);
            damageStack(slotContext, stack);
        }
    }
}
