package artifacts.client;

import artifacts.item.wearable.belt.CloudInABottleItem;
import artifacts.network.DoubleJumpPacket;
import artifacts.network.NetworkHandler;
import artifacts.registry.ModItems;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class CloudInABottleInputHandler {

    private static boolean canDoubleJump;
    private static boolean hasReleasedJumpKey;

    public static void register() {
        ClientTickEvent.CLIENT_POST.register(CloudInABottleInputHandler::onClientTick);
    }

    private static void onClientTick(Minecraft instance) {
        LocalPlayer player = instance.player;
        if (player != null && player.input != null) {
            handleCloudInABottleInput(player);
        }
    }

    private static void handleCloudInABottleInput(LocalPlayer player) {
        if ((player.isOnGround() || player.onClimbable()) && !player.isInWater()) {
            hasReleasedJumpKey = false;
            canDoubleJump = true;
        } else if (!player.input.jumping) {
            hasReleasedJumpKey = true;
        } else if (!player.getAbilities().flying && canDoubleJump && hasReleasedJumpKey) {
            canDoubleJump = false;
            if (ModItems.CLOUD_IN_A_BOTTLE.get().isEquippedBy(player)) {
                NetworkHandler.CHANNEL.sendToServer(new DoubleJumpPacket());
                CloudInABottleItem.jump(player);
            }
        }
    }
}
