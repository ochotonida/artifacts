package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PowerGloveItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/power_glove_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/power_glove_slim.png");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        if (!ModConfig.server.isCosmetic(this)) {
            int attackDamageBonus = ModConfig.server.powerGlove.attackDamageBonus.get();
            result.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, new ResourceLocation(Artifacts.MODID, "power_glove_attack_damage").toString(), attackDamageBonus, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return TEXTURE_SLIM;
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE_DEFAULT;
    }
}
