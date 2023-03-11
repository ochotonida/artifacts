package artifacts.common.item.curio;

import artifacts.Artifacts;
import artifacts.common.init.ModGameRules;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

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
        super.onEquip(slotContext, originalStack, newStack);
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

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltipList, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltipList, flags);
        Set<String> curioTags = CuriosApi.getCuriosHelper().getCurioTags(stack.getItem());
        List<String> slots = new ArrayList<>(curioTags);
        if (Artifacts.CONFIG.client.showTooltips && ModGameRules.isInitialized() && !isCosmetic() && !slots.isEmpty()) {
            tooltipList.add(Component.empty());

            String identifier = slots.contains("curio") ? "curio" : slots.get(0);
            tooltipList.add(Component.translatable("curios.modifiers." + identifier));

            tooltipList.add((Component.translatable(
                    "attribute.modifier.plus." +
                            AttributeModifier.Operation.ADDITION.toValue(),
                    ATTRIBUTE_MODIFIER_FORMAT.format(getAmount()),
                    Component.translatable(attribute.getDescriptionId())))
                    .withStyle(ChatFormatting.BLUE));
        }
    }
}
