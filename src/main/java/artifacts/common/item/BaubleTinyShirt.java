package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

public class BaubleTinyShirt extends BaubleBase implements IBaubleShrinking {

    public BaubleTinyShirt() {
        super("tiny_shirt", BaubleType.BODY);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        super.onEquipped(stack, player);
        IBaubleShrinking.super.onEquipped(stack, player);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        super.onUnequipped(stack, player);
        IBaubleShrinking.super.onUnequipped(stack, player);
    }
}
