package artifacts.attribute;

import artifacts.Artifacts;
import artifacts.item.UmbrellaItem;
import artifacts.mixin.accessors.EntityAccessor;
import artifacts.registry.ModAttributes;
import artifacts.registry.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public abstract class DynamicAttributeModifier {

    private static final DynamicAttributeModifier MOUNT_SPEED = new DynamicAttributeModifier(
            Artifacts.id("mount_speed"),
            Attributes.MOVEMENT_SPEED,
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    ) {
        @Override
        protected boolean shouldApply(LivingEntity entity) {
            return entity.getControllingPassenger() != null
                    && !hasDefaultAttributeValue(entity.getControllingPassenger(), ModAttributes.MOUNT_SPEED);
        }

        @Override
        protected double getAmount(LivingEntity entity) {
            return Objects.requireNonNull(entity.getControllingPassenger()).getAttributeValue(ModAttributes.MOUNT_SPEED) - 1;
        }
    };

    private static final DynamicAttributeModifier SPRINTING_SPEED = new DynamicAttributeModifier(
            Artifacts.id("sprinting_speed"),
            Attributes.MOVEMENT_SPEED,
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    ) {
        @Override
        protected boolean shouldApply(LivingEntity entity) {
            return entity.isSprinting()
                    // Punching an entity while sprinting and holding the sprint key causes a vanilla desync bug (in 1.21.1 at least)
                    // Adding an attribute modifier forces the server to sync the movement speed attribute,
                    // this puts the client in a state where the player is sprinting but doesn't have the additional movement speed
                    // This line prevents that bug from being triggered except when needed
                    && !hasDefaultAttributeValue(entity, ModAttributes.SPRINTING_SPEED);
        }

        @Override
        protected double getAmount(LivingEntity entity) {
            return entity.getAttributeValue(ModAttributes.SPRINTING_SPEED) - 1;
        }
    };

    private static final DynamicAttributeModifier SPRINTING_STEP_HEIGHT = new DynamicAttributeModifier(
            Artifacts.id("sprinting_step_height"),
            Attributes.STEP_HEIGHT,
            AttributeModifier.Operation.ADD_VALUE
    ) {
        @Override
        protected boolean shouldApply(LivingEntity entity) {
            return entity.isSprinting() && !hasDefaultAttributeValue(entity, ModAttributes.SPRINTING_STEP_HEIGHT);
        }

        @Override
        protected double getAmount(LivingEntity entity) {
            return entity.getAttributeValue(ModAttributes.SPRINTING_STEP_HEIGHT);
        }
    };

    private static final DynamicAttributeModifier UMBRELLA_GRAVITY = new DynamicAttributeModifier(
            Artifacts.id("umbrella_gravity"),
            Attributes.GRAVITY,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ) {
        @Override
        protected boolean shouldApply(LivingEntity entity) {
            return UmbrellaItem.shouldGlide(entity);
        }

        @Override
        protected double getAmount(LivingEntity entity) {
            return -0.875D;
        }
    };

    private static final DynamicAttributeModifier MOVEMENT_SPEED_ON_SNOW = new DynamicAttributeModifier(
            Artifacts.id("movement_speed_on_snow"),
            Attributes.MOVEMENT_SPEED,
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    ) {
        @Override
        protected boolean shouldApply(LivingEntity entity) {
            if (entity.onGround() && entity instanceof EntityAccessor entityAccessor) {
                if (!hasDefaultAttributeValue(entity, ModAttributes.MOVEMENT_SPEED_ON_SNOW)) {
                    BlockState onState = entity.level().getBlockState(entityAccessor.callGetBlockPosBelowThatAffectsMyMovement());
                    if (onState.is(BlockTags.SNOW)) {
                        return true;
                    } else {
                        BlockState aboveState = entity.level().getBlockState(entityAccessor.callGetBlockPosBelowThatAffectsMyMovement().above());
                        return !aboveState.isAir() && aboveState.is(ModTags.SNOW_LAYERS);
                    }
                }
            }
            return false;
        }

        @Override
        protected double getAmount(LivingEntity entity) {
            return entity.getAttributeValue(ModAttributes.MOVEMENT_SPEED_ON_SNOW) - 1;
        }
    };

    private final Holder<Attribute> attribute;
    private final AttributeModifier.Operation operation;
    private final ResourceLocation id;

    public DynamicAttributeModifier(ResourceLocation id, Holder<Attribute> attribute, AttributeModifier.Operation operation) {
        this.attribute = attribute;
        this.operation = operation;
        this.id = id;
    }

    protected abstract boolean shouldApply(LivingEntity entity);

    protected abstract double getAmount(LivingEntity entity);

    private void tick(LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if (attributeInstance == null) {
            return;
        }
        if (shouldApply(entity)) {
            double amount = getAmount(entity);
            AttributeModifier modifier = attributeInstance.getModifier(id);
            if (modifier == null || modifier.amount() != amount) {
                attributeInstance.addOrUpdateTransientModifier(new AttributeModifier(id, amount, operation));
            }
        } else if (attributeInstance.hasModifier(id)) {
            attributeInstance.removeModifier(id);
        }
    }

    private static boolean hasDefaultAttributeValue(LivingEntity entity, Holder<Attribute> attribute) {
        return entity.getAttributeValue(attribute) == attribute.value().getDefaultValue();
    }

    private static boolean shouldUpdateModifiers(LivingEntity entity) {
        if (entity.tickCount % 10 != 0) {
            return entity instanceof Player || entity.getControllingPassenger() instanceof Player;
        } else if (
                entity instanceof Mob mob && mob.getTarget() != null
                || entity instanceof OwnableEntity ownable && ownable.getOwner() instanceof Player
        ) {
            return true;
        } else {
            return entity.tickCount % 20 == 0;
        }
    }

    public static void tickModifiers(LivingEntity entity) {
        if (!shouldUpdateModifiers(entity)) {
            return;
        }
        if (entity.canSprint()) {
            SPRINTING_SPEED.tick(entity);
            SPRINTING_STEP_HEIGHT.tick(entity);
        }
        if (entity instanceof Player player && player.getAbilities().flying || entity.onGround() || entity.isFallFlying()) {
            MOVEMENT_SPEED_ON_SNOW.tick(entity);
        }
        if (entity instanceof Player) {
            UMBRELLA_GRAVITY.tick(entity);
        }
        if (entity instanceof Saddleable) {
            MOUNT_SPEED.tick(entity);
        }
    }
}
