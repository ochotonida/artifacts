package artifacts.fabric.mixin.item.wearable.kittyslippers;

import artifacts.registry.ModItems;
import artifacts.registry.ModTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HurtByTargetGoal.class)
public abstract class HurtByTargetGoalMixin extends TargetGoal {

    public HurtByTargetGoalMixin(Mob mob, boolean checkVisibility) {
        super(mob, checkVisibility);
        throw new IllegalStateException();
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void cancelRevenge(CallbackInfoReturnable<Boolean> info) {
        LivingEntity attacker = mob.getLastHurtByMob();
        if (ModTags.isInTag(mob.getType(), ModTags.CREEPERS) && ModItems.KITTY_SLIPPERS.get().isEquippedBy(attacker)) {
            info.setReturnValue(false);
        }
    }
}
