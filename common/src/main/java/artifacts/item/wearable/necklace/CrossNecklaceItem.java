package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CrossNecklaceItem extends WearableArtifactItem {

    private boolean canApplyBonus(LivingEntity entity, ItemStack stack) {
        return ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get() > 0
                && !isOnCooldown(entity)
                && stack.getOrCreateTag().getBoolean("CanApplyBonus");
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get() <= 0;
    }

    private static void setCanApplyBonus(ItemStack stack, boolean canApplyBonus) {
        stack.getOrCreateTag().putBoolean("CanApplyBonus", canApplyBonus);
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public void wornTick(LivingEntity entity, ItemStack stack) {
        if (entity.invulnerableTime <= 10) {
            setCanApplyBonus(stack, true);
        } else if (canApplyBonus(entity, stack)) {
            entity.invulnerableTime += Math.min(60 * 20, Math.max(0, ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get()));
            setCanApplyBonus(stack, false);
            addCooldown(entity, ModGameRules.CROSS_NECKLACE_COOLDOWN.get());
        }
    }
}
