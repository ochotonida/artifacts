package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ClawsModel;
import artifacts.client.render.model.curio.GloveModel;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class FeralClawsItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/feral_claws_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/feral_claws_default.png");

    public static AttributeModifier FERAL_CLAWS_ATTACK_SPEED = new AttributeModifier(UUID.fromString("7a3367b2-0a38-491d-b5c7-338d5d0c1dd4"), "artifacts:feral_claws_attack_speed", 1, AttributeModifier.Operation.MULTIPLY_TOTAL);

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        result.put(Attributes.ATTACK_SPEED, FERAL_CLAWS_ATTACK_SPEED);
        return result;
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE_DEFAULT;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return TEXTURE_SLIM;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected GloveModel createModel(boolean smallArms) {
        return new ClawsModel(smallArms);
    }
}
