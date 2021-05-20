package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
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
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (entity.invulnerableTime <= 10) {
            setCanApplyBonus(stack, true);
        } else {
            if (canApplyBonus(stack)) {
                entity.invulnerableTime += ModConfig.server.crossNecklace.invincibilityBonus.get();
                setCanApplyBonus(stack, false);
                damageStack(identifier, index, entity, stack);
            }
        }
    }
}
