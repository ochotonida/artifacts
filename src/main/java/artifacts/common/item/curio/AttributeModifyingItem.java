package artifacts.common.item.curio;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public abstract class AttributeModifyingItem extends CurioItem {

    private final Attribute attribute;
    protected final UUID modifierId;
    protected final String modifierName;

    public AttributeModifyingItem(Attribute attribute, UUID modifierId, String modifierName) {
        this.attribute = attribute;
        this.modifierId = modifierId;
        this.modifierName = modifierName;
    }

    @Override
    protected boolean isCosmetic() {
        return getAmount() <= 0;
    }

    private AttributeModifier createModifier() {
        return new AttributeModifier(modifierId, modifierName, getAmount(), AttributeModifier.Operation.ADDITION);
    }

    protected abstract double getAmount();

    protected void onAttributeUpdated(LivingEntity entity) {

    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!slotContext.entity().level.isClientSide()) {
            AttributeInstance attributeInstance = slotContext.entity().getAttribute(attribute);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(modifierId);
                AttributeModifier modifier = createModifier();
                attributeInstance.addPermanentModifier(modifier);
                onAttributeUpdated(slotContext.entity());
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!slotContext.entity().level.isClientSide()) {
            AttributeInstance attributeInstance = slotContext.entity().getAttribute(attribute);
            if (attributeInstance != null) {
                AttributeModifier existingModifier = attributeInstance.getModifier(modifierId);
                if (existingModifier == null || existingModifier.getAmount() != getAmount()) {
                    attributeInstance.removeModifier(modifierId);
                    attributeInstance.addPermanentModifier(createModifier());
                    onAttributeUpdated(slotContext.entity());
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!slotContext.entity().level.isClientSide()) {
            AttributeInstance attributeInstance = slotContext.entity().getAttribute(attribute);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(modifierId);
                onAttributeUpdated(slotContext.entity());
            }
        }
    }
}
