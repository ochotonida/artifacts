package artifacts.mixin.item.diggingclaws;

import artifacts.common.init.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {

    @Shadow @Final protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void damageDiggingClaws(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            // noinspections ConstantConditions
            ModItems.DIGGING_CLAWS.get().damageEquippedStacks(player);
        }
    }
}
