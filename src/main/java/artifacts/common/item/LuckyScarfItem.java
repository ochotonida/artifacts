package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ScarfModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class LuckyScarfItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/lucky_scarf.png");

    public LuckyScarfItem() {
        super(new Properties(), "lucky_scarf");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ScarfModel getModel() {
                if (model == null) {
                    model = new ScarfModel();
                }
                return (ScarfModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }

            @Override
            public int getFortuneBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index) {
                return 1;
            }
        });
    }
}
