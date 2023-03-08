package artifacts.common.item.curio.belt;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class UniversalAttractorItem extends CurioItem {

    public UniversalAttractorItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onItemToss);
    }

    public static int getCooldown(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Cooldown");
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.getOrCreateTag().putInt("Cooldown", cooldown);
    }

    private void onItemToss(ItemTossEvent event) {
        if (ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get()) {
            CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getPlayer()).ifPresent((triple) -> setCooldown(triple.right, 200));
        }
    }

    // magnet logic from Botania, see https://github.com/Vazkii/Botania
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (!ModGameRules.UNIVERSAL_ATTRACTOR_ENABLED.get() || entity.isSpectator() || !(entity instanceof Player)) {
            return;
        }

        int cooldown = getCooldown(stack);
        if (cooldown <= 0) {
            Vec3 playerPos = entity.position().add(0, 0.75, 0);

            int range = 5;
            List<ItemEntity> items = entity.level.getEntitiesOfClass(ItemEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
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
        } else {
            setCooldown(stack, cooldown - 1);
        }
    }
}
