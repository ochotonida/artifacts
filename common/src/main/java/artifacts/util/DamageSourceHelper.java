package artifacts.util;

import artifacts.mixin.accessors.LivingEntityAccessor;
import artifacts.registry.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
        return source.isDirect() && source.is(ModTags.IS_MELEE);
    }

    public static boolean shouldDestroyWornItemsOnDeath(LivingEntity entity) {
        return entity instanceof Mob && !wasLastHurtByPlayer(entity);
    }

    public static boolean wasLastHurtByPlayer(LivingEntity entity) {
        if (entity instanceof LivingEntityAccessor mob) {
            return mob.getLastHurtByPlayerTime() > 0 && mob.getLastHurtByPlayer() != null;
        }
        return false;
    }
}
