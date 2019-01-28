package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class BaubleMechanicalGlove extends BaubleBase implements IBaubleAttackSpeedBoost, IBaubleAttackDamageBoost {

    public BaubleMechanicalGlove(String name) {
        super(name, BaubleType.RING);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        super.onEquipped(stack, player);
        IBaubleAttackSpeedBoost.super.onEquipped(stack, player);
        IBaubleAttackDamageBoost.super.onEquipped(stack, player);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        super.onUnequipped(stack, player);
        IBaubleAttackSpeedBoost.super.onUnequipped(stack, player);
        IBaubleAttackDamageBoost.super.onUnequipped(stack, player);
    }
}
