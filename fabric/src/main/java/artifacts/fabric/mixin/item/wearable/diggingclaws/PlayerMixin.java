package artifacts.fabric.mixin.item.wearable.diggingclaws;

import artifacts.item.wearable.hands.DiggingClawsItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
        throw new IllegalStateException();
    }

    @Inject(method = "hasCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void increaseBaseToolTier(BlockState state, CallbackInfoReturnable<Boolean> info) {
        if (DiggingClawsItem.canDiggingClawsHarvest(this, state)) {
            info.setReturnValue(true);
        }
    }

    /**
     * This is identical to the forge version but might not be ideal
     * It adds the speed after the vanilla method does all the calculations on the base modifier such as haste and underwater
     */
    // TODO: identical artifacts-forge behaviour but could do this on the speed multiplier instead of end result
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void increaseMiningSpeed(BlockState state, CallbackInfoReturnable<Float> info) {
        info.setReturnValue(info.getReturnValueF() + DiggingClawsItem.getSpeedBonus((Player) (Object) this, state));
    }
}
