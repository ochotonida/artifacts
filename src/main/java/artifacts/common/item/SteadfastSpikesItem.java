package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.SteadfastSpikesModel;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.UUID;

public class SteadfastSpikesItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/steadfast_spikes.png");

    private static final AttributeModifier STEADFAST_SPIKES_KNOCKBACK_RESISTANCE = new AttributeModifier(UUID.fromString("2aa3958f-49f5-47ba-a707-a4679ad7ff17"), "artifacts:steadfast_spikes_knockback_resistance", 1, AttributeModifier.Operation.ADDITION);

    public SteadfastSpikesItem() {
        super(new Properties(), "steadfast_spikes");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier) {
                Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(identifier);
                result.put(Attributes.field_233820_c_, STEADFAST_SPIKES_KNOCKBACK_RESISTANCE);
                return result;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected SteadfastSpikesModel getModel() {
                if (model == null) {
                    model = new SteadfastSpikesModel();
                }
                return (SteadfastSpikesModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
