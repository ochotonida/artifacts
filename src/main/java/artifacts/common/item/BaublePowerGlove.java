package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class BaublePowerGlove extends BaubleBase implements IBaubleAttackDamageBoost {

    public BaublePowerGlove() {
        super("power_glove", BaubleType.RING);
        System.out.println(UUID.randomUUID());
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
