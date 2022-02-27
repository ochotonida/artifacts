package artifacts.mixin.item.diggingclaws;

import artifacts.common.init.ModItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInteractionManager.class)
public abstract class PlayerInteractionManagerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void damageDiggingClaws(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            // noinspections ConstantConditions
            ModItems.DIGGING_CLAWS.get().damageEquippedStacks(player);
        }
    }
}
