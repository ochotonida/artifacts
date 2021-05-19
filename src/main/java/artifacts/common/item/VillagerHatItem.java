package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.head.VillagerHatModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerHatItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/villager_hat.png");

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new VillagerHatModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
