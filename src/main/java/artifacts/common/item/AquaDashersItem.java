package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.feet.AquaDashersModel;
import artifacts.common.capability.swimhandler.ISwimHandler;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

public class AquaDashersItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/aqua_dashers.png");

    public AquaDashersItem() {
        addListener(LivingFluidCollisionEvent.class, this::onFluidCollision);
    }

    private void onFluidCollision(LivingFluidCollisionEvent event, LivingEntity wearer) {
        if (wearer.isSprinting() && wearer.fallDistance < 6 && !wearer.isUsingItem() && !wearer.isCrouching()) {
            wearer.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(handler -> {
                if (!handler.isWet() && !handler.isSwimming()) {
                    event.setResult(Event.Result.ALLOW);
                    if (event.getFluidState().is(FluidTags.LAVA)) {
                        if (!wearer.fireImmune() && !EnchantmentHelper.hasFrostWalker(wearer)) {
                            wearer.hurt(DamageSource.HOT_FLOOR, 1);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (entity.tickCount % 20 == 0 && isSprintingOnFluid(entity)) {
            damageStack(identifier, index, entity, stack);
        }
    }

    public boolean isSprinting(LivingEntity entity) {
        return isEquippedBy(entity)
                && entity.isSprinting()
                && entity.fallDistance < 6
                && !entity.getCapability(SwimHandlerCapability.INSTANCE).map(ISwimHandler::isWet).orElse(true);
    }

    private boolean isSprintingOnFluid(LivingEntity entity) {
        if (isSprinting(entity)) {
            BlockPos pos = new BlockPos(MathHelper.floor(entity.getX()), MathHelper.floor(entity.getY() - 0.2), MathHelper.floor(entity.getZ()));
            return !entity.level.getBlockState(pos).getFluidState().isEmpty();
        }
        return false;
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
