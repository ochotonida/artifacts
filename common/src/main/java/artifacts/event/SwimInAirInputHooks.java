package artifacts.event;

import artifacts.component.SwimData;
import artifacts.component.ability.SwimInAir;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

public class SwimInAirInputHooks {

    private static boolean wasSwimKeyDown;

    public static void onClientTick(Minecraft instance) {
        LocalPlayer player = instance.player;
        // noinspection ConstantValue
        if (player != null && player.input != null) {
            handleSwimInAirInput(player);
        }
    }

    private static void handleSwimInAirInput(Player player) {
        SwimData swimData = PlatformServices.getPlatformHelper().getSwimData(player);
        if (swimData == null) {
            return;
        }

        boolean isSwimKeyDown = ModKeyMappings.getHeliumFlamingoKey().isDown();
        boolean isSwimKeyPressed = isSwimKeyDown && !wasSwimKeyDown;
        wasSwimKeyDown = isSwimKeyDown;

        if (isSwimKeyPressed) {
            if (swimData.isSwimFlying() ? player.isSwimming() && swimData.shouldDepleteSwimFlyingCharge(player) : SwimInAir.canSwim(player)) {
                swimData.toggleSwimFlying(player);
                swimData.syncToServer(player);
            }
        }
    }
}
