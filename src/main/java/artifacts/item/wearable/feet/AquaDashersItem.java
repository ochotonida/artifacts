package artifacts.item.wearable.feet;

import artifacts.capability.SwimHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.Event;

public class AquaDashersItem extends WearableArtifactItem {

    public AquaDashersItem() {
        addListener(LivingFluidCollisionEvent.class, this::onFluidCollision);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.AQUA_DASHERS_ENABLED.get();
    }

    private void onFluidCollision(LivingFluidCollisionEvent event, LivingEntity wearer) {
        if (ModGameRules.AQUA_DASHERS_ENABLED.get() && wearer.isSprinting() && wearer.fallDistance < 6 && !wearer.isUsingItem() && !wearer.isCrouching()) {
            wearer.getCapability(SwimHandler.CAPABILITY).ifPresent(handler -> {
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

    public boolean isSprinting(LivingEntity entity) {
        return isEquippedBy(entity)
                && ModGameRules.AQUA_DASHERS_ENABLED.get()
                && entity.isSprinting()
                && entity.fallDistance < 6
                && !entity.getCapability(SwimHandler.CAPABILITY).map(SwimHandler::isWet).orElse(true);
    }
}
