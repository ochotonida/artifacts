package artifacts.mixin.item.umbrella;

import artifacts.common.item.UmbrellaItem;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraftforge.common.ForgeMod;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetHandler.class)
public abstract class ServerPlayNetHandlerMixin {

    @Shadow
    private boolean clientIsFloating;

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "handleMovePlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/network/play/ServerPlayNetHandler;clientIsFloating:Z", shift = At.Shift.AFTER, opcode = Opcodes.PUTFIELD))
    private void allowUmbrellaFlying(CallbackInfo info) {
        ModifiableAttributeInstance gravity = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null && gravity.hasModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING)) {
            clientIsFloating = false;
        }
    }
}
