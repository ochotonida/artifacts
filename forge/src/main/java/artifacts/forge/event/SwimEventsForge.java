package artifacts.forge.event;

import artifacts.component.SwimEventHandler;
import artifacts.item.wearable.feet.AquaDashersItem;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import dev.architectury.event.EventResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class SwimEventsForge {

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(SwimEventsForge::onPlayerSwim);
        MinecraftForge.EVENT_BUS.addListener(SwimEventsForge::onAquaDashersFluidCollision);
    }

    public static void onPlayerSwim(PlayerSwimEvent event) {
        if (event.getResult() == Event.Result.DEFAULT) {
            EventResult result = SwimEventHandler.onPlayerSwim(event.getEntity());
            if (!result.interruptsFurtherEvaluation()) {
                event.setResult(Event.Result.DEFAULT);
            } else if (result.isTrue()) {
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void onAquaDashersFluidCollision(LivingFluidCollisionEvent event) {
        if (AquaDashersItem.onFluidCollision(event.getEntity(), event.getFluidState())) {
            event.setResult(Event.Result.ALLOW);
        }
    }
}
