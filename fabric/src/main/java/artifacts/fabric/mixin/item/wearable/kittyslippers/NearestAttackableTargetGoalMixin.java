package artifacts.fabric.mixin.item.wearable.kittyslippers;

import artifacts.registry.ModItems;
import artifacts.registry.ModTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetGoalMixin<T extends LivingEntity> extends TargetGoal {

    @Unique
    private static final Predicate<LivingEntity> NOT_WEARING_KITTY_SLIPPERS = entity -> !ModItems.KITTY_SLIPPERS.get().isEquippedBy(entity);

    @Shadow
    @Final
    protected Class<T> targetType;

    public NearestAttackableTargetGoalMixin(Mob mob, boolean checkVisibility) {
        super(mob, checkVisibility);
        throw new IllegalStateException();
    }

    @ModifyArg(method = "<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;selector(Ljava/util/function/Predicate;)Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;"))
    private @Nullable Predicate<LivingEntity> addCreeperTargetPredicate(@Nullable Predicate<LivingEntity> targetPredicate) {
        if (ModTags.isInTag(mob.getType(), ModTags.CREEPERS) && this.targetType == Player.class) {
            return targetPredicate == null ? NOT_WEARING_KITTY_SLIPPERS : targetPredicate.and(NOT_WEARING_KITTY_SLIPPERS);
        }
        return targetPredicate;
    }
}
