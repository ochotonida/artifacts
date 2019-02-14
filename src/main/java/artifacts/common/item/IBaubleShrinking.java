package artifacts.common.item;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public interface IBaubleShrinking extends IBauble {

    UUID SHRINK_UUID = UUID.fromString("7a3367b2-0a38-491d-b5c7-338d5d0c1dd4");
    AttributeModifier SHRINK = (new AttributeModifier(SHRINK_UUID, "attack speed boost", -0.5, 2)).setSaved(true);

    default void setShrinkAttributeModifier(EntityLivingBase player, ItemStack stack) {
        if (!(player instanceof EntityPlayer)) {
            return;
        }

        IAttributeInstance attributeHeight = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
        IAttributeInstance attributeWidth = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);

        if (attributeHeight.getModifier(SHRINK_UUID) != null) {
            attributeHeight.removeModifier(SHRINK);
        }
        if (attributeWidth.getModifier(SHRINK_UUID) != null) {
            attributeWidth.removeModifier(SHRINK);
        }

        for (int slot : getBaubleType(stack).getValidSlots()) {
            if (BaublesApi.getBaublesHandler((EntityPlayer) player).getStackInSlot(slot).getItem() instanceof IBaubleShrinking) {
                attributeHeight.applyModifier(SHRINK);
                attributeWidth.applyModifier(SHRINK);
                return;
            }
        }
    }

    @Override
    default void onEquipped(ItemStack stack, EntityLivingBase player) {
        setShrinkAttributeModifier(player, stack);
    }

    @Override
    default void onUnequipped(ItemStack stack, EntityLivingBase player) {
        setShrinkAttributeModifier(player, stack);
    }
}
