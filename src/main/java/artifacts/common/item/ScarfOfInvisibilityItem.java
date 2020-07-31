package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ScarfModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ScarfOfInvisibilityItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/scarf_of_invisibility.png");

    public ScarfOfInvisibilityItem() {
        super(new Properties(), "scarf_of_invisibility");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (!livingEntity.world.isRemote && livingEntity.ticksExisted % 15 == 0) {
                    livingEntity.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 39, 0, true, false));
                }
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ScarfModel getModel() {
                if (model == null) {
                    model = new ScarfModel(RenderType::getEntityTranslucent);
                }
                return (ScarfModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
