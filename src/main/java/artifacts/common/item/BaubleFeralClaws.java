package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

public class BaubleFeralClaws extends BaubleBase implements IBaubleAttackSpeedBoost {

    public BaubleFeralClaws() {
        super("feral_claws", BaubleType.RING);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        super.onEquipped(stack, player);
        IBaubleAttackSpeedBoost.super.onEquipped(stack, player);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        super.onUnequipped(stack, player);
        IBaubleAttackSpeedBoost.super.onUnequipped(stack, player);
    }
}
