package artifacts.common.item;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public interface IBaubleAttackSpeedBoost extends IBauble {

    UUID ATTACK_SPEED_BOOST_UUID = UUID.fromString("7a3367b2-0a38-491d-b5c7-338d5d0c1dd4");
    AttributeModifier ATTACK_SPEED_BOOST = (new AttributeModifier(ATTACK_SPEED_BOOST_UUID, "attack speed boost", 1, 2)).setSaved(true);

    default void setAttackSpeedBoost(EntityLivingBase player, ItemStack stack) {
        if (!(player instanceof EntityPlayer)) {
            return;
        }

        IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);

        if (attribute.getModifier(ATTACK_SPEED_BOOST_UUID) != null) {
            attribute.removeModifier(ATTACK_SPEED_BOOST);
        }

        for (int slot : getBaubleType(stack).getValidSlots()) {
            if (BaublesApi.getBaublesHandler((EntityPlayer) player).getStackInSlot(slot).getItem() instanceof BaubleFeralClaws) {
                attribute.applyModifier(ATTACK_SPEED_BOOST);
                return;
            }
        }
    }

    @Override
    default void onEquipped(ItemStack stack, EntityLivingBase player) {
        setAttackSpeedBoost(player, stack);
    }

    @Override
    default void onUnequipped(ItemStack stack, EntityLivingBase player) {
        setAttackSpeedBoost(player, stack);
    }
}
