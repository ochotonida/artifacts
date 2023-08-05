package artifacts.fabric.event;

import artifacts.component.SwimEventHandler;
import artifacts.item.wearable.feet.AquaDashersItem;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import dev.architectury.event.EventResult;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;

public class SwimEventsFabric {

    @SuppressWarnings("UnstableApiUsage")
    public static void register() {
        PlayerSwimCallback.EVENT.register(SwimEventsFabric::onPlayerSwim);
        LivingFluidCollisionCallback.EVENT.register(SwimEventsFabric::onAquaDashersFluidCollision);
    }

    private static TriState onPlayerSwim(Player player) {
        EventResult result = SwimEventHandler.onPlayerSwim(player);
        if (!result.interruptsFurtherEvaluation()) {
            return TriState.DEFAULT;
        } else if (result.isTrue()) {
            return TriState.TRUE;
        } else {
            return TriState.FALSE;
        }
    }

    private static boolean onAquaDashersFluidCollision(LivingEntity entity, FluidState fluidState) {
        return AquaDashersItem.onFluidCollision(entity, fluidState);
    }
}
