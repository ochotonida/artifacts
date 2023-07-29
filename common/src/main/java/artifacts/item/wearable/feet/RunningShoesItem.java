package artifacts.item.wearable.feet;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class RunningShoesItem extends WearableArtifactItem {

    private static final AttributeModifier STEP_HEIGHT_BONUS = new AttributeModifier(UUID.fromString("4a312f09-78e0-4f3a-95c2-07ed63212472"), "artifacts:running_shoes_step_height", 0.5, AttributeModifier.Operation.ADDITION);

    public RunningShoesItem() {
        TickEvent.PLAYER_PRE.register(this::onPlayerTick);
    }

    private static Attribute getStepHeightAttribute() {
        return PlatformServices.platformHelper.getStepHeightAttribute();
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.RUNNING_SHOES_DO_INCREASE_STEP_HEIGHT.get() && ModGameRules.RUNNING_SHOES_SPEED_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.RUNNING_SHOES_SPEED_BONUS.get() > 0) {
            tooltip.add(tooltipLine("movement_speed"));
        }
        if (ModGameRules.RUNNING_SHOES_DO_INCREASE_STEP_HEIGHT.get()) {
            tooltip.add(tooltipLine("step_height"));
        }
    }

    private static AttributeModifier getSpeedBonus() {
        double speedMultiplier = Math.max(0, ModGameRules.RUNNING_SHOES_SPEED_BONUS.get() / 100D);
        return new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"), "artifacts:running_shoes_movement_speed", speedMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private void onPlayerTick(Player player) {
        // onUnequip does not get called on the client
        if (!isEquippedBy(player)) {
            AttributeInstance stepHeight = player.getAttribute(getStepHeightAttribute());
            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier movementSpeedBonus = getSpeedBonus();
            if (movementSpeed != null && movementSpeed.hasModifier(movementSpeedBonus)) {
                movementSpeed.removeModifier(movementSpeedBonus);
            }
            if (stepHeight != null && stepHeight.hasModifier(STEP_HEIGHT_BONUS)) {
                stepHeight.removeModifier(STEP_HEIGHT_BONUS);
            }
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void wornTick(LivingEntity entity, ItemStack stack) {
        AttributeInstance stepHeight = entity.getAttribute(getStepHeightAttribute());
        AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedBonus = getSpeedBonus();
        if (entity.isSprinting()) {
            if (!movementSpeed.hasModifier(speedBonus)) {
                movementSpeed.addTransientModifier(speedBonus);
            }
            if (ModGameRules.RUNNING_SHOES_DO_INCREASE_STEP_HEIGHT.get() && !stepHeight.hasModifier(STEP_HEIGHT_BONUS) && entity instanceof Player) {
                stepHeight.addTransientModifier(STEP_HEIGHT_BONUS);
            }
        } else {
            if (movementSpeed.hasModifier(speedBonus)) {
                movementSpeed.removeModifier(speedBonus);
            }
            if (stepHeight.hasModifier(STEP_HEIGHT_BONUS)) {
                stepHeight.removeModifier(STEP_HEIGHT_BONUS);
            }
        }
    }
}
