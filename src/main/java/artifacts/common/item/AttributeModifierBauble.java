package artifacts.common.item;

import artifacts.common.ModConfig;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AttributeModifierBauble extends BaubleBase {

    private final ExtendedAttributeModifier[] attributeModifiers;

    public AttributeModifierBauble(String name, BaubleType type, ExtendedAttributeModifier... attributeModifiers) {
        super(name, type);
        this.attributeModifiers = attributeModifiers;
        System.out.println(UUID.randomUUID() + " eeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        super.onEquipped(stack, player);
        if (player instanceof EntityPlayer && !player.world.isRemote) {
            applyModifiers(null, (EntityPlayer) player);
        }
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        super.onUnequipped(stack, player);
        if (player instanceof EntityPlayer && !player.world.isRemote) {
            applyModifiers(stack, (EntityPlayer) player);
        }
    }

    private void applyModifiers(@Nullable ItemStack excludedStack, EntityPlayer player) {
        IBaublesItemHandler baublesHandler = BaublesApi.getBaublesHandler(player);
        Set<ExtendedAttributeModifier> modifiers = new HashSet<>();

        for (int slot : BaubleType.TRINKET.getValidSlots()) {
            ItemStack stack = baublesHandler.getStackInSlot(slot);
            if (stack.getItem() instanceof AttributeModifierBauble && stack != excludedStack) {
                modifiers.addAll(Arrays.asList(((AttributeModifierBauble) stack.getItem()).attributeModifiers));
            }
        }

        modifiers.retainAll(Arrays.asList(attributeModifiers));

        for (ExtendedAttributeModifier modifier : attributeModifiers) {
            for (IAttribute attribute : modifier.affectedAttributes) {
                IAttributeInstance instance = player.getAttributeMap().getAttributeInstance(attribute);
                if (instance.getModifier(modifier.getID()) != null) {
                    instance.removeModifier(modifier);
                }
            }
        }

        for (ExtendedAttributeModifier modifier : modifiers) {
            for (IAttribute attribute : modifier.affectedAttributes) {
                player.getAttributeMap().getAttributeInstance(attribute).applyModifier(modifier);
            }
        }
    }

    public static class ExtendedAttributeModifier extends AttributeModifier {

        public static ExtendedAttributeModifier ATTACK_DAMAGE = new ExtendedAttributeModifier(UUID.fromString("15fab7b9-5916-460b-a8e8-8434849a0662"), "attack damage boost", ModConfig.attackDamageBoost, 0, SharedMonsterAttributes.ATTACK_DAMAGE);
        public static ExtendedAttributeModifier ATTACK_SPEED = new ExtendedAttributeModifier(UUID.fromString("7a3367b2-0a38-491d-b5c7-338d5d0c1dd4"), "attack speed boost", 1, 2, SharedMonsterAttributes.ATTACK_SPEED);
        public static ExtendedAttributeModifier SHRINKING = new ExtendedAttributeModifier(UUID.fromString("d4203885-1dbe-42ac-8891-30efd5b2ee7e"), "shrinking", -0.5, 2, ArtemisLibAttributes.ENTITY_HEIGHT, ArtemisLibAttributes.ENTITY_WIDTH);

        public final IAttribute[] affectedAttributes;

        public ExtendedAttributeModifier(UUID id, String nameIn, double amountIn, int operationIn, IAttribute... affectedAttributes) {
            super(id, nameIn, amountIn, operationIn);
            this.affectedAttributes = affectedAttributes;
            setSaved(true);
        }
    }
}
