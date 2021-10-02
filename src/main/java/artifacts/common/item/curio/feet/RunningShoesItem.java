package artifacts.common.item.curio.feet;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import top.theillusivec4.curios.api.SlotContext;

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
            AttributeInstance movementSpeed = event.player.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedBonus = getSpeedBonus();
            if (movementSpeed != null && movementSpeed.hasModifier(speedBonus)) {
                movementSpeed.removeModifier(speedBonus);
                event.player.maxUpStep = 0.6F;
            }
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this)) {
            AttributeInstance movementSpeed = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedBonus = getSpeedBonus();
            if (slotContext.entity().isSprinting()) {
                if (!movementSpeed.hasModifier(speedBonus)) {
                    movementSpeed.addTransientModifier(speedBonus);
                }
                if (slotContext.entity() instanceof Player) {
                    slotContext.entity().maxUpStep = Math.max(slotContext.entity().maxUpStep, 1.1F);
                }

                if (slotContext.entity().tickCount % 20 == 0) {
                    damageStack(slotContext, stack);
                }
            } else {
                if (movementSpeed.hasModifier(speedBonus)) {
                    movementSpeed.removeModifier(speedBonus);
                    slotContext.entity().maxUpStep = 0.6F;
                }
            }
        }
    }
}
