package artifacts.forge.client;

import artifacts.client.UmbrellaArmPoseHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;

public class UmbrellaArmPoseHandler {

    public static void setup() {
        MinecraftForge.EVENT_BUS.addListener(UmbrellaArmPoseHandler::onLivingRender);
    }

    public static void onLivingRender(RenderLivingEvent.Pre<?, ?> event) {
        UmbrellaArmPoseHelper.setUmbrellaArmPose(event.getRenderer().getModel(), event.getEntity());
    }
}
