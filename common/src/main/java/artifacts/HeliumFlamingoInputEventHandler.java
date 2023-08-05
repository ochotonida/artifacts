package artifacts;

import artifacts.component.SwimData;
import artifacts.item.wearable.necklace.CharmOfSinkingItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

public class HeliumFlamingoInputEventHandler {

    private static boolean wasSprintKeyDown;
    private static boolean wasSprintingOnGround;
    private static boolean hasTouchedGround;

    public static void register() {
        ClientTickEvent.CLIENT_POST.register(HeliumFlamingoInputEventHandler::onClientTick);
    }

    private static void onClientTick(Minecraft instance) {
        LocalPlayer player = instance.player;
        if (player != null && player.input != null) {
            handleHeliumFlamingoInput(player);
        }
    }

    private static void handleHeliumFlamingoInput(Player player) {
        if (ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() <= 0) {
            return;
        }

        boolean isSprintKeyDown = ModKeyMappings.getHeliumFlamingoKey().isDown();

        SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
        if (swimData == null) {
            return;
        }

        if (!swimData.isSwimming()) {
            if (player.onGround()) {
                hasTouchedGround = true;
            } else if (canActivateHeliumFlamingo(swimData, player, isSprintKeyDown)) {
                swimData.setSwimming(true);
                swimData.syncSwimming();
                hasTouchedGround = false;
            }
        } else if (player.getAbilities().flying) {
            swimData.setSwimming(false);
            swimData.syncSwimming();
            hasTouchedGround = true;
        }

        wasSprintKeyDown = isSprintKeyDown;
        if (!isSprintKeyDown) {
            wasSprintingOnGround = false;
        } else if (player.onGround()) {
            wasSprintingOnGround = true;
        }
    }

    private static boolean canActivateHeliumFlamingo(SwimData swimData, Player player, boolean isSprintKeyDown) {
        if (swimData.isSwimming()
                || swimData.getSwimTime() < 0
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
                && (!player.isInWater() || CharmOfSinkingItem.shouldSink(player))
                && !player.isFallFlying()
                && !player.getAbilities().flying
                && !player.isPassenger();
    }
}
