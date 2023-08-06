package artifacts.forge.event;

import artifacts.item.wearable.belt.CloudInABottleItem;
import artifacts.item.wearable.feet.BunnyHoppersItem;
import artifacts.item.wearable.hands.DiggingClawsItem;
import artifacts.item.wearable.head.DrinkingHatItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModTags;
import artifacts.util.DamageSourceHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class ArtifactEventsForge {

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ArtifactEventsForge::onLivingFall);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onDrinkingHatItemUse);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArtifactEventsForge::onVampiricGlovesLivingDamage);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onGoldenHookExperienceDrop);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onKittySlippersChangeTarget);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onKittySlippersLivingUpdate);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, ArtifactEventsForge::onDiggingClawsBreakSpeed);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onDiggingClawsHarvestCheck);
    }

    private static void onLivingFall(LivingFallEvent event) {
        onBunnyHoppersFall(event);
        onCloudInABottleFall(event);
    }

    private static void onBunnyHoppersFall(LivingFallEvent event) {
        if (BunnyHoppersItem.shouldCancelFallDamage(event.getEntity())) {
            event.setDamageMultiplier(0);
        }
    }

    private static void onCloudInABottleFall(LivingFallEvent event) {
        event.setDistance(CloudInABottleItem.getReducedFallDistance(event.getEntity(), event.getDistance()));
    }

    private static void onDrinkingHatItemUse(LivingEntityUseItemEvent.Start event) {
        event.setDuration(DrinkingHatItem.getDrinkingHatUseDuration(event.getEntity(), event.getItem().getUseAnimation(), event.getDuration()));
    }

    private static void onVampiricGlovesLivingDamage(LivingDamageEvent event) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(event.getSource());
        if (
                attacker != null
                && ModItems.VAMPIRIC_GLOVE.get().isEquippedBy(attacker)
                && DamageSourceHelper.isMeleeAttack(event.getSource())
        ) {
            int maxHealthAbsorbed = ModGameRules.VAMPIRIC_GLOVE_MAX_HEALING_PER_HIT.get();
            float absorptionRatio = ModGameRules.VAMPIRIC_GLOVE_ABSORPTION_RATIO.get() / 100F;

            float damageDealt = Math.min(event.getAmount(), event.getEntity().getHealth());
            float damageAbsorbed = Math.min(maxHealthAbsorbed, absorptionRatio * damageDealt);

            if (damageAbsorbed > 0) {
                attacker.heal(damageAbsorbed);
            }
        }
    }

    private static void onGoldenHookExperienceDrop(LivingExperienceDropEvent event) {
        if (ModItems.GOLDEN_HOOK.get().isEquippedBy(event.getAttackingPlayer())) {
            if (event.getEntity() instanceof Player) {
                return; // players shouldn't drop extra XP
            }

            int experienceBonus = (int) (event.getOriginalExperience() * ModGameRules.GOLDEN_HOOK_EXPERIENCE_BONUS.get() / 100F);
            if (experienceBonus > 0) {
                event.setDroppedExperience(event.getDroppedExperience() + experienceBonus);
            }
        }
    }

    private static void onKittySlippersChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity target = event.getNewTarget();
        if (
                ModGameRules.KITTY_SLIPPERS_ENABLED.get()
                && ModItems.KITTY_SLIPPERS.get().isEquippedBy(target)
                && event.getEntity() instanceof Mob creeper
                && creeper.getType().is(ModTags.CREEPERS)
        ) {
            event.setCanceled(true);
        }
    }

    private static void onKittySlippersLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (
                ModGameRules.KITTY_SLIPPERS_ENABLED.get()
                && ModItems.KITTY_SLIPPERS.get().isEquippedBy(event.getEntity().getLastHurtByMob())
                && event.getEntity().getType().is(ModTags.CREEPERS)
        ) {
            event.getEntity().setLastHurtByMob(null);
        }
    }

    private static void onDiggingClawsBreakSpeed(PlayerEvent.BreakSpeed event) {
        float speedBonus = DiggingClawsItem.getSpeedBonus(event.getEntity(), event.getState());
        if (speedBonus > 0) {
            event.setNewSpeed(event.getNewSpeed() + speedBonus);
        }
    }

    private static void onDiggingClawsHarvestCheck(PlayerEvent.HarvestCheck event) {
        event.setCanHarvest(event.canHarvest() || DiggingClawsItem.canDiggingClawsHarvest(event.getEntity(), event.getTargetBlock()));
    }
}
