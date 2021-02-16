package artifacts.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class DamageSourceHelper {

    @Nullable
    public static LivingEntity getAttacker(DamageSource source) {
        if (source.getTrueSource() instanceof LivingEntity) {
            return (LivingEntity) source.getTrueSource();
        }
        return null;
    }

    public static boolean isMeleeAttack(DamageSource source) {
        return source instanceof EntityDamageSource
                && !(source instanceof IndirectEntityDamageSource)
                && !((EntityDamageSource) source).getIsThornsDamage();
    }
}
