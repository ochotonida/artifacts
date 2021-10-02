package artifacts.mixin.item.villagerhat;

import artifacts.common.init.ModItems;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin {

    @Shadow
    @Nullable
    private Player tradingPlayer;

    @Inject(method = "notifyTrade", at = @At("HEAD"))
    private void damageVillagerHat(CallbackInfo callbackInfo) {
        if (ModItems.VILLAGER_HAT.get().isEquippedBy(tradingPlayer)) {
            ModItems.VILLAGER_HAT.get().damageEquippedStacks(tradingPlayer);
        }
    }
}
