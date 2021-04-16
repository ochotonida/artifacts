package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.necklace.CharmOfSinkingModel;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.config.Config;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class CharmOfSinkingItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/charm_of_sinking.png");

    public CharmOfSinkingItem() {
        addListener(EventPriority.HIGH, PlayerEvent.BreakSpeed.class, this::onBreakSpeed, PlayerEvent::getPlayer);
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getPlayer().isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(event.getPlayer())) {
            event.setNewSpeed(event.getNewSpeed() * 5);
        }
    }

    @Override
    public void onEquip(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!Config.isCosmetic(this) && entity instanceof ServerPlayerEntity) {
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
        if (!Config.isCosmetic(this) && entity instanceof ServerPlayerEntity) {
            entity.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        handler.setSinking(false);
                        handler.syncSinking((ServerPlayerEntity) entity);
                    }
            );
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new CharmOfSinkingModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
