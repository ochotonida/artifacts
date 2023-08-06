package artifacts.item.wearable.hands;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class GoldenHookItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.GOLDEN_HOOK_EXPERIENCE_BONUS.get() <= 0;
    }

    public static int getModifiedExperience(int originalXP, LivingEntity entity, Player attacker) {
        if (ModItems.GOLDEN_HOOK.get().isEquippedBy(attacker)) {
            if (entity instanceof Player) {
                return originalXP; // players shouldn't drop extra XP
            }

            int experienceBonus = (int) (originalXP * ModGameRules.GOLDEN_HOOK_EXPERIENCE_BONUS.get() / 100F);
            return originalXP + Math.max(0, experienceBonus);
        }
        return originalXP;
    }
}
