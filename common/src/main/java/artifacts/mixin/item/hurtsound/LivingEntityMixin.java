package artifacts.mixin.item.hurtsound;

import artifacts.event.HurtSoundHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
        throw new IllegalStateException();
    }

    @Shadow
    protected abstract float getSoundVolume();

    @Shadow
    public abstract float getVoicePitch();

    @Inject(method = "playHurtSound", at = @At("HEAD"))
    private void onServerPlayHurtSound(CallbackInfo info) {
        //noinspection ConstantConditions
        HurtSoundHandler.onPlaySoundAtEntity((LivingEntity) (Object) this, this.getSoundVolume(), this.getVoicePitch());
    }
}
