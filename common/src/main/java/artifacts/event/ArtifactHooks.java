package artifacts.event;

import artifacts.attribute.DynamicAttributeModifier;
import artifacts.component.SwimData;
import artifacts.component.ability.EquipmentAbility;
import artifacts.component.ability.PostDamageCooldown;
import artifacts.component.ability.ToolTierUpgrade;
import artifacts.component.ability.mobeffect.AttackEffect;
import artifacts.component.ability.mobeffect.PostDamageEffect;
import artifacts.component.itemdamage.DamageOnBlockMined;
import artifacts.component.itemdamage.DamageOnHurt;
import artifacts.component.itemdamage.DamageOverTime;
import artifacts.equipment.EquipmentSlotManager;
import artifacts.extensions.ability.LivingEntityExtensions;
import artifacts.item.UmbrellaHelper;
import artifacts.mixin.accessors.MobAccessor;
import artifacts.platform.PlatformServices;
import artifacts.registry.*;
import artifacts.util.DamageSourceHelper;
import artifacts.util.ItemDamageUtil;
import be.florens.expandability.api.EventResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ArtifactHooks {

    public static void livingUpdate(LivingEntity entity) {
        if (entity instanceof Player player) {
            SwimData swimData = PlatformServices.getPlatformHelper().getSwimData(entity);
            if (swimData != null) {
                swimData.update(player);
            }
        }
        onItemTick(entity);
        DynamicAttributeModifier.tickModifiers(entity);
        if (!entity.onGround()) {
            UmbrellaHelper.onLivingUpdate(entity);
        }
        DamageOverTime.onLivingUpdate(entity);
    }

    // Called just before the entity's health is modified
    public static void beforeLivingDamaged(LivingEntity entity, DamageSource source, float newHealth) {
        // clamp the entity's new health in case the damage dealt exceeds the remaining hp
        float damageDealt = entity.getHealth() - Math.max(0, newHealth);
        boolean isKillingBlow = newHealth <= 0;

        // abilities & effects
        absorbDamage(entity, source, damageDealt);
        PostDamageEffect.onLivingDamaged(entity, source);
        // item damage
        DamageOnHurt.onLivingDamaged(entity, source);
        damageItemsWhenAttacking(entity, source, isKillingBlow);
        // cooldowns
        PostDamageCooldown.onLivingDamaged(entity, source);
    }

    public static void onItemChanged(LivingEntity entity, ItemStack oldStack, ItemStack newStack) {
        if (entity.level().isClientSide() || ItemStack.matches(oldStack, newStack)) {
            return;
        }

        boolean wasToggledOff = !ItemDamageUtil.isDisabledOrBroken(oldStack)
                && ItemDamageUtil.isDisabledOrBroken(newStack);
        for (ModDataComponents.TickingAbility<?, ?> entry : ModDataComponents.TICKING_ABILITIES) {
            handleUnequip(entry, entity, oldStack, newStack, wasToggledOff);
        }

        updateHasTickingAbilities(entity);
    }

    private static <C, T extends EquipmentAbility> void handleUnequip(
            ModDataComponents.TickingAbility<C, T> tickingAbility,
            LivingEntity entity,
            ItemStack oldStack, ItemStack newStack,
            boolean wasToggledOff
    ) {
        C oldComponent = oldStack.get(tickingAbility.type().get());
        // Item was toggled off, does not have the ability, or the new ability is different
        if (oldComponent != null && (wasToggledOff || !oldComponent.equals(newStack.get(tickingAbility.type().get())))) {
            for (T oldAbility : tickingAbility.type().getEntries(oldComponent)) {
                if (oldAbility.isNonCosmetic()) {
                    tickingAbility.ticker().onUnequip(oldAbility, entity);
                }
            }
        }
    }

    public static void updateHasTickingAbilities(LivingEntity entity) {
        boolean shouldTick = EquipmentSlotManager.reduceEquipment(entity, false, false, false, (hasTickingAbilities, slotAccess) -> {
            for (ModDataComponents.TickingAbility<?, ?> entry : ModDataComponents.TICKING_ABILITIES) {
                // abilities are tracked as ticking even when they're cosmetic,
                // since updating the config does not trigger onItemChanged
                if (slotAccess.get().has(entry.type().get())) {
                    return true;
                }
            }
            return hasTickingAbilities;
        });
        ((LivingEntityExtensions) entity).artifacts$setTickingAbilities(shouldTick);
    }

    public static void onItemTick(LivingEntity entity) {
        // tick players both server- and clientside
        if (!(entity instanceof Player) && !((LivingEntityExtensions) entity).artifacts$hasTickingAbilities()) {
            return;
        }
        for (ModDataComponents.TickingAbility<?, ?> entry : ModDataComponents.TICKING_ABILITIES) {
            onAbilityTick(entry, entity);
        }
    }

    private static <T extends EquipmentAbility> void onAbilityTick(
            ModDataComponents.TickingAbility<?, T> entry,
            LivingEntity entity
    ) {
        entry.type().on(entity)
                .includeInactive()
                .iterate((ability, slot) ->
                        entry.ticker().wornTick(ability, slot, entity, slot.isOnCooldown(entity), slot.isDisabledOrBroken())
                );
    }

    public static void onAttackBurningLivingHurt(LivingEntity entity, DamageSource damageSource) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
        if (attacker != null && DamageSourceHelper.isMeleeAttack(damageSource) && !entity.fireImmune()) {
            int duration = (int) attacker.getAttributeValue(ModAttributes.ATTACK_BURNING_DURATION);
            entity.igniteForSeconds(duration);
        }
    }

    public static void doPostAttackEffects(LivingEntity entity, DamageSource damageSource) {
        ModDataComponents.RETALIATION_EFFECTS.on(entity).iterate((ability, slot) ->
                ability.onLivingHurt(entity, slot, damageSource)
        );

        AttackEffect.onLivingHurt(entity, damageSource);
        onAttackBurningLivingHurt(entity, damageSource);
    }

    public static void onEntityAdded(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            updateHasTickingAbilities(livingEntity);
        }
        if (entity instanceof PathfinderMob creeper && creeper.is(ModTags.CREEPERS)) {
            Predicate<LivingEntity> predicate = target -> ModDataComponents.CREEPER_REPELLENT.on(target).findAny();
            ((MobAccessor) creeper).getGoalSelector().addGoal(3,
                    new AvoidEntityGoal<>(creeper, Player.class, predicate, 6, 1, 1.3, EntitySelector.NO_CREATIVE_OR_SPECTATOR)
            );
        }
    }

    public static void onPlaySoundAtEntity(LivingEntity entity, float volume, float pitch) {
        ModDataComponents.HURT_SOUND.on(entity)
                .includeInactive()
                .filter(component -> component.enabled().get())
                .iterate((component, _) -> entity.playSound(component.soundEvent().value(), volume, pitch));
    }

    public static ItemStack applySmeltOresAbility(ItemStack original, @Nullable Entity entity, @Nullable BlockState state, Consumer<Integer> experienceConsumer) {
        if (entity instanceof LivingEntity livingEntity
                && livingEntity.level() instanceof ServerLevel serverLevel
                && ModDataComponents.AUTO_SMELT.on(livingEntity).findAny()
                && state != null
                && state.is(ModTags.ORES)
        ) {
            if (original.is(ModTags.RAW_MATERIALS)) {
                SingleRecipeInput input = new SingleRecipeInput(original);
                Optional<RecipeHolder<SmeltingRecipe>> recipe = serverLevel
                        .recipeAccess()
                        .getRecipeFor(RecipeType.SMELTING, input, livingEntity.level());
                if (recipe.isPresent()) {
                    ItemStack smeltingResult = recipe.get().value().assemble(input);
                    if (!smeltingResult.isEmpty()) {
                        experienceConsumer.accept(getExperience(recipe.get().value().experience()));
                        return smeltingResult.copyWithCount(smeltingResult.getCount() * original.getCount());
                    }
                }
            }
        }
        return original;
    }

    private static int getExperience(float experience) {
        int amount = Mth.floor(experience);
        if (Math.random() < Mth.frac(experience)) {
            amount++;
        }
        return amount;
    }

    public static void onBlockBroken(LivingEntity entity, BlockState blockState) {
        DamageOnBlockMined.onBlockBroken(entity, blockState);
        ToolTierUpgrade.onBlockBroken(entity, blockState);
    }

    public static int modifyUseDuration(int originalDuration, ItemStack item, LivingEntity entity) {
        if (originalDuration <= 0) {
            return originalDuration;
        }
        if (item.getUseAnimation() == ItemUseAnimation.EAT) {
            return (int) Math.max(1, Math.round(originalDuration / entity.getAttributeValue(ModAttributes.EATING_SPEED)));
        } else if (item.getUseAnimation() == ItemUseAnimation.DRINK) {
            return (int) Math.max(1, Math.round(originalDuration / entity.getAttributeValue(ModAttributes.DRINKING_SPEED)));
        }
        return originalDuration;
    }

    public static int modifyExperience(int originalXp, LivingEntity entity, Player attacker) {
        if (attacker == null || entity instanceof Player || originalXp <= 0) {
            return originalXp;
        }

        double multiplier = attacker.getAttributeValue(ModAttributes.ENTITY_EXPERIENCE);
        int droppedXp = (int) Math.round(originalXp * multiplier);
        return Math.max(0, droppedXp);
    }

    public static void absorbDamage(LivingEntity entity, DamageSource damageSource, float amount) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
        if (attacker != null && DamageSourceHelper.isMeleeAttack(damageSource)) {
            ModDataComponents.DAMAGE_ABSORPTION.on(attacker).iterate((ability, slot) -> {
                double absorptionRatio = ability.absorptionRatio().get();
                double maxHealthAbsorbed = ability.maxDamageAbsorbed().get();

                float damageDealt = Math.min(amount, entity.getHealth());
                float damageAbsorbed = (float) Math.min(maxHealthAbsorbed, absorptionRatio * damageDealt);

                if (damageAbsorbed > 0 && ability.absorptionChance().get() > entity.getRandom().nextDouble()) {
                    attacker.heal(damageAbsorbed);
                    slot.hurtAndBreak(ability.itemDamage().get());
                }
            });
        }
    }

    public static void damageItemsWhenAttacking(LivingEntity target, DamageSource damageSource, boolean isKillingBlow) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
        if (attacker == null) {
            return;
        }
        ModDataComponents.DAMAGE_ON_ATTACK.on(attacker)
                .filter(component -> !component.requireMelee() || DamageSourceHelper.isMeleeAttack(damageSource))
                .filter(component -> !component.requireKill() || isKillingBlow)
                .filter(component -> component.entity().isEmpty() || component.entity().get().contains(target.typeHolder()))
                .iterate((component, slot) -> slot.hurtAndBreak(component.itemDamage().get()));
    }

    public static float getModifiedFriction(float friction, LivingEntity entity, Block block) {
        if (friction > 0.6F && block.defaultBlockState().is(BlockTags.ICE)) {
            double slipperinessReduction = entity.getAttributeValue(ModAttributes.SLIP_RESISTANCE);
            return Mth.lerp(((float) slipperinessReduction), friction, 0.6F);
        }
        return friction;
    }

    public static void applyBoneMealAfterEating(LivingEntity entity, FoodProperties properties) {
        if (!entity.level().isClientSide()
                && ModDataComponents.POST_EATING_PLANT_GROWTH.on(entity).findAny()
                && properties.nutrition() > 0
                && !properties.canAlwaysEat()
                && entity.onGround()
                && entity.getBlockStateOn().is(ModTags.ROOTED_BOOTS_GRASS)
        ) {
            BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), entity.level(), entity.getOnPos());
        }
    }

    public static EventResult onPlayerSwim(Player player) {
        SwimData swimData = PlatformServices.getPlatformHelper().getSwimData(player);
        if (swimData != null) {
            if (swimData.isSwimFlying()) {
                return EventResult.SUCCESS;
            } else if (ModDataComponents.SINKING.on(player).findAny()) {
                return EventResult.FAIL;
            }
        }
        return EventResult.PASS;
    }

    public static boolean onFluidCollision(LivingEntity entity, FluidState fluidState) {
        SwimData swimData = PlatformServices.getPlatformHelper().getSwimData(entity);
        if (swimData == null || swimData.shouldBreakSurfaceTension() || swimData.isSwimFlying()) {
            return false;
        }
        return ModDataComponents.FLUID_COLLISION.on(entity)
                .filter(ability -> ability.matchesFluid(fluidState) && ability.condition().test(entity))
                .findAny();
    }

    public static boolean fart(LivingEntity entity) {
        double chance = entity.getAttributeValue(ModAttributes.FLATULENCE);
        if (!entity.level().isClientSide() && entity.getRandom().nextFloat() < chance) {
            entity.gameEvent(ModGameEvents.FART.holder());
            entity.level().playSound(null, entity, ModSoundEvents.FART.value(), SoundSource.PLAYERS, 1, 0.9F + entity.getRandom().nextFloat() * 0.2F);
            ModDataComponents.DAMAGE_ON_FART.on(entity)
                    .iterate((component, slot) -> slot.hurtAndBreak(component.get()));
            return true;
        }
        return false;
    }

    public static void onItemFished(Player player) {
        ModDataComponents.DAMAGE_ON_ITEM_FISHED.on(player)
                .iterate((component, slot) -> slot.hurtAndBreak(component.get()));
    }

    public static void onJump(LivingEntity entity) {
        ModDataComponents.DAMAGE_ON_JUMP.on(entity)
                .iterate((component, slot) -> slot.hurtAndBreak(component.get()));
    }

    public static void onFall(LivingEntity entity, int damage, double effectiveFallDistance) {
        if (!entity.is(EntityTypeTags.FALL_DAMAGE_IMMUNE) && damage <= 0 && effectiveFallDistance > 3) {
            ModDataComponents.DAMAGE_ON_FALL.on(entity)
                    .iterate((component, slot) -> slot.hurtAndBreak(component.get()));
        }
    }
}
