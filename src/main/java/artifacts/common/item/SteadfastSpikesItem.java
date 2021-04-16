package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.feet.SteadfastSpikesModel;
import artifacts.common.config.Config;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class SteadfastSpikesItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/steadfast_spikes.png");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        if (!Config.isCosmetic(this)) {
            result.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, new ResourceLocation(Artifacts.MODID, "steadfast_spikes_knockback_resistance").toString(), 1, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new SteadfastSpikesModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
