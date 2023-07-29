package artifacts.item.wearable.belt;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class UniversalAttractorItem extends WearableArtifactItem {

    public UniversalAttractorItem() {
        PlayerEvent.DROP_ITEM.register(this::onItemToss);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get();
    }

    EventResult onItemToss(Player player, ItemEntity entity) {
        if (ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get() && isEquippedBy(player)) {
            player.getCooldowns().addCooldown(this, 100);
        }
        return EventResult.pass();
    }

    @Override
    public void wornTick(LivingEntity entity, ItemStack stack) {
        if (
                !ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get()
                || !(entity instanceof Player player)
                || player.isSpectator()
                || player.getCooldowns().isOnCooldown(this)
                || !isActivated(stack)
        ) {
            return;
        }

        Vec3 playerPos = player.position().add(0, 0.75, 0);

        int range = 5;
        List<ItemEntity> items = player.level().getEntitiesOfClass(ItemEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        int amountPulled = 0;
        for (ItemEntity item : items) {
            if (item.isAlive() && !item.hasPickUpDelay()) {
                if (amountPulled++ > 200) {
                    break;
                }

                Vec3 motion = playerPos.subtract(item.position().add(0, item.getBbHeight() / 2, 0));
                if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
                    motion = motion.normalize();
                }
                item.setDeltaMovement(motion.scale(0.6));
            }
        }
    }
}
