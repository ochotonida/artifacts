package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

public class BaublePowerGlove extends BaubleBase implements IBaubleAttackDamageBoost {

    public BaublePowerGlove() {
        super("power_glove", BaubleType.RING);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        super.onEquipped(stack, player);
        IBaubleAttackDamageBoost.super.onEquipped(stack, player);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        super.onUnequipped(stack, player);
        IBaubleAttackDamageBoost.super.onUnequipped(stack, player);
    }
}
