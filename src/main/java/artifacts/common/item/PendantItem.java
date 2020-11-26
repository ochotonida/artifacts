package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.PendantModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public abstract class PendantItem extends CurioItem {

    private final ResourceLocation texture;

    public PendantItem(String name) {
        texture = new ResourceLocation(Artifacts.MODID, String.format("textures/entity/curio/%s.png", name));
        MinecraftForge.EVENT_BUS.addListener(this::onLivingHurt);
    }

    public abstract void onLivingHurt(LivingHurtEvent event);

    @Override
    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new PendantModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return texture;
    }
}
