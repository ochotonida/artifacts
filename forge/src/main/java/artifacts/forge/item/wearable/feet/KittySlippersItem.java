package artifacts.forge.item.wearable.feet;

import artifacts.forge.event.ArtifactEventHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModTags;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class KittySlippersItem extends WearableArtifactItem {

    public KittySlippersItem() {
        EntityEvent.ADD.register(this::onEntityJoinWorld);
        ArtifactEventHandler.addListener(this, LivingChangeTargetEvent.class, this::onLivingChangeTargetEvent, LivingChangeTargetEvent::getNewTarget);
        ArtifactEventHandler.addListener(this, LivingEvent.LivingTickEvent.class, this::onLivingUpdate, event -> event.getEntity().getLastHurtByMob());
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.KITTY_SLIPPERS_ENABLED.get();
    }

    private EventResult onEntityJoinWorld(Entity entity, Level level) {
        if (entity instanceof PathfinderMob creeper && creeper.getType().is(ModTags.CREEPERS)) {
            creeper.goalSelector.addGoal(3, new AvoidEntityGoal<>(creeper, Player.class, (target) -> target != null && ModGameRules.KITTY_SLIPPERS_ENABLED.get() && isEquippedBy(target), 6, 1, 1.3, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
        }
        return EventResult.pass();
    }

    private void onLivingChangeTargetEvent(LivingChangeTargetEvent event, LivingEntity wearer) {
        if (ModGameRules.KITTY_SLIPPERS_ENABLED.get() && event.getEntity() instanceof Mob creeper && creeper.getType().is(ModTags.CREEPERS)) {
            event.setCanceled(true);
        }
    }

    private void onLivingUpdate(LivingEvent.LivingTickEvent event, LivingEntity wearer) {
        if (ModGameRules.KITTY_SLIPPERS_ENABLED.get() && event.getEntity().getType().is(ModTags.CREEPERS)) {
            event.getEntity().setLastHurtByMob(null);
        }
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.CAT_AMBIENT;
    }
}
