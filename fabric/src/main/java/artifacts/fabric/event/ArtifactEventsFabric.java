package artifacts.fabric.event;

import artifacts.item.wearable.hands.DiggingClawsItem;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class ArtifactEventsFabric {

    public static void register() {
        PlayerEvents.BREAK_SPEED.register(ArtifactEventsFabric::onDiggingClawsBreakSpeed);
    }

    private static float onDiggingClawsBreakSpeed(Player player, BlockState state, BlockPos pos, float speed) {
        return speed + DiggingClawsItem.getSpeedBonus(player, state);
    }
}
