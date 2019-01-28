package artifacts.common.item;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class BaubleFeralClaws extends BaubleBase {

    public static final UUID ATTACK_SPEED_BOOST_UUID = UUID.fromString("7a3367b2-0a38-491d-b5c7-338d5d0c1dd4");
    public static final AttributeModifier ATTACK_SPEED_BOOST = (new AttributeModifier(ATTACK_SPEED_BOOST_UUID, "attack speed boost", 1, 2)).setSaved(true);

    public BaubleFeralClaws(String name) {
        super(name, BaubleType.RING);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        super.onEquipped(stack, player);
        setAttackSpeedBoost(player, stack);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        super.onUnequipped(stack, player);
        setAttackSpeedBoost(player, stack);
    }

    private void setAttackSpeedBoost(EntityLivingBase player, ItemStack stack) {
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
}
