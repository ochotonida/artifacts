package artifacts.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class DamageSourceHelper {

    @Nullable
    public static LivingEntity getAttacker(DamageSource source) {
        if (source.getEntity() instanceof LivingEntity entity) {
            return entity;
        }
        return null;
    }

    public static boolean isMeleeAttack(DamageSource source) {
        return !source.isIndirect()
                && (source.is(DamageTypes.MOB_ATTACK)
                || source.is(DamageTypes.PLAYER_ATTACK)
                || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO));
    }
}
