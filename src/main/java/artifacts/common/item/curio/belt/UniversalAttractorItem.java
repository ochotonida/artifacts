package artifacts.common.item.curio.belt;

import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModKeyMappings;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class UniversalAttractorItem extends CurioItem {

    public UniversalAttractorItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onItemToss);
    }

    protected KeyMapping getToggleKey() {
        return ModKeyMappings.TOGGLE_UNIVERSAL_ATTRACTOR;
    }

    @Override
    public boolean isToggleable() {
        return true;
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get();
    }

    private void onItemToss(ItemTossEvent event) {
        if (ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get()) {
            event.getPlayer().getCooldowns().addCooldown(this, 100);
        }
    }

    // magnet logic from Botania, see https://github.com/Vazkii/Botania
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get()
                || !(slotContext.entity() instanceof Player player)
                || player.isSpectator()
                || player.getCooldowns().isOnCooldown(this)
                || !isActivated(stack)
        ) {
            return;
        }

        Vec3 playerPos = player.position().add(0, 0.75, 0);

        int range = 5;
        List<ItemEntity> items = player.level.getEntitiesOfClass(ItemEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        int pulled = 0;
        for (ItemEntity item : items) {
            if (item.isAlive() && !item.hasPickUpDelay() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                if (pulled++ > 200) {
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
