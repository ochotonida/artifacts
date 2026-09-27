package artifacts.util;

import artifacts.registry.ModDataComponents;
import artifacts.registry.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
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

    public static boolean shouldDestroyWornItemOnDeath(LivingEntity entity, ItemStack stack) {
        return stack.has(ModDataComponents.EQUIPABLE.get())
                && entity instanceof Mob
                && !wasLastHurtByPlayer(entity);
    }

    public static boolean wasLastHurtByPlayer(LivingEntity entity) {
        return entity.getLastHurtByPlayerMemoryTime() > 0 && entity.getLastHurtByPlayer() != null;
    }
}
