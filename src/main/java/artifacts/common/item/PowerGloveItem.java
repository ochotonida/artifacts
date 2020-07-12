package artifacts.common.item;

import artifacts.Artifacts;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.UUID;

public class PowerGloveItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/power_glove_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/power_glove_slim.png");

    private static final AttributeModifier POWER_GLOVE_ATTACK_DAMAGE = new AttributeModifier(UUID.fromString("15fab7b9-5916-460b-a8e8-8434849a0662"), "artifacts:power_glove_attack_damage", 4, AttributeModifier.Operation.ADDITION).setSaved(false);

    public PowerGloveItem() {
        super(new Properties(), "power_glove");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new GloveCurio(this) {
            @Override
            public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
                Multimap<String, AttributeModifier> result = super.getAttributeModifiers(identifier);
                result.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), POWER_GLOVE_ATTACK_DAMAGE);
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
        });
    }
}
