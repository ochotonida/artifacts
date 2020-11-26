package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.SteadfastSpikesModel;
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

import java.util.UUID;

public class SteadfastSpikesItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/steadfast_spikes.png");

    private static final AttributeModifier STEADFAST_SPIKES_KNOCKBACK_RESISTANCE = new AttributeModifier(UUID.fromString("2aa3958f-49f5-47ba-a707-a4679ad7ff17"), "artifacts:steadfast_spikes_knockback_resistance", 1, AttributeModifier.Operation.ADDITION);

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(identifier, stack);
        result.put(Attributes.KNOCKBACK_RESISTANCE, STEADFAST_SPIKES_KNOCKBACK_RESISTANCE);
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
