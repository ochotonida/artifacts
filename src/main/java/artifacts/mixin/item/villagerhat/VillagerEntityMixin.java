package artifacts.mixin.item.villagerhat;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {

    @ModifyVariable(method = "updateSpecialPrices", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/merchant/villager/VillagerEntity;getPlayerReputation(Lnet/minecraft/entity/player/PlayerEntity;)I"))
    private int increaseReputation(int i, PlayerEntity player) {
        if (ModItems.VILLAGER_HAT.get().isEquippedBy(player)) {
            i += ModConfig.server.villagerHat.reputationBonus.get();
        }
        return i;
    }
}
