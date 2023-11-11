package artifacts.forge.event;

import artifacts.item.wearable.belt.CloudInABottleItem;
import artifacts.item.wearable.belt.ObsidianSkullItem;
import artifacts.item.wearable.feet.BunnyHoppersItem;
import artifacts.item.wearable.hands.DiggingClawsItem;
import artifacts.item.wearable.hands.GoldenHookItem;
import artifacts.item.wearable.hands.VampiricGloveItem;
import artifacts.item.wearable.head.DrinkingHatItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class ArtifactEventsForge {

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, ArtifactEventsForge::onLivingDamage);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ArtifactEventsForge::onLivingFall);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onDrinkingHatItemUse);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onGoldenHookExperienceDrop);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onKittySlippersChangeTarget);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onKittySlippersLivingUpdate);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, ArtifactEventsForge::onDiggingClawsBreakSpeed);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventsForge::onDiggingClawsHarvestCheck);
    }

    private static void onLivingDamage(LivingDamageEvent event) {
        VampiricGloveItem.onLivingDamage(event.getEntity(), event.getSource(), event.getAmount());
        ObsidianSkullItem.onLivingDamage(event.getEntity(), event.getSource(), event.getAmount());
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

    private static void onGoldenHookExperienceDrop(LivingExperienceDropEvent event) {
        int originalXp = event.getOriginalExperience();
        int droppedXp = event.getDroppedExperience();
        int modifiedXp = droppedXp + GoldenHookItem.getExperienceBonus(originalXp, event.getEntity(), event.getAttackingPlayer());
        event.setDroppedExperience(modifiedXp);
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
