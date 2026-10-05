package artifacts.mixin.item.umbrella.server;

import artifacts.item.UmbrellaHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {

    @Shadow
    private boolean clientIsFloating;

    @Shadow
    public ServerPlayer player;

    @Inject(method = "handlePlayerPositionChange", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;clientIsFloating:Z", shift = At.Shift.AFTER, opcode = Opcodes.PUTFIELD))
    private void allowUmbrellaFlying(CallbackInfo info) {
        if (UmbrellaHelper.isHoldingUmbrellaUpright(player, false)) {
            clientIsFloating = false;
        }
    }
}
