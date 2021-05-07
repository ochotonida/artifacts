package artifacts.mixin.villagerhat;

import artifacts.common.init.ModItems;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(AbstractVillagerEntity.class)
public abstract class AbstractVillagerEntityMixin {

    @Shadow
    @Nullable
    private PlayerEntity tradingPlayer;

    @Inject(method = "notifyTrade", at = @At("HEAD"))
    private void damageVillagerHat(CallbackInfo callbackInfo) {
        if (ModItems.VILLAGER_HAT.get().isEquippedBy(tradingPlayer)) {
            ModItems.VILLAGER_HAT.get().damageEquippedStacks(tradingPlayer);
        }
    }
}
