package artifacts.item.wearable.feet;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.mixin.accessors.MobAccessor;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModTags;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class KittySlippersItem extends WearableArtifactItem {

    public KittySlippersItem() {
        EntityEvent.ADD.register(this::onEntityJoinWorld);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.KITTY_SLIPPERS_ENABLED.get();
    }

    private EventResult onEntityJoinWorld(Entity entity, Level level) {
        if (entity instanceof PathfinderMob creeper && creeper.getType().is(ModTags.CREEPERS)) {
            ((MobAccessor) creeper).getGoalSelector().addGoal(3, new AvoidEntityGoal<>(creeper, Player.class, (target) -> target != null && ModGameRules.KITTY_SLIPPERS_ENABLED.get() && isEquippedBy(target), 6, 1, 1.3, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
        }
        return EventResult.pass();
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.CAT_AMBIENT;
    }
}
