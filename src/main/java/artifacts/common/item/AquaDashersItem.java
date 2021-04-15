package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.AquaDashersModel;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

public class AquaDashersItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/aqua_dashers.png");

    public AquaDashersItem() {
        addListener(LivingFluidCollisionEvent.class, this::onFluidCollision);
    }

    public void onFluidCollision(LivingFluidCollisionEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isSprinting() && entity.fallDistance < 6 && !entity.isUsingItem()) {
            event.getEntityLiving().getCapability(SwimHandlerCapability.INSTANCE).ifPresent(handler -> {
                if (!handler.isWet() && !handler.isSwimming()) {
                    event.setResult(Event.Result.ALLOW);
                }
            });
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new AquaDashersModel(1.25F);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
