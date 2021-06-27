package artifacts.common.item.curio.feet;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.UUID;

public class RunningShoesItem extends CurioItem {

    public RunningShoesItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    private static AttributeModifier getSpeedBonus() {
        double speedMultiplier = ModConfig.server.runningShoes.speedMultiplier.get();
        return new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"), "artifacts:running_shoes_movement_speed", speedMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        // onUnequip does not get called on the client
        if (!isEquippedBy(event.player)) {
            ModifiableAttributeInstance movementSpeed = event.player.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedBonus = getSpeedBonus();
            if (movementSpeed != null && movementSpeed.hasModifier(speedBonus)) {
                movementSpeed.removeModifier(speedBonus);
                event.player.maxUpStep = 0.6F;
            }
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this)) {
            ModifiableAttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedBonus = getSpeedBonus();
            if (entity.isSprinting()) {
                if (!movementSpeed.hasModifier(speedBonus)) {
                    movementSpeed.addTransientModifier(speedBonus);
                }
                if (entity instanceof PlayerEntity) {
                    entity.maxUpStep = Math.max(entity.maxUpStep, 1.1F);
                }

                if (entity.tickCount % 20 == 0) {
                    damageStack(identifier, index, entity, stack);
                }
            } else {
                if (movementSpeed.hasModifier(speedBonus)) {
                    movementSpeed.removeModifier(speedBonus);
                    entity.maxUpStep = 0.6F;
                }
            }
        }
    }
}
