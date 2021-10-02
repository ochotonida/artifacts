package artifacts.common.item.curio.hands;

import artifacts.common.capability.killtracker.EntityKillTrackerCapability;
import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

public class GoldenHookItem extends CurioItem {

    public GoldenHookItem() {
        addListener(LivingExperienceDropEvent.class, this::onLivingExperienceDrop, LivingExperienceDropEvent::getAttackingPlayer);
    }

    private void onLivingExperienceDrop(LivingExperienceDropEvent event, LivingEntity wearer) {
        if (event.getEntityLiving() instanceof Player) {
            return; // players shouldn't drop extra XP
        }

        double killRatio = wearer
                .getCapability(EntityKillTrackerCapability.INSTANCE)
                .map(tracker -> tracker.getKillRatio(event.getEntityLiving().getType()))
                .orElse(0D);

        double minMultiplier = ModConfig.server.goldenHook.minExperienceMultiplier.get();
        double maxMultiplier = ModConfig.server.goldenHook.maxExperienceMultiplier.get();
        double maxKillRatio = ModConfig.server.goldenHook.maximumKillRatio.get();
        double maxExperience = ModConfig.server.goldenHook.maxExperience.get();

        // bonus decreases linearly in relation to the ratio kills of the same type in the list of tracked kills
        // no bonus if more than half of the tracked kills are of the same type
        // maximum bonus is 5 * original XP (give or take a few rounding errors)
        double multiplier = Mth.lerp(Math.max(0, (maxKillRatio - killRatio) / maxKillRatio), minMultiplier, maxMultiplier);
        int experienceBonus = (int) Math.min(maxExperience, multiplier * event.getOriginalExperience());
        event.setDroppedExperience(event.getDroppedExperience() + experienceBonus);

        damageEquippedStacks(wearer);
    }
}
