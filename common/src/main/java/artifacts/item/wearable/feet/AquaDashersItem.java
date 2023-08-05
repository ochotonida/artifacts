package artifacts.item.wearable.feet;

import artifacts.component.SwimData;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;

public class AquaDashersItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.AQUA_DASHERS_ENABLED.get();
    }

    public static boolean onFluidCollision(LivingEntity player, FluidState fluidState) {
        if (canCollideWithFluid(player)) {
            SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
            if (swimData != null && !swimData.isWet() && !swimData.isSwimming()) {
                if (fluidState.is(FluidTags.LAVA) && !player.fireImmune() && !EnchantmentHelper.hasFrostWalker(player)) {
                    player.hurt(player.damageSources().hotFloor(), 1);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean canCollideWithFluid(LivingEntity player) {
        return ModGameRules.AQUA_DASHERS_ENABLED.get()
                && ModItems.AQUA_DASHERS.get().isEquippedBy(player)
                && player.isSprinting()
                && player.fallDistance < 6
                && !player.isUsingItem()
                && !player.isCrouching();
    }
}
