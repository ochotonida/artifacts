package artifacts.forge.client;

import artifacts.forge.capability.SwimHandler;
import artifacts.forge.item.wearable.belt.CloudInABottleItem;
import artifacts.forge.network.DoubleJumpPacket;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.network.NetworkHandler;
import artifacts.network.ToggleArtifactPacket;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class InputEventHandler {

    private static boolean wasSprintKeyDown;
    private static boolean wasSprintingOnGround;
    private static boolean hasTouchedGround;
    private static boolean canDoubleJump;
    private static boolean hasReleasedJumpKey;

    private static final Map<WearableArtifactItem, KeyMapping> TOGGLE_KEY_MAPPINGS = new HashMap<>();

    public static void setup() {
        ClientTickEvent.CLIENT_POST.register(InputEventHandler::onClientTick);
        addToggleInputHandler(ModItems.NIGHT_VISION_GOGGLES.get(), ModKeyMappings.TOGGLE_NIGHT_VISION_GOGGLES);
        addToggleInputHandler(ModItems.UNIVERSAL_ATTRACTOR.get(), ModKeyMappings.TOGGLE_UNIVERSAL_ATTRACTOR);
    }

    public static KeyMapping getToggleKey(WearableArtifactItem item) {
        return TOGGLE_KEY_MAPPINGS.get(item);
    }

    private static void addToggleInputHandler(WearableArtifactItem item, KeyMapping toggleKey) {
        TOGGLE_KEY_MAPPINGS.put(item, toggleKey);
        ToggleInputHandler handler = new ToggleInputHandler(item);
        ClientTickEvent.CLIENT_PRE.register(instance -> handler.onClientTick());
    }

    private static void onClientTick(Minecraft instance) {
        LocalPlayer player = instance.player;
        // noinspection ConstantConditions
        if (player != null && player.input != null) {
            handleCloudInABottleInput(player);
            handleHeliumFlamingoInput(player);
        }
    }

    private static void handleCloudInABottleInput(LocalPlayer player) {
        if ((player.onGround() || player.onClimbable()) && !player.isInWater()) {
            hasReleasedJumpKey = false;
            canDoubleJump = true;
        } else if (!player.input.jumping) {
            hasReleasedJumpKey = true;
        } else if (!player.getAbilities().flying && canDoubleJump && hasReleasedJumpKey) {
            canDoubleJump = false;
            if (ModItems.CLOUD_IN_A_BOTTLE.get().isEquippedBy(player)) {
                artifacts.forge.network.NetworkHandler.INSTANCE.sendToServer(new DoubleJumpPacket());
                CloudInABottleItem.jump(player);
            }
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

    private static class ToggleInputHandler {

        private boolean wasToggleKeyDown;
        private final WearableArtifactItem item;

        public ToggleInputHandler(WearableArtifactItem item) {
            this.item = item;
        }

        public void onClientTick() {
            boolean isToggleKeyDown = getToggleKey(item).isDown();
            if (isToggleKeyDown && !wasToggleKeyDown) {
                NetworkHandler.CHANNEL.sendToServer(new ToggleArtifactPacket(item));
            }
            wasToggleKeyDown = isToggleKeyDown;
        }
    }
}
