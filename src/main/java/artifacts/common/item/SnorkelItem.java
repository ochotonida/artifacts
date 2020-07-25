package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.SnorkelModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class SnorkelItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/snorkel.png");

    public SnorkelItem() {
        super(new Properties(), "snorkel");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (!livingEntity.world.isRemote && livingEntity.ticksExisted % 15 == 0) {
                    livingEntity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 39, 0, true, false));
                }
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected SnorkelModel getModel() {
                if (model == null) {
                    model = new SnorkelModel();
                }
                return (SnorkelModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
