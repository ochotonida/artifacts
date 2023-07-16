package artifacts.forge.item.wearable.hands;

import artifacts.forge.item.wearable.WearableArtifactItem;
import artifacts.forge.registry.ModGameRules;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

public class GoldenHookItem extends WearableArtifactItem {

    public GoldenHookItem() {
        addListener(LivingExperienceDropEvent.class, this::onLivingExperienceDrop, LivingExperienceDropEvent::getAttackingPlayer);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.GOLDEN_HOOK_EXPERIENCE_BONUS.get() <= 0;
    }

    private void onLivingExperienceDrop(LivingExperienceDropEvent event, LivingEntity wearer) {
        if (event.getEntity() instanceof Player) {
            return; // players shouldn't drop extra XP
        }

        int experienceBonus = (int) (event.getOriginalExperience() * ModGameRules.GOLDEN_HOOK_EXPERIENCE_BONUS.get() / 100F);
        if (experienceBonus > 0) {
            event.setDroppedExperience(event.getDroppedExperience() + experienceBonus);
        }
    }
}
