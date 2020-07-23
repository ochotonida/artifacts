package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.CrossNecklaceModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CrossNecklaceItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/cross_necklace.png");

    public CrossNecklaceItem() {
        super(new Properties(), "cross_necklace");
    }

    private static boolean canApplyBonus(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("CanApplyBonus");
    }

    private static void setCanApplyBonus(ItemStack stack, boolean canApplyBonus) {
        stack.getOrCreateTag().putBoolean("CanApplyBonus", canApplyBonus);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public void curioTick(String identifier, int index, LivingEntity entity) {
                if (entity.hurtResistantTime <= 10) {
                    setCanApplyBonus(stack, true);
                } else {
                    if (canApplyBonus(stack)) {
                        entity.hurtResistantTime += 20;
                        setCanApplyBonus(stack, false);
                    }
                }
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected CrossNecklaceModel getModel() {
                if (model == null) {
                    model = new CrossNecklaceModel();
                }
                return (CrossNecklaceModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
