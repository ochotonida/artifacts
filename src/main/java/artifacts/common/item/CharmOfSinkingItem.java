package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ShoesModel;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CharmOfSinkingItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/helium_flamingo.png");

    @Override
    public void onEquip(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (entity instanceof ServerPlayerEntity) {
            entity.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        handler.setSinking(true);
                        handler.syncSinking((ServerPlayerEntity) entity);
                    }
            );
        }
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (entity instanceof ServerPlayerEntity) {
            entity.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        handler.setSinking(false);
                        handler.syncSinking((ServerPlayerEntity) entity);
                    }
            );
        }
    }

    @Override
    protected BipedModel<LivingEntity> createModel() {
        return new ShoesModel(1);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
