package artifacts.forge.client;

import artifacts.forge.capability.SwimHandler;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

public class InputEventHandler {

    private static boolean wasSprintKeyDown;
    private static boolean wasSprintingOnGround;
    private static boolean hasTouchedGround;

    public static void setup() {
        ClientTickEvent.CLIENT_POST.register(InputEventHandler::onClientTick);
    }

    private static void onClientTick(Minecraft instance) {
        LocalPlayer player = instance.player;
        // noinspection ConstantConditions
        if (player != null && player.input != null) {
            handleHeliumFlamingoInput(player);
        }
    }

    private static void handleHeliumFlamingoInput(Player player) {
        if (ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() <= 0) {
            return;
        }

        boolean isSprintKeyDown = ModKeyMappings.getHeliumFlamingoKey().isDown();

        player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                handler -> {
                    if (!handler.isSwimming()) {
                        if (player.onGround()) {
                            hasTouchedGround = true;
                        } else if (canActivateHeliumFlamingo(handler, player, isSprintKeyDown)) {
                            handler.setSwimming(true);
                            handler.syncSwimming();
                            hasTouchedGround = false;
                        }
                    } else if (player.getAbilities().flying) {
                        handler.setSwimming(false);
                        handler.syncSwimming();
                        hasTouchedGround = true;
                    }
                }
        );

        wasSprintKeyDown = isSprintKeyDown;
        if (!isSprintKeyDown) {
            wasSprintingOnGround = false;
        } else if (player.onGround()) {
            wasSprintingOnGround = true;
        }
    }

    private static boolean canActivateHeliumFlamingo(SwimHandler handler, Player player, boolean isSprintKeyDown) {
        if (handler.isSwimming()
                || handler.getSwimTime() < 0
                || !ModItems.HELIUM_FLAMINGO.get().isEquippedBy(player)) {
            return false;
        }
        if (player.isSwimming()) {
            return true;
        }
        return isSprintKeyDown
                && !wasSprintKeyDown
                && !wasSprintingOnGround
                && hasTouchedGround
                && !player.onGround()
                && (!player.isInWater() || SwimHandler.isSinking(player))
                && !player.isFallFlying()
                && !player.getAbilities().flying
                && !player.isPassenger();
    }
}
