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

public interface IBaubleAttackDamageBoost extends IBauble {

    UUID ATTACK_DAMAGE_BOOST_UUID = UUID.fromString("15fab7b9-5916-460b-a8e8-8434849a0662");
    AttributeModifier ATTACK_DAMAGE_BOOST = (new AttributeModifier(ATTACK_DAMAGE_BOOST_UUID, "attack speed boost", 3, 0)).setSaved(true);

    default void setAttackDamageBoost(EntityLivingBase player, ItemStack stack) {
        System.out.println("x");
        if (!(player instanceof EntityPlayer)) {
            return;
        }

        IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);

        if (attribute.getModifier(ATTACK_DAMAGE_BOOST_UUID) != null) {
            attribute.removeModifier(ATTACK_DAMAGE_BOOST);
        }

        for (int slot : getBaubleType(stack).getValidSlots()) {
            if (BaublesApi.getBaublesHandler((EntityPlayer) player).getStackInSlot(slot).getItem() instanceof IBaubleAttackDamageBoost) {
                attribute.applyModifier(ATTACK_DAMAGE_BOOST);
                return;
            }
        }
    }

    @Override
    default void onEquipped(ItemStack stack, EntityLivingBase player) {
        setAttackDamageBoost(player, stack);
    }

    @Override
    default void onUnequipped(ItemStack stack, EntityLivingBase player) {
        setAttackDamageBoost(player, stack);
    }
}
