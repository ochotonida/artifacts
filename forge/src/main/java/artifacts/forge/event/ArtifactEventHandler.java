package artifacts.forge.event;

import artifacts.forge.capability.SwimHandler;
import artifacts.item.UmbrellaItem;
import artifacts.item.wearable.hands.DiggingClawsItem;
import artifacts.item.wearable.head.DrinkingHatItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModSoundEvents;
import artifacts.registry.ModTags;
import artifacts.util.DamageSourceHelper;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;

public class ArtifactEventHandler {

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ArtifactEventHandler::onLivingFall);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onDrinkingHatItemUse);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ArtifactEventHandler::onCharmOfSinkingBreakSpeed);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArtifactEventHandler::onVampiricGlovesLivingDamage);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onGoldenHookExperienceDrop);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onKittySlippersChangeTarget);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onKittySlippersLivingUpdate);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onHeliumFlamingoTick);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onAquaDashersFluidCollision);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, ArtifactEventHandler::onDiggingClawsBreakSpeed);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onDiggingClawsHarvestCheck);
        MinecraftForge.EVENT_BUS.addListener(ArtifactEventHandler::onUmbrellaLivingUpdate);
    }

    private static void onLivingFall(LivingFallEvent event) {
        onBunnyHoppersFall(event);
        onCloudInABottleFall(event);
    }

    private static void onBunnyHoppersFall(LivingFallEvent event) {
        if (ModGameRules.BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE.get() && ModItems.BUNNY_HOPPERS.get().isEquippedBy(event.getEntity())) {
            event.setDamageMultiplier(0);
        }
    }

    private static void onCloudInABottleFall(LivingFallEvent event) {
        if (ModGameRules.CLOUD_IN_A_BOTTLE_ENABLED.get() && ModItems.CLOUD_IN_A_BOTTLE.get().isEquippedBy(event.getEntity())) {
            event.setDistance(Math.max(0, event.getDistance() - 3));
        }
    }

    private static void onDrinkingHatItemUse(LivingEntityUseItemEvent.Start event) {
        int newDuration = Math.min(
                getDrinkingHatUseDuration(event, ModItems.PLASTIC_DRINKING_HAT.get()),
                getDrinkingHatUseDuration(event, ModItems.NOVELTY_DRINKING_HAT.get())
        );
        event.setDuration(Math.max(1, newDuration));

    }

    private static int getDrinkingHatUseDuration(LivingEntityUseItemEvent.Start event, DrinkingHatItem drinkingHat) {
        UseAnim action = event.getItem().getUseAnimation();
        if (!drinkingHat.isEquippedBy(event.getEntity()) || action != UseAnim.EAT && action != UseAnim.DRINK) {
            return event.getDuration();
        }
        return (int) (event.getDuration() * Math.min(1, Math.max(0, drinkingHat.getDurationMultiplier(action))));
    }

    // TODO might be better as a mixin
    private static void onCharmOfSinkingBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (
                ModGameRules.CHARM_OF_SINKING_ENABLED.get()
                && ModItems.CHARM_OF_SINKING.get().isEquippedBy(event.getEntity())
                && event.getEntity().isEyeInFluidType(ForgeMod.WATER_TYPE.get())
                && !EnchantmentHelper.hasAquaAffinity(event.getEntity())
        ) {
            event.setNewSpeed(event.getNewSpeed() * 5);
        }
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

    private static void onHeliumFlamingoTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        event.player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                handler -> {
                    int maxFlightTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() * 20);
                    int rechargeTime = Math.max(20, ModGameRules.HELIUM_FLAMINGO_RECHARGE_DURATION.get() * 20);

                    if (handler.isSwimming()) {
                        if (!ModItems.HELIUM_FLAMINGO.get().isEquippedBy(event.player)
                                || handler.getSwimTime() > maxFlightTime
                                || event.player.isInWater() && !event.player.isSwimming() && !SwimHandler.isSinking(event.player)
                                || (!event.player.isInWater() || SwimHandler.isSinking(event.player)) && event.player.onGround()) {
                            handler.setSwimming(false);
                            if (!event.player.onGround() && !event.player.isInWater()) {
                                event.player.playSound(ModSoundEvents.POP.get(), 0.5F, 0.75F);
                            }
                        }

                        if (ModItems.HELIUM_FLAMINGO.get().isEquippedBy(event.player) && !event.player.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
                            if (!event.player.getAbilities().invulnerable) {
                                handler.setSwimTime(handler.getSwimTime() + 1);
                            }
                        }
                    } else if (handler.getSwimTime() < 0) {
                        handler.setSwimTime(
                                handler.getSwimTime() < -rechargeTime
                                        ? -rechargeTime
                                        : handler.getSwimTime() + 1
                        );
                    }
                }
        );
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void onAquaDashersFluidCollision(LivingFluidCollisionEvent event) {
        if (
                ModGameRules.AQUA_DASHERS_ENABLED.get()
                        && ModItems.AQUA_DASHERS.get().isEquippedBy(event.getEntity())
                        && event.getEntity().isSprinting()
                        && event.getEntity().fallDistance < 6
                        && !event.getEntity().isUsingItem()
                        && !event.getEntity().isCrouching()
        ) {
            event.getEntity().getCapability(SwimHandler.CAPABILITY).ifPresent(handler -> {
                if (!handler.isWet() && !handler.isSwimming()) {
                    event.setResult(Event.Result.ALLOW);
                    if (event.getFluidState().is(FluidTags.LAVA)) {
                        if (!event.getEntity().fireImmune() && !EnchantmentHelper.hasFrostWalker(event.getEntity())) {
                            event.getEntity().hurt(event.getEntity().damageSources().hotFloor(), 1);
                        }
                    }
                }
            });
        }
    }

    private static void onDiggingClawsBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (ModItems.DIGGING_CLAWS.get().isEquippedBy(event.getEntity()) && event.getEntity().hasCorrectToolForDrops(event.getState())) {
            float speedBonus = Math.max(0, ModGameRules.DIGGING_CLAWS_DIG_SPEED_BONUS.get() / 10F);
            event.setNewSpeed(event.getNewSpeed() + speedBonus);
        }
    }

    private static void onDiggingClawsHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (ModItems.DIGGING_CLAWS.get().isEquippedBy(event.getEntity()) && !event.canHarvest()) {
            event.setCanHarvest(canDiggingClawsHarvest(event.getTargetBlock()));
        }
    }

    private static boolean canDiggingClawsHarvest(BlockState state) {
        Tier tier = DiggingClawsItem.getToolTier();
        return tier != null
                && TierSortingRegistry.isCorrectTierForDrops(tier, state)
                && state.is(ModTags.MINEABLE_WITH_DIGGING_CLAWS);
    }

    private static void onUmbrellaLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        AttributeInstance gravity = entity.getAttribute(PlatformServices.platformHelper.getEntityGravityAttribute());
        if (gravity != null) {
            boolean isInWater = entity.isInWater() && !SwimHandler.isSinking(entity);
            if (ModGameRules.UMBRELLA_IS_GLIDER.get() && !entity.onGround() && !isInWater && event.getEntity().getDeltaMovement().y < 0 && !entity.hasEffect(MobEffects.SLOW_FALLING)
                    && (entity.getOffhandItem().getItem() == ModItems.UMBRELLA.get()
                    || entity.getMainHandItem().getItem() == ModItems.UMBRELLA.get()) && !(entity.isUsingItem() && !entity.getUseItem().isEmpty() && entity.getUseItem().getItem().getUseAnimation(entity.getUseItem()) == UseAnim.BLOCK)) {
                if (!gravity.hasModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING)) {
                    gravity.addTransientModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING);
                }
                entity.fallDistance = 0;
            } else if (gravity.hasModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING)) {
                gravity.removeModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING);
            }
        }
    }
}
