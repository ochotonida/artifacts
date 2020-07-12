package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.CrossNecklaceModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CrossNecklaceItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/cross_necklace.png");

    public CrossNecklaceItem() {
        super(new Properties(), "cross_necklace");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public void onCurioTick(String identifier, int index, LivingEntity entity) {
                if (entity.hurtResistantTime > 10 && entity.ticksExisted % 2 == 0) {
                    entity.hurtResistantTime++;
                }
            }

            @Override
            protected CrossNecklaceModel getModel() {
                if (model == null) {
                    model = new CrossNecklaceModel();
                }
                return (CrossNecklaceModel) model;
            }

            @Override
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
