package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.VillagerHatModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class VillagerHatItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/villager_hat.png");

    public VillagerHatItem() {
        super(new Properties(), "villager_hat");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            @OnlyIn(Dist.CLIENT)
            protected VillagerHatModel getModel() {
                if (model == null) {
                    model = new VillagerHatModel();
                }
                return (VillagerHatModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }

            @Override
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (!livingEntity.world.isRemote && livingEntity.ticksExisted % 15 == 0) {
                    livingEntity.addPotionEffect(new EffectInstance(Effects.HERO_OF_THE_VILLAGE, 39, 1, true, false));
                }
            }
        });
    }
}
