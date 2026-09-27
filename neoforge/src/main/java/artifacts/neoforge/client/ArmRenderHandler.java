package artifacts.neoforge.client;

import artifacts.equipment.client.EquipmentRenderingManager;
import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.common.NeoForge;

public abstract class ArmRenderHandler {

    public static void setup() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, ArmRenderHandler::onRenderArm);
    }

    public static void onRenderArm(RenderArmEvent<?> event) {
        if (!event.isCanceled() && event.getAvatar() instanceof AbstractClientPlayer player) {
            EquipmentRenderingManager.renderFirstPersonArm(event.getPoseStack(), event.getSubmitNodeCollector(), event.getLightCoords(), player, event.getArm());
        }
    }
}
