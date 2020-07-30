package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.FlippersModel;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.UUID;

public class FlippersItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/flippers.png");

    private static final AttributeModifier FLIPPER_SWIM_SPEED = new AttributeModifier(UUID.fromString("63f1bb32-d301-419b-ab52-5d1af94eed1d"), "artifacts:flipper_swim_speed", 1, AttributeModifier.Operation.ADDITION);

    public FlippersItem() {
        super(new Item.Properties(), "flippers");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier) {
                Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(identifier);
                result.put(ForgeMod.SWIM_SPEED.get(), FLIPPER_SWIM_SPEED);
                return result;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected FlippersModel getModel() {
                if (model == null) {
                    model = new FlippersModel();
                }
                return (FlippersModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
