package artifacts.item.wearable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public abstract class AttributeModifyingItem extends WearableArtifactItem {

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
    public void onEquip(LivingEntity entity, ItemStack originalStack, ItemStack newStack) {
        super.onEquip(entity, originalStack, newStack);
        if (!entity.level().isClientSide()) {
            AttributeInstance attributeInstance = entity.getAttribute(attribute);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(modifierId);
                AttributeModifier modifier = createModifier();
                attributeInstance.addPermanentModifier(modifier);
                onAttributeUpdated(entity);
            }
        }
    }

    @Override
    public void wornTick(LivingEntity entity, ItemStack stack) {
        if (!entity.level().isClientSide()) {
            AttributeInstance attributeInstance = entity.getAttribute(attribute);
            if (attributeInstance != null) {
                AttributeModifier existingModifier = attributeInstance.getModifier(modifierId);
                if (existingModifier == null || existingModifier.getAmount() != getAmount()) {
                    attributeInstance.removeModifier(modifierId);
                    attributeInstance.addPermanentModifier(createModifier());
                    onAttributeUpdated(entity);
                }
            }
        }
    }

    @Override
    public void onUnequip(LivingEntity entity, ItemStack originalStack, ItemStack newStack) {
        if (!entity.level().isClientSide()) {
            AttributeInstance attributeInstance = entity.getAttribute(attribute);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(modifierId);
                onAttributeUpdated(entity);
            }
        }
    }
}
