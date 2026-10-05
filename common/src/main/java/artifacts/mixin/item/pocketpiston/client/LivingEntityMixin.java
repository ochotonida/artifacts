package artifacts.mixin.item.pocketpiston.client;

import artifacts.extensions.item.pocketpiston.LivingEntityExtensions;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityExtensions {

    @Unique
    private static final int RETRACTION_DURATION = 2;

    @Unique
    private static final int RETRACTION_DELAY = 4;

    @Unique
    private float artifacts$pocketPistonLength;

    @Unique
    private int artifacts$pocketPistonTimeRemaining;

    @Inject(method = "tick", at = @At("HEAD"))
    private void updatePocketPistonLength(CallbackInfo ci) {
        float d = (artifacts$pocketPistonTimeRemaining < RETRACTION_DURATION ? -1F : 1F) / RETRACTION_DURATION;
        artifacts$pocketPistonLength = Math.clamp(artifacts$pocketPistonLength + d, 0, 1);

        LivingEntity self = (LivingEntity) (Object) this;

        if (self.isSwinging()) {
            artifacts$pocketPistonTimeRemaining = RETRACTION_DELAY + RETRACTION_DURATION;
        }

        if (artifacts$pocketPistonTimeRemaining > 0) {
            artifacts$pocketPistonTimeRemaining -= 1;
        }
    }

    @Unique
    @Override
    public float artifacts$getPocketPistonLength(float partialTicks) {
        float d = (artifacts$pocketPistonTimeRemaining + partialTicks < RETRACTION_DURATION ? -1F : 1F) / RETRACTION_DURATION;
        return Math.clamp(artifacts$pocketPistonLength + d * partialTicks, 0, 1);
    }
}
